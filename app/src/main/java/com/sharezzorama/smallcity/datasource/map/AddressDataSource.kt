package com.sharezzorama.smallcity.datasource.map

import com.sharezzorama.smallcity.data.entity.Address
import kotlinx.coroutines.Deferred

interface AddressDataSource {

    suspend fun getBuildingsAsync(s: String): Deferred<List<Address>>
}