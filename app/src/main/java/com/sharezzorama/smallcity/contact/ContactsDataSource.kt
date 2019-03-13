package com.sharezzorama.smallcity.contact

import com.sharezzorama.smallcity.data.entity.Contact
import kotlinx.coroutines.Deferred

interface ContactsDataSource {

    fun save(contact: Contact): Deferred<Contact>

    suspend fun getAll(): Deferred<List<Contact>>
}