package com.sharezzorama.smallcity.contact

import com.sharezzorama.smallcity.entity.Contact
import io.reactivex.Observable
import io.reactivex.Single

interface ContactsDataSource {

    fun save(contact: Contact): Single<Contact>

    fun getAll(): Observable<List<Contact>>
}