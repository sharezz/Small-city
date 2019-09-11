package com.sharezzorama.smallcity.datasource.contact

import com.sharezzorama.smallcity.contact.Rest
import com.sharezzorama.smallcity.datasource.contact.ContactsDataSource.Companion.ITEMS_PER_REQUEST_DEFAULT
import com.sharezzorama.smallcity.data.entity.Contact
import kotlinx.coroutines.Deferred

class ContactRemoteDataSource : ContactsDataSource {

    override fun save(contact: Contact) = Rest.apiService.createContactAsync(contact)

    override suspend fun getAll() = Rest.apiService.getAllContactsAsync()

    override suspend fun searchAsync(query: String, page: Int) = Rest.apiService.searchContactsAsync(query, page, ITEMS_PER_REQUEST_DEFAULT)

    override suspend fun getAllByBuildingAsync(buildingId: Int) = Rest.apiService.getContactsByBuildingAsync(buildingId)
}