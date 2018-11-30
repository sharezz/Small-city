package com.sharezzorama.smallcity

import android.Manifest
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.sharezzorama.smallcity.contact.ContactFBDataSource
import com.sharezzorama.smallcity.entity.Contact
import com.sharezzorama.smallcity.entity.Phone
import com.sharezzorama.smallcity.utils.PermissionsUtils
import kotlinx.android.synthetic.main.activity_main.*
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.ItemizedIconOverlay
import org.osmdroid.views.overlay.ItemizedOverlayWithFocus
import org.osmdroid.views.overlay.OverlayItem
import java.util.*


class MainActivity : AppCompatActivity() {

    companion object {
        private const val RQ_PERMISSIONS = 1
    }


    private val dataSource = ContactFBDataSource()


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                message.setText(R.string.title_home)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                message.setText(R.string.title_dashboard)
                dataSource.getAll()
                        .subscribe(
                                { Log.d("BANANA", "Get all success! count: ${it.size}") },
                                { Log.e("BANANA", "Get all failed", it) })
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                dataSource.save(Contact(name = "Contact${Date().time}", phone = Phone(Date().time.toString(), "Desc")))
                        .subscribe(
                                { Log.d("BANANA", "Save success! id: ${it.id}") },
                                { Log.e("BANANA", "Save failed", it) }
                        )


                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestPermissions()

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        map.setTileSource(TileSourceFactory.MAPNIK)
        map.setBuiltInZoomControls(true)
        map.setMultiTouchControls(true)
        val mapController = map.controller
        mapController.setZoom(15.0)
        val startPoint = GeoPoint(49.795495, 72.829091)
        mapController.setCenter(startPoint)
        val items = ArrayList<OverlayItem>()

        items.add(OverlayItem("Title", "Description", GeoPoint(49.795495, 72.829091)))
        val overlay = ItemizedOverlayWithFocus<OverlayItem>(this, items, object : ItemizedIconOverlay.OnItemGestureListener<OverlayItem> {
            override fun onItemLongPress(index: Int, item: OverlayItem?): Boolean {
                Toast.makeText(this@MainActivity, "onItemLongPress: ${item?.title}", Toast.LENGTH_LONG).show()
                return false
            }

            override fun onItemSingleTapUp(index: Int, item: OverlayItem?): Boolean {
                Toast.makeText(this@MainActivity, "onItemSingleTapUp: ${item?.title}", Toast.LENGTH_LONG).show()
                return false
            }

        })

        overlay.setFocusItemsOnTap(true)
        map.overlays.add(overlay)
    }


    override fun onResume() {
        super.onResume()
        map.onResume()
    }

    override fun onPause() {
        super.onPause()
        map.onPause()
    }

    private fun requestPermissions() {
        PermissionsUtils.requestPermissionsSafely(activity = this,
                permissions = listOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE),
                requestCode = RQ_PERMISSIONS)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == RQ_PERMISSIONS && grantResults.isNotEmpty()) {
            map.onResume()
        } else {
            //show error
        }
    }

}
