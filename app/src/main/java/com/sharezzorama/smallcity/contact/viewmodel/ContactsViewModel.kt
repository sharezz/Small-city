package com.sharezzorama.smallcity.contact.viewmodel

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sharezzorama.smallcity.contact.ContactsDataSource
import com.sharezzorama.smallcity.data.entity.Contact
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class ContactsViewModel(private val dataSource: ContactsDataSource) : ViewModel() {
    val contactsLiveData = MutableLiveData<List<Contact>>()
    val dataLoading = ObservableBoolean(false)
    val dataLoadingError = ObservableBoolean(false)

    fun loadContacts() {
        if (contactsLiveData.value == null) {

            viewModelScope.launch {
                try {
                    dataLoading.set(true)
                    dataLoadingError.set(false)
                    val contacts = dataSource.getAll().await()
                    contactsLiveData.postValue(contacts)
                } catch (e: Exception) {
                    dataLoadingError.set(true)
                    Log.e("BANANA", "Error", e)
                } finally {
                    dataLoading.set(false)
                }
            }
        }
    }
}