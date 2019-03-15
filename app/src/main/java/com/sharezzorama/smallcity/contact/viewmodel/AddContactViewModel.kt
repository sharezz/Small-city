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

class AddContactViewModel(private val dataSource: ContactsDataSource) : ViewModel() {
    private val liveData = MutableLiveData<Contact>()

    fun save(contact: Contact): LiveData<Contact> {

        val job = GlobalScope.launch(Dispatchers.IO) {
                try {
                    val contact = dataSource.save(contact).await()
                    liveData.postValue(contact)
                } catch (e: RuntimeException) {
                    Log.e("BANANA", "Error", e)
                } finally {

                }
            }

        return liveData
    }
}