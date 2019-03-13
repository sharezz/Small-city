package com.sharezzorama.smallcity.contact.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.sharezzorama.smallcity.R
import com.sharezzorama.smallcity.base.BaseFragment
import com.sharezzorama.smallcity.base.Layout
import com.sharezzorama.smallcity.contact.viewmodel.ContactsViewModel
import com.sharezzorama.smallcity.data.entity.Contact
import kotlinx.android.synthetic.main.fragment_contacts.*
import org.koin.androidx.viewmodel.ext.android.viewModel

@Layout(id = R.layout.fragment_contacts)
class ContactsFragment : BaseFragment() {

    private val contactsViewModel: ContactsViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = ContactsAdapter()

        contactsViewModel.loadContacts()
                .observe(this, Observer<List<Contact>> { list ->
                    (recyclerView.adapter as ContactsAdapter).apply {
                        contacts.addAll(list)
                        notifyDataSetChanged()
                    }
                })
    }
}