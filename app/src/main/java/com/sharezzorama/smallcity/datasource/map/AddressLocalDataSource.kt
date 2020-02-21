package com.sharezzorama.smallcity.datasource.map

import com.sharezzorama.smallcity.dao.AppDatabase
import com.sharezzorama.smallcity.data.entity.Address

class AddressLocalDataSource(private val database: AppDatabase) {

    suspend fun getBuildingsAsync() = database.addressDao().getAllAsync()

    fun create(address: Address) {database.addressDao().insert(address)}

    fun create(addressList: List<Address>) {database.addressDao().insertAll(addressList)}

    fun getBuildingById(id: Int) = database.addressDao().findById(id)

}