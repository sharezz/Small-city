package com.sharezzorama

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import com.sharezzorama.smallcity.R
import com.sharezzorama.smallcity.data.entity.Address
import com.sharezzorama.smallcity.data.entity.Contact
import kotlinx.android.synthetic.main.fragment_map.*
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

fun View.getInflater() = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

fun Activity.hideKeyboard() {
    (getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager).let { inputMethodManager ->
        val view = if (currentFocus != null) currentFocus else View(this)
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

/**
 * Конвертирует контакт в маркер
 *
 * @param mapView - MapView
 * @param provideAddress - Функция возвращающая адрес контакта
 * @return - Marker ot null if address is not available
 */
fun Contact.toMarker(mapView: MapView, markerIdPrefix: String, provideAddress: (Contact) -> Address?): Marker? {
    provideAddress(this)?.let { address ->
        return address
                .toMarker(mapView)
                .apply {
                    title = name
                    icon = ContextCompat.getDrawable(mapView.context, R.drawable.ic_map_marker)
                    subDescription = "$description, ${address.street} ${address.housenumber}"
                    id = "$markerIdPrefix${address.id}"
                }
    }
    return null
}

/**
 * Converts the address into a marker
 * @param mapView - MapView
 * @return - Marker
 *
 */
fun Address.toMarker(mapView: MapView) =
        Marker(mapView)
                .apply {
                   // alpha = 0F
                    icon = ContextCompat.getDrawable(mapView.context, R.drawable.ic_circle_medium)
                    position = GeoPoint(lat, lng)
                }