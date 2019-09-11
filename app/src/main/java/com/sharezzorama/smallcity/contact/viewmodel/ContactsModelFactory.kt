package com.sharezzorama.smallcity.contact.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sharezzorama.smallcity.datasource.contact.ContactsDataSource

class ContactsModelFactory(private val dataSource: ContactsDataSource) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass == ContactsViewModel::class.java) {
            return ContactsViewModel(dataSource) as T
        }
        return super.create(modelClass)
    }
}