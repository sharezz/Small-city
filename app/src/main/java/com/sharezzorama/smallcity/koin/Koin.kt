package com.sharezzorama.smallcity.koin

import com.sharezzorama.smallcity.datasource.contact.ContactRemoteDataSource
import com.sharezzorama.smallcity.datasource.contact.ContactsDataSource
import com.sharezzorama.smallcity.datasource.map.AddressDataSource
import com.sharezzorama.smallcity.datasource.map.AddressRemoteDataSource
import com.sharezzorama.smallcity.contact.viewmodel.contact.add.AddContactViewModel
import com.sharezzorama.smallcity.contact.viewmodel.ContactsViewModel
import com.sharezzorama.smallcity.map.viewmodel.AddressViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module

val myModule = module {
    single { ContactRemoteDataSource() } bind ContactsDataSource::class
    single { AddressRemoteDataSource() } bind AddressDataSource::class
    viewModel { ContactsViewModel(get()) }
    viewModel { AddContactViewModel(get()) }
    viewModel { AddressViewModel(get()) }
}