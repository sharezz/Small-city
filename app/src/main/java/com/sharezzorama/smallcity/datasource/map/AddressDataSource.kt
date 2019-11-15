package com.sharezzorama.smallcity.datasource.map

import com.sharezzorama.smallcity.data.entity.Address
import kotlinx.coroutines.Deferred
import java.util.*

interface AddressDataSource {

    suspend fun getBuildingsAsync(date: Date): Deferred<List<Address>>
}