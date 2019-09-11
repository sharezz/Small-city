package com.sharezzorama.smallcity.map

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.sharezzorama.smallcity.ProjectConstants.Companion.CITY_CENTER_LAT
import com.sharezzorama.smallcity.ProjectConstants.Companion.CITY_CENTER_LON
import com.sharezzorama.smallcity.R
import com.sharezzorama.smallcity.base.BaseFragment
import com.sharezzorama.smallcity.base.Layout
import com.sharezzorama.smallcity.data.entity.Address
import com.sharezzorama.smallcity.map.viewmodel.AddressViewModel
import com.sharezzorama.smallcity.utils.PermissionsUtils
import kotlinx.android.synthetic.main.fragment_map.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.CustomZoomButtonsController.Visibility.*


@Layout(id = R.layout.fragment_map)
open class MapFragment : BaseFragment() {

    companion object {
        const val ZOOM = 17.0
        val START_GEO_POINT = GeoPoint(CITY_CENTER_LAT, CITY_CENTER_LON)
    }

    private val mapInitializer = MapInitializer(START_GEO_POINT, ZOOM, R.drawable.ic_circle_medium, false)
    protected val addressViewModel: AddressViewModel by sharedViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMap()
        if (hasPermissions()) {
            loadBuildings()
        } else {
            PermissionsUtils.requestPermissionsSafely(
                    fragment = this,
                    permissions =
                    listOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE), requestCode = 45)
        }
    }

    private fun hasPermissions() =
            PermissionsUtils.hasPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                    && PermissionsUtils.hasPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)

    private fun loadBuildings() {
        addressViewModel.loadBuildings()
        addressViewModel.buildingsLiveData.observe(this, Observer { buildings ->
            buildings
                    .filter { building -> building.value.lat != null && building.value.lng != null }
                    .forEach { building ->
                        val marker = Marker(mapView)
                        marker.position = GeoPoint(building.value.lat?.toDouble()!!, building.value.lng?.toDouble()!!)
                        marker.title = "${building.value.street}, ${building.value.housenumber}"
                        marker.icon =ContextCompat.getDrawable(context!!, mapInitializer.buildingMarkerIcon)
                        marker.subDescription
                        marker.setOnMarkerClickListener { selectedMarker, _ ->
                            onBuildingSelected(building.value, selectedMarker)
                            true
                        }
                        mapView.overlays.add(marker)
                    }
            onBuildingsLoaded()
        })
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (hasPermissions()) {
            loadBuildings()
        }
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        mapView.onPause()
        super.onPause()
    }

    private fun initMap() {
        onInitMap(mapInitializer)
        mapView.apply {
            setTileSource(TileSourceFactory.HIKEBIKEMAP)
            zoomController.setVisibility(if (mapInitializer.showZoomButtons) ALWAYS else NEVER)
            setMultiTouchControls(true)
            controller.apply {
                setZoom(mapInitializer.zoom)
                setCenter(mapInitializer.startGeoPoint)
            }
        }
        //loadBuildings()
        //addressViewModel.loadBuildings()

        /* mapView.setOnGenericMotionListener(object : View.OnGenericMotionListener {
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
         })*/
    }

    open protected fun onInitMap(mapInitializer: MapInitializer) {}
    open protected fun onBuildingsLoaded() {}
    open protected fun onBuildingSelected(building: Address, marker: Marker) {

        val contactSheet = activity
        if (contactSheet != null && contactSheet is OnBuildingSelectListener) {
            contactSheet.onBuildingSelected(building, marker)
        }
    }
}