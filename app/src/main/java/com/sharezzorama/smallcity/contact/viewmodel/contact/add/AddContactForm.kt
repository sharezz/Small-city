package com.sharezzorama.smallcity.contact.viewmodel.contact.add

import androidx.databinding.Observable
import androidx.databinding.ObservableField
import com.sharezzorama.smallcity.R
import com.sharezzorama.smallcity.data.entity.Address

class AddContactForm {
    var name = ObservableField<String>()
    val nameError = ObservableField<Int>()

    val description = ObservableField<String>()

    val mobilePhone = ObservableField<String>()
    val mobilePhoneError = ObservableField<Int>()

    val phone = ObservableField<String>()
    var buildingAddress = ObservableField<String>()
    var building = ObservableField<Address>()
            .apply {
                addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {

                    override fun onPropertyChanged(sender: Observable, propertyId: Int) {
                        buildingAddress.set("${get()?.street} ${get()?.housenumber}")
                    }
                })
            }
    val tagInput = ObservableField<String>()

    val schedule: ObservableField<MutableList<String>> = ObservableField()

    fun isValid(): Boolean {
        return isStringValueNotEmpty(name, nameError, R.string.error_empty_field)
                && isStringValueNotEmpty(mobilePhone, mobilePhoneError, R.string.error_empty_field)
    }

    private fun isStringValueNotEmpty(field: ObservableField<String>, errorField: ObservableField<Int>, errorResId: Int): Boolean {
        val value = field.get()
        if (value == null || value.isEmpty()) {
            errorField.set(errorResId)
            return false
        } else {
            errorField.set(null)
        }
        return true
    }
}