package com.sharezzorama.smallcity.datasource.map

import com.sharezzorama.smallcity.contact.Rest
import com.sharezzorama.smallcity.data.entity.Address
import com.sharezzorama.smallcity.data.entity.converters.DateConverter
import kotlinx.coroutines.Deferred
import java.util.*

class AddressRemoteDataSource : AddressDataSource {

    override suspend fun getBuildingsAsync(date: Date): Deferred<List<Address>> = Rest
            .apiService
            .getBuildings(DateConverter.dateToString(date))
}