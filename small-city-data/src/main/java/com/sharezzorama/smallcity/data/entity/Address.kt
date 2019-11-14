package com.sharezzorama.smallcity.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "addresses")
data class Address(@PrimaryKey
                   val id: Int,
                   val externalId: Long,
                   val street: String?,
                   val city: String?,
                   val housenumber: String?,
                   val name: String?,
                   val lat: Double,
                   val lng: Double) : Serializable