package com.sharezzorama.smallcity.contact.viewmodel

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.viewModelScope
import com.sharezzorama.smallcity.data.entity.Address
import com.sharezzorama.smallcity.datasource.contact.ContactsDataSource
import com.sharezzorama.smallcity.data.entity.Contact
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class ContactsViewModel(private val dataSource: ContactsDataSource) : ViewModel() {
    val contactsLiveData = MutableLiveData<MutableList<Contact>>()
    val dataLoading = ObservableBoolean(false)
    val dataLoadingError = ObservableBoolean(false)
    val count = ObservableInt(-1)
    val addContactEvent = MutableLiveData<Address?>()

    fun loadContacts() {
        count.set(-1)
        if (contactsLiveData.value == null) {
            viewModelScope.launch {
                try {
                    dataLoading.set(true)
                    dataLoadingError.set(false)
                    val contacts = dataSource.getAll().await()
                    contactsLiveData.postValue(contacts.toMutableList())
                    count.set(contacts.size)
                } catch (e: Exception) {
                    dataLoadingError.set(true)
                    Log.e("BANANA", "Error", e)
                } finally {
                    dataLoading.set(false)
                }
            }
        }
    }

    fun search(query:String){
        count.set(-1)
        contactsLiveData.value?.clear()
       // if (contactsLiveData.value == null) {
            viewModelScope.launch {
                try {
                    dataLoading.set(true)
                    dataLoadingError.set(false)
                    val contacts = dataSource.searchAsync(query, 0).await()
                    contactsLiveData.postValue(contacts.toMutableList())
                    count.set(contacts.size)
                } catch (e: Exception) {
                    dataLoadingError.set(true)
                    Log.e("BANANA", "Error", e)
                } finally {
                    dataLoading.set(false)
                }
            }
    }

    fun getByBuilding(buildingId:Int){
        count.set(-1)
        contactsLiveData.value?.clear()
        // if (contactsLiveData.value == null) {
        viewModelScope.launch {
            try {
                dataLoading.set(true)
                dataLoadingError.set(false)
                val contacts = dataSource.getAllByBuildingAsync(buildingId).await()
                contactsLiveData.postValue(contacts.toMutableList())
                count.set(contacts.size)
            } catch (e: Exception) {
                dataLoadingError.set(true)
                Log.e("BANANA", "Error", e)
            } finally {
                dataLoading.set(false)
            }
        }
    }

    fun addContact(building:Address?){ addContactEvent.value = building}
    fun onContactSelected(contact: Contact) {

    }
}