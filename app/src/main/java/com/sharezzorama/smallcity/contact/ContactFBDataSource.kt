package com.sharezzorama.smallcity.contact

/*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.sharezzorama.smallcity.data.entity.Contact
import io.reactivex.Observable
import io.reactivex.Single

class ContactFBDataSource : ContactsDataSource {

    companion object {
        private const val REFERENCE_KEY = "contacts"
    }

    private val contactsReference = FirebaseDatabase.getInstance().getReference(REFERENCE_KEY)

    override fun save(contact: Contact) =
            Single.create<Contact> { emitter ->
                val id = createId()
                contactsReference.child(id).setValue(contact) { error, reference ->
                    if (error != null) {
                        emitter.onError(error.toException())
                    } else {
                        emitter.onSuccess(contact.apply { this.id = id })
                    }
                }
            }

    override suspend fun getAll(): Observable<List<Contact>> =
            Observable.create { emitter ->
                contactsReference.orderByChild("name").startAt("Contact154357459").addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {
                        emitter.onError(error.toException())
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        val map = snapshot.children.map { child -> child.getValue(Contact::class.java)!! }
                        emitter.onNext(map)
                    }
                })
            }

    private fun createId() = contactsReference.push().key!!
}*/
