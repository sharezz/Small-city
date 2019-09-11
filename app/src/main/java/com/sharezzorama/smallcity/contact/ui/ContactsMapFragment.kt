package com.sharezzorama.smallcity.contact.ui

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.sharezzorama.smallcity.R
import com.sharezzorama.smallcity.base.Layout
import com.sharezzorama.smallcity.contact.viewmodel.ContactsViewModel
import com.sharezzorama.smallcity.data.entity.Address
import com.sharezzorama.smallcity.data.entity.Contact
import com.sharezzorama.smallcity.map.MapFragment
import kotlinx.android.synthetic.main.fragment_contacts.*
import kotlinx.android.synthetic.main.fragment_map.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.osmdroid.util.BoundingBox
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker
import java.util.function.Predicate

@Layout(id = R.layout.fragment_map)
class ContactsMapFragment : MapFragment() {

    companion object {
        const val SEARCH_RESULT_ITEM_ID_PREFIX = "search_result_"
    }

    private val contactsViewModel: ContactsViewModel by sharedViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        contactsViewModel.contactsLiveData
                .observe(this, Observer<List<Contact>> { list ->
                    cleanLastSearchResult()
                    list.forEach { contact ->
                        val marker = Marker(mapView)
                        val building = addressViewModel.getBuilding(contact.buildingId)
                        if (building != null) {
                            marker.position = GeoPoint(building.lat?.toDouble()!!, building.lng?.toDouble()!!)
                            marker.title = "${contact.name}"
                            marker.icon = ContextCompat.getDrawable(context!!, R.drawable.ic_map_marker)
                            marker.subDescription = "${contact.description}, ${building.street} ${building.housenumber}"
                            marker.id = building.id.toString()
                            marker.id = generateMarkerId(building)
                            /* marker.setOnMarkerClickListener { selectedMarker, _ ->
                                 onBuildingSelected(contact, selectedMarker)
                                 true
                             }*/
                            mapView.overlays.add(marker)
                        }
                    }

                    //show last point
                    if (list.isNotEmpty()) {
                        val building = addressViewModel.getBuilding(list.last().buildingId)
                        val geopoint = GeoPoint(GeoPoint(building?.lat?.toDouble()!!, building.lng?.toDouble()!!))
                        mapView.controller.animateTo(geopoint)
                        //mapView.zoomToBoundingBox(BoundingBox())
                    }
                })
    }

    private fun generateMarkerId(building: Address) =
            "$SEARCH_RESULT_ITEM_ID_PREFIX${building.id}"

    private fun cleanLastSearchResult() {
        mapView
                .overlays
                .filter { overlay ->
                    overlay is Marker
                            && overlay.id != null
                            && overlay.id.contains(SEARCH_RESULT_ITEM_ID_PREFIX)
                }
                .forEach { overlay -> mapView.overlays.remove(overlay) }
    }
}