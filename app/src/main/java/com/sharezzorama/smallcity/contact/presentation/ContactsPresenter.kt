package com.sharezzorama.smallcity.contact.presentation

import com.arellomobile.mvp.InjectViewState
import com.sharezzorama.smallcity.base.mvp.BaseMvpPresenter
import com.sharezzorama.smallcity.datasource.contact.ContactsDataSource

@InjectViewState
class ContactsPresenter(private val dataSource: ContactsDataSource) : BaseMvpPresenter<ContactsView>() {

    /* override fun onFirstViewAttach() {
         super.onFirstViewAttach()
         loadAll()
     }

     private fun loadAll() {
         dataSource.getAll()
                 .doOnSubscribe { viewState.showProgress() }
                 .doAfterTerminate { viewState.showProgress(false) }
                 .subscribeBy(
                         onError = { viewState.showError() },
                         onNext = { list -> viewState.showContacts(list) }
                 )
     }*/
}