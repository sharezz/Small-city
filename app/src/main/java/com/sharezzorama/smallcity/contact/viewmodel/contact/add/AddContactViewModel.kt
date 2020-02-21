package com.sharezzorama.smallcity.contact.viewmodel.contact.add

import android.util.Log
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.material.chip.Chip
import com.google.android.material.textfield.TextInputLayout
import com.sharezzorama.smallcity.base.AViewModel
import com.sharezzorama.smallcity.data.entity.*
import com.sharezzorama.smallcity.datasource.contact.ContactsDataSource
import kotlinx.coroutines.launch

class AddContactViewModel(private val dataSource: ContactsDataSource) : AViewModel() {
    private val liveData = MutableLiveData<Contact>()
    val form: AddContactForm = AddContactForm()
    val dataLoading = ObservableBoolean(false)
    val commonError = ObservableField<String>()
    val openPlacePickerEvent = MutableLiveData<Void>()
    val tagList = ObservableArrayList<Tag>()


    fun save(): LiveData<Contact> {
        if (form.isValid()) {
            viewModelScope.launch {
                try {
                    dataLoading.set(true)
                    commonError.set(null)
                    val contact = buildContact()
                    liveData.postValue(dataSource.save(contact).await())
                    dataLoading.set(false)
                } catch (e: Exception) {
                    dataLoading.set(false)
                    commonError.set("Error")
                    Log.e("BANANA", "Error", e)
                } finally {

                }
            }
        }

        return liveData
    }

    fun addLocation(){openPlacePickerEvent.value = null}

    fun onTagTextChanged(s: CharSequence,start: Int,before : Int, count :Int){
        if(s.isNotEmpty()) {
            val last = s[s.length - 1]
            if (last.toString() == ","){
                val tagValue = s.subSequence(0, s.length - 1).toString()
                tagList.add(Tag(0, tagValue))
                form.tagInput.set("")
                form.tagInput.notifyChange()
            }
        }
    }

    fun removeTag(v:View){
        if(v is Chip){
            v.getTag()
        }
    }

    fun locationSelected(address: Address){
        form.building.set(address)
    }

    private fun buildContact() = Contact().apply {
        name = form.name.get()
        description = form.description.get()
        phones = mutableListOf()
        buildingId = form.building.get()?.id?:0
        tags = tagList

        //Phones
        val mutablePhones = mutableListOf<Phone>()
        phones = mutablePhones
        checkPhone(form.mobilePhone, mutablePhones, PhoneTypeEnum.MOBILE)
        checkPhone(form.phone, mutablePhones, PhoneTypeEnum.WORK)

        //Schedule
        form.schedule.set(mutableListOf())
        weekSchedule = form.schedule.get()
    }

    /*
    Проверяет введено ли значение. Если значение введено добавляет телефон в список
     */
    private fun checkPhone(field: ObservableField<String>, phones: MutableList<Phone>, phoneType: PhoneTypeEnum) {
        (if (!field.get().isNullOrEmpty())
            Phone(field.get().toString(), phoneType)
        else null)
                ?.let { phone -> phones.add(phone) }
    }

    companion object {

        @BindingAdapter("error")
        @JvmStatic
        fun setError(textInputLayout: TextInputLayout, errorRes: Any?) {
            if (errorRes == null) {
                textInputLayout.apply { error = null }
            }

            if (errorRes is Int)
                textInputLayout.apply { error = context.getString(errorRes) }
        }
    }

}