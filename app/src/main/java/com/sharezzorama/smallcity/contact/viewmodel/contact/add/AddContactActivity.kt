package com.sharezzorama.smallcity.contact.viewmodel.contact.add

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import com.sharezzorama.smallcity.R
import com.sharezzorama.smallcity.data.entity.Address

class AddContactActivity : AppCompatActivity() {

    companion object {
        const val KEY_ARG_ADDRESS = "key_arg_address"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contact)
        val contactAddFragment = if (intent.hasExtra(KEY_ARG_ADDRESS))
            ContactAddFragment.createInstance(intent.getSerializableExtra(KEY_ARG_ADDRESS) as Address)
        else ContactAddFragment()
        supportFragmentManager.commit {
            replace(R.id.contentFrame, contactAddFragment)
        }

    }
}
