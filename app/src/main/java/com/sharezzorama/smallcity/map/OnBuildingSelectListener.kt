package com.sharezzorama.smallcity.map

import com.sharezzorama.smallcity.data.entity.Address
import org.osmdroid.views.overlay.Marker

interface OnBuildingSelectListener {
    fun onBuildingSelected(building: Address, marker: Marker)
}