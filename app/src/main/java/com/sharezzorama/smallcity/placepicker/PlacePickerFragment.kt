package com.sharezzorama.smallcity.placepicker

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.sharezzorama.smallcity.R
import com.sharezzorama.smallcity.base.Layout
import com.sharezzorama.smallcity.data.entity.Address
import com.sharezzorama.smallcity.map.MapFragment
import org.osmdroid.views.overlay.Marker

@Layout(id = R.layout.fragment_map)
class PlacePickerFragment : MapFragment() {

    companion object {
        const val KEY_PICKED_PLACE = "picked_place"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*mapView.setOnGenericMotionListener(object : View.OnGenericMotionListener {
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
        })*/
    }


    override fun onBuildingSelected(building: Address, marker: Marker) {
        super.onBuildingSelected(building, marker)
        activity?.apply {
            setResult(Activity.RESULT_OK, Intent().apply { putExtra(KEY_PICKED_PLACE, building) })
            finish()
        }
    }
}