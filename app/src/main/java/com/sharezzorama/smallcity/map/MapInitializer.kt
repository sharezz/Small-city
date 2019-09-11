package com.sharezzorama.smallcity.map

import androidx.annotation.DrawableRes
import org.osmdroid.util.GeoPoint

data class MapInitializer (var startGeoPoint:GeoPoint,
                           var zoom:Double,
                           @DrawableRes var buildingMarkerIcon: Int,
                           var showZoomButtons:Boolean)