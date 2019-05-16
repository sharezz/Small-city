package com.sharezzorama.smallcity.map

import android.Manifest
import android.os.Bundle
import android.view.InputDevice
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import com.sharezzorama.smallcity.R
import com.sharezzorama.smallcity.base.BaseFragment
import com.sharezzorama.smallcity.base.Layout
import com.sharezzorama.smallcity.utils.PermissionsUtils
import kotlinx.android.synthetic.main.fragment_map.*
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint


@Layout(id = R.layout.fragment_map)
open class MapFragment : BaseFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        PermissionsUtils.requestPermissionsSafely(
                fragment = this,
                permissions =
                listOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE), requestCode = 45)

        mapView.setTileSource(TileSourceFactory.MAPNIK)
        mapView.setBuiltInZoomControls(false);
        mapView.setMultiTouchControls(true);
        val mapController = mapView.getController()
        mapController.setZoom(15.0)
        val startPoint = GeoPoint(49.795495, 72.829091)
        mapController.setCenter(startPoint)

        mapView.setOnGenericMotionListener(object : View.OnGenericMotionListener {
            override fun onGenericMotion(view: View, event: MotionEvent): Boolean {
                if (0 != event.getSource() and InputDevice.SOURCE_CLASS_POINTER) {
                    when (event.getAction()) {
                        MotionEvent.ACTION_SCROLL -> {
                            if (event.getAxisValue(MotionEvent.AXIS_VSCROLL) < 0.0f) {
                                mapView.controller.zoomOut()
                                val iGeoPoint = mapView.getProjection().fromPixels(event.x.toInt(), event.y.toInt())
                                Toast.makeText(context, "lat: ${iGeoPoint.latitude}, lng:${iGeoPoint.longitude}", Toast.LENGTH_LONG).show()

                            } else {
                                val iGeoPoint = mapView.getProjection().fromPixels(event.x.toInt(), event.y.toInt())
                                Toast.makeText(context, "lat: ${iGeoPoint.latitude}, lng:${iGeoPoint.longitude}", Toast.LENGTH_LONG).show()
                                mapView.controller.animateTo(iGeoPoint)
                                mapView.controller.zoomIn()
                            }
                            return true
                        }
                    }
                }
                return false
            }
        })
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        mapView.onPause()
        super.onPause()
    }


}