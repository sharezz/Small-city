package com.sharezzorama.smallcity.contact

import com.sharezzorama.smallcity.data.entity.Contact

class ContactRemoteDataSource : ContactsDataSource {

    override fun save(contact: Contact) = Rest.apiService.createContact(contact)

    override suspend fun getAll() = Rest.apiService.getAllContacts()
}