package com.sharezzorama.smallcity.contact.ui

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sharezzorama.smallcity.R
import com.sharezzorama.smallcity.data.entity.Contact
import kotlinx.android.synthetic.main.list_item_contacts.view.*

class ContactsAdapter : RecyclerView.Adapter<ContactsAdapter.ContactViewHolder>() {
    val contacts = mutableListOf<Contact>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder(View.inflate(parent.context, R.layout.list_item_contacts, null))
    }

    override fun getItemCount() = contacts.size

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(contacts[position])
    }

    inner class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(contact: Contact) {
            itemView.name.text = contact.name
            itemView.description.text = contact.description
        }
    }

}