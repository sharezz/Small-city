package com.sharezzorama.smallcity.contact.ui.contact

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sharezzorama.smallcity.R
import com.sharezzorama.smallcity.base.BaseFragment
import com.sharezzorama.smallcity.base.Layout
import com.sharezzorama.smallcity.contact.viewmodel.contact.add.AddContactViewModel
import com.sharezzorama.smallcity.databinding.FragmentAddContactBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

@Layout(id = R.layout.fragment_add_contact)
class ContactAddFragment : BaseFragment() {

    private val saveContactViewModel: AddContactViewModel by viewModel()
    private lateinit var binding: FragmentAddContactBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAddContactBinding.inflate(inflater, container, false)
        binding.viewmodel = saveContactViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}