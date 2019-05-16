package com.sharezzorama.smallcity.contact.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.sharezzorama.smallcity.R
import com.sharezzorama.smallcity.base.BaseFragment
import com.sharezzorama.smallcity.base.Layout
import com.sharezzorama.smallcity.contact.viewmodel.ContactsViewModel
import com.sharezzorama.smallcity.data.entity.Contact
import com.sharezzorama.smallcity.databinding.FragmentContactsBinding
import kotlinx.android.synthetic.main.fragment_contacts.*
import org.koin.androidx.viewmodel.ext.android.viewModel

@Layout(id = R.layout.fragment_contacts)
class ContactsFragment : BaseFragment() {

    private val contactsViewModel: ContactsViewModel by viewModel()
    private lateinit var contactsBinding: FragmentContactsBinding;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        contactsBinding = FragmentContactsBinding.inflate(inflater, container, false)
        contactsBinding.viewmodel = contactsViewModel
        return contactsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = ContactsAdapter()

        contactsViewModel.contactsLiveData
                .observe(this, Observer<List<Contact>> { list ->
                    (recyclerView.adapter as ContactsAdapter).apply {
                        contacts.addAll(list)
                        notifyDataSetChanged()
                    }
                })

        contactsViewModel.loadContacts()
    }
}