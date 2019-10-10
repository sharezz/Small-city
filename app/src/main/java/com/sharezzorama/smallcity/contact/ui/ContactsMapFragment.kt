package com.sharezzorama.smallcity.contact.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.sharezzorama.smallcity.R
import com.sharezzorama.smallcity.base.Layout
import com.sharezzorama.smallcity.contact.viewmodel.ContactsViewModel
import com.sharezzorama.smallcity.data.entity.Address
import com.sharezzorama.smallcity.data.entity.Contact
import com.sharezzorama.smallcity.map.MapFragment
import com.sharezzorama.toMarker
import kotlinx.android.synthetic.main.fragment_map.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.osmdroid.util.BoundingBox
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker

@Layout(id = R.layout.fragment_map)
class ContactsMapFragment : MapFragment() {

    companion object {
        const val SEARCH_RESULT_ITEM_ID_PREFIX = "search_result_"
    }

    private val contactsViewModel: ContactsViewModel by sharedViewModel()
    private val provideAddressFunction: (Contact) -> Address? = { contact ->
        addressViewModel.getBuilding(contact.buildingId)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Observe contact list
        contactsViewModel
                .contactsLiveData
                .observe(this, Observer<List<Contact>> { list -> onContactsUpdated(list) })
    }

    /**
     * Отображает список контактов на карте
     *
     * @param list - Список контактов
     */
    private fun onContactsUpdated(list: List<Contact>) {

        cleanLastSearchResult()

        list.map { contact -> contact.toMarker(mapView, SEARCH_RESULT_ITEM_ID_PREFIX, provideAddressFunction) }
                .forEach { marker -> mapView.overlays.add(marker) }

        zoom(list, provideAddressFunction)
    }

    /**
     * Zoom in on the card to display all contacts
     *
     * @param contacts - List of contacts
     * @param provideAddressFunction - Function returning contact address
     */
    private fun zoom(contacts: List<Contact>, provideAddressFunction: (Contact) -> Address?) {
        if (contacts.isEmpty()) {
            return
        }

        val addresses = contacts.mapNotNull { provideAddressFunction(it) }

        //Animate to single point
        if (addresses.size == 1) {
            val address = addresses[0]
            mapView.controller.animateTo(GeoPoint(address.lat, address.lng))
            return
        }

        //Zoom to bound box in case when exist more than one addresses
        val minLat = addresses.minBy { it.lat }?.lat
        val maxLat = addresses.maxBy { it.lat }?.lat
        val minLon = addresses.minBy { it.lng }?.lng
        val maxLon = addresses.maxBy { it.lng }?.lng
        val boundingBox = BoundingBox()
        boundingBox.set(maxLat!!, maxLon!!, minLat!!, minLon!!)
        mapView.zoomToBoundingBox(
                boundingBox,
                true,
                resources.getDimension(R.dimen.map_search_border_dp).toInt()
        )
    }

    /**
     * Clears up the search result
     */
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