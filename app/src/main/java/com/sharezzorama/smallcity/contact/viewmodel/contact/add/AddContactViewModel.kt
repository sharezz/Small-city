package com.sharezzorama.smallcity.contact.viewmodel.contact.add

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.material.textfield.TextInputLayout
import com.sharezzorama.smallcity.base.AViewModel
import com.sharezzorama.smallcity.contact.ContactsDataSource
import com.sharezzorama.smallcity.data.entity.Contact
import com.sharezzorama.smallcity.data.entity.Phone
import com.sharezzorama.smallcity.data.entity.PhoneTypeEnum
import kotlinx.coroutines.launch
import java.lang.Exception

class AddContactViewModel(private val dataSource: ContactsDataSource) : AViewModel() {
    private val liveData = MutableLiveData<Contact>()
    val form: AddContactForm = AddContactForm()
    val dataLoading = ObservableBoolean(false)
    val commonError = ObservableField<String>()

    fun save(): LiveData<Contact> {

        if (form.isValid()) {
            viewModelScope.launch {
                try {
                    dataLoading.set(true)
                    commonError.set(null)
                    liveData.postValue(dataSource.save(buildContact()).await())
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

    private fun buildContact() = Contact().apply {
        name = form.name
        description = form.description.get()
        phones = mutableListOf()
        val mutablePhones = mutableListOf<Phone>()
        phones = mutablePhones
        checkPhone(form.mobilePhone, mutablePhones, PhoneTypeEnum.MOBILE)
        checkPhone(form.phone, mutablePhones, PhoneTypeEnum.WORK)
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

      /*  @BindingAdapter("error")
        @JvmStatic
        fun setError(editText: EditText, errorRes: Any?) {
            if (errorRes == null) {
                editText.apply { error = null }
            }

            if (errorRes is Int)
                editText.apply { error = context.getString(errorRes) }
        }
*/
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