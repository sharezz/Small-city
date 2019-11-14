package com.sharezzorama.smallcity.datasource.map

import com.sharezzorama.smallcity.dao.AppDatabase
import com.sharezzorama.smallcity.data.entity.Address
import kotlinx.coroutines.Deferred

class AddressLocalDataSource(private val database: AppDatabase) {

    // suspend fun getBuildingsAsync() = database.addressDao().getAll()

    suspend fun create(address: Address) {database.addressDao().insert(address)}

    suspend fun create(addressList: List<Address>) {database.addressDao().insertAll(addressList)}

}