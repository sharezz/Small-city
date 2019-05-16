package com.sharezzorama.smallcity.contact.viewmodel.contact.add

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import com.sharezzorama.smallcity.BR
import com.sharezzorama.smallcity.R

class AddContactForm : BaseObservable() {
    var name :String? = null
    set(value) {
        notifyPropertyChanged(BR.valid)}


    val description = ObservableField<String>()
    val mobilePhone = ObservableField<String>()
    val phone = ObservableField<String>()

    val nameError = ObservableField<Int>()

    @Bindable
    fun isValid(): Boolean {
        return isNameValid()
    }

    private fun isNameValid(): Boolean {
        if (name == null || name?.isEmpty() == true) {
            nameError.set(R.string.error_empty_field)
            return false
        } else {
            nameError.set(null)
        }
        return true
    }
}