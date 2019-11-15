package com.sharezzorama.smallcity.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.sharezzorama.smallcity.data.entity.converters.DateConverter
import java.io.Serializable
import java.util.*

@Entity(tableName = "addresses")
@TypeConverters(DateConverter::class)
data class Address(@PrimaryKey
                   val id: Int,
                   val externalId: Long,
                   val street: String?,
                   val city: String?,
                   val housenumber: String?,
                   val name: String?,
                   val lat: Double,
                   val lng: Double,
                   val created: Date,
                   val updated: Date
) : Serializable