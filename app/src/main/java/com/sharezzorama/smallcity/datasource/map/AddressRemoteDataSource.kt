package com.sharezzorama.smallcity.datasource.map

import com.sharezzorama.smallcity.contact.Rest
import com.sharezzorama.smallcity.data.entity.Address
import kotlinx.coroutines.Deferred

class AddressRemoteDataSource : AddressDataSource {

    override suspend fun getBuildingsAsync(s: String): Deferred<List<Address>> = Rest.apiService.getBuildings(s)}