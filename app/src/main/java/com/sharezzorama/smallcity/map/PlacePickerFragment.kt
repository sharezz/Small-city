package com.sharezzorama.smallcity.map

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import kotlinx.android.synthetic.main.fragment_map.*
import org.osmdroid.events.MapListener
import org.osmdroid.views.MapView
import org.osmdroid.api.IGeoPoint
import android.view.InputDevice
import android.widget.Toast


class PlacePickerFragment : MapFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView.setOnGenericMotionListener(object : View.OnGenericMotionListener {
            override fun onGenericMotion(view: View, event: MotionEvent): Boolean {
                if (0 != event.getSource() and InputDevice.SOURCE_CLASS_POINTER) {
                    when (event.getAction()) {
                        MotionEvent.ACTION_SCROLL -> {
                            if (event.getAxisValue(MotionEvent.AXIS_VSCROLL) < 0.0f)
                                mapView.controller.zoomOut()
                            else {
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
}