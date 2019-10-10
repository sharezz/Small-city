package com.sharezzorama.smallcity.data.entity

import java.io.Serializable

data class Address(val id: Int,
                   val externalId: Long,
                   val street: String?,
                   val city: String?,
                   val housenumber: String?,
                   val name: String?,
                   val lat: Double,
                   val lng: Double) : Serializable