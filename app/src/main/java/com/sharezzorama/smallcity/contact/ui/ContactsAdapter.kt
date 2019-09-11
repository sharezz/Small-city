package com.sharezzorama.smallcity.contact.ui

import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sharezzorama.getInflater
import com.sharezzorama.smallcity.R
import com.sharezzorama.smallcity.contact.viewmodel.ContactsViewModel
import com.sharezzorama.smallcity.data.entity.Contact
import com.sharezzorama.smallcity.databinding.ContactItemBinding
import com.sharezzorama.smallcity.map.viewmodel.AddressViewModel
import kotlinx.android.synthetic.main.contact_item.view.*

class ContactsAdapter(private val contactsViewModel: ContactsViewModel, private val addressViewModel: AddressViewModel) : RecyclerView.Adapter<ContactsAdapter.ContactViewHolder>() {
    val contacts = mutableListOf<Contact>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=
            ContactViewHolder(ContactItemBinding.inflate(parent.getInflater(), parent, false))

    override fun getItemCount() = contacts.size

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(contacts[position])
    }

    inner class ContactViewHolder(val binding: ContactItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(contact: Contact) {
            binding.contact = contact
            binding.building = addressViewModel.getBuilding(contact.buildingId)
            binding.clickListener = View.OnClickListener { contactsViewModel.onContactSelected(contact) }
        }
    }

}