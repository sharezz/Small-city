package com.sharezzorama.smallcity.datasource.contact

import com.sharezzorama.smallcity.data.entity.Contact
import kotlinx.coroutines.Deferred

interface ContactsDataSource {

    companion object{
        const val ITEMS_PER_REQUEST_DEFAULT = 40
    }

    fun save(contact: Contact): Deferred<Contact>

    suspend fun getAll(): Deferred<List<Contact>>

    suspend fun searchAsync(query:String, page:Int): Deferred<List<Contact>>

    suspend fun getAllByBuildingAsync(buildingId:Int): Deferred<List<Contact>>
}