package com.sharezzorama.smallcity.koin

import com.sharezzorama.smallcity.contact.ContactRemoteDataSource
import com.sharezzorama.smallcity.contact.ContactsDataSource
import com.sharezzorama.smallcity.contact.viewmodel.AddContactViewModel
import com.sharezzorama.smallcity.contact.viewmodel.ContactsViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val myModule = module {
    single { ContactRemoteDataSource/*ContactFBDataSource*/() } bind ContactsDataSource::class
    viewModel { ContactsViewModel(get()) }
    viewModel { AddContactViewModel(get()) }
}