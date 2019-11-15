package com.sharezzorama.smallcity.map

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.sharezzorama.smallcity.ProjectConstants.Companion.CITY_CENTER_LAT
import com.sharezzorama.smallcity.ProjectConstants.Companion.CITY_CENTER_LON
import com.sharezzorama.smallcity.R
import com.sharezzorama.smallcity.base.BaseFragment
import com.sharezzorama.smallcity.base.Layout
import com.sharezzorama.smallcity.data.entity.Address
import com.sharezzorama.smallcity.map.viewmodel.AddressViewModel
import com.sharezzorama.smallcity.utils.PermissionsUtils
import com.sharezzorama.toMarker
import kotlinx.android.synthetic.main.fragment_map.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController.Visibility.ALWAYS
import org.osmdroid.views.CustomZoomButtonsController.Visibility.NEVER
import org.osmdroid.views.overlay.Marker


@Layout(id = R.layout.fragment_map)
open class MapFragment : BaseFragment() {

    companion object {
        const val ZOOM = 17.0
        val START_GEO_POINT = GeoPoint(CITY_CENTER_LAT, CITY_CENTER_LON)
    }

    private val mapInitializer = MapInitializer(START_GEO_POINT, ZOOM, R.drawable.ic_circle_medium, false)
    protected val addressViewModel: AddressViewModel by sharedViewModel()


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
        addressViewModel
                .buildingsLiveData
                .observe(this, Observer { buildings ->
                    onBuildingsLoaded(buildings = buildings)
                })
        addressViewModel.loadBuildings()
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
    }

    protected open fun onInitMap(mapInitializer: MapInitializer) {}
    protected open fun onBuildingSelected(building: Address, marker: Marker) {

        val contactSheet = activity
        if (contactSheet != null && contactSheet is OnBuildingSelectListener) {
            contactSheet.onBuildingSelected(building, marker)
        }
    }

    protected open fun onBuildingsLoaded(buildings: List<Address>) {
        buildings.map { address ->
            val marker = address.toMarker(mapView)
            marker.setOnMarkerClickListener { m, _ ->
                onBuildingSelected(address, m)
                true
            }
            marker
        }
                .forEach { marker ->
                    mapView.overlays.add(marker)
                }
    }
}