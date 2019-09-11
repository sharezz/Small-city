package com.sharezzorama.smallcity.datasource.map

import com.sharezzorama.smallcity.data.entity.Address
import kotlinx.coroutines.Deferred

interface AddressDataSource {

    suspend fun getBuildingsAsync(): Deferred<List<Address>>
}