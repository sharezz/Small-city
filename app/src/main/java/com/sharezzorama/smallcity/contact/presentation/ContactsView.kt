package com.sharezzorama.smallcity.contact.presentation

import com.sharezzorama.smallcity.base.mvp.BaseMvpView
import com.sharezzorama.smallcity.entity.Contact

interface ContactsView : BaseMvpView {
    fun showContacts(contacts: List<Contact>)
    fun showProgress(show: Boolean = true)
    fun showError()
}