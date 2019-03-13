package com.sharezzorama.smallcity.contact.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sharezzorama.smallcity.contact.ContactsDataSource
import com.sharezzorama.smallcity.data.entity.Contact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ContactsViewModel(private val dataSource: ContactsDataSource) : ViewModel() {
    private val liveData = MutableLiveData<MutableList<Contact>>()

    fun loadContacts(): LiveData<MutableList<Contact>> {
        if (liveData.value == null) {
            val job = GlobalScope.launch(Dispatchers.IO) {
                try {
                    val contacts = dataSource.getAll().await()
                    val mutableListOf = mutableListOf<Contact>()
                    mutableListOf.addAll(contacts)
                    liveData.postValue(mutableListOf)
                } catch (e: RuntimeException) {
                    Log.e("BANANA", "Error", e)
                } finally {

                }
            }
        } else {
            liveData.value?.add(Contact(name = "Temp"))
        }
        return liveData
    }
}