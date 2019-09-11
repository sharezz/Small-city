package com.sharezzorama.smallcity.datasource.map

import com.sharezzorama.smallcity.contact.Rest

class AddressRemoteDataSource : AddressDataSource {

    override suspend fun getBuildingsAsync() = Rest.apiService.getBuildings()}