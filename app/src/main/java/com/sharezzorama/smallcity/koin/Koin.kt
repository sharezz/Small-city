package com.sharezzorama.smallcity.koin

import androidx.room.Room
import com.sharezzorama.smallcity.MainApplication
import com.sharezzorama.smallcity.datasource.contact.ContactRemoteDataSource
import com.sharezzorama.smallcity.datasource.contact.ContactsDataSource
import com.sharezzorama.smallcity.datasource.map.AddressDataSource
import com.sharezzorama.smallcity.datasource.map.AddressRemoteDataSource
import com.sharezzorama.smallcity.contact.viewmodel.contact.add.AddContactViewModel
import com.sharezzorama.smallcity.contact.viewmodel.ContactsViewModel
import com.sharezzorama.smallcity.dao.AppDatabase
import com.sharezzorama.smallcity.datasource.map.AddressLocalDataSource
import com.sharezzorama.smallcity.map.viewmodel.AddressViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.scope.Scope
import org.koin.dsl.bind
import org.koin.dsl.module

val myModule = module {
    single { ContactRemoteDataSource() } bind ContactsDataSource::class
    single { AddressRemoteDataSource() } bind AddressDataSource::class
    single { AddressLocalDataSource(get()) } bind AddressLocalDataSource::class
    single { (androidContext() as MainApplication).database }
    viewModel { ContactsViewModel(get()) }
    viewModel { AddContactViewModel(get()) }
    viewModel { AddressViewModel(get(), get()) }
}

private fun Scope.appDatabase() =
        Room.databaseBuilder(get(), AppDatabase::class.java, "database").build()