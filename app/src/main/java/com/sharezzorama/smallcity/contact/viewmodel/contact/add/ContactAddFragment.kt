package com.sharezzorama.smallcity.contact.viewmodel.contact.add

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.text.method.DigitsKeyListener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import com.redmadrobot.inputmask.MaskedTextChangedListener
import com.sharezzorama.smallcity.ProjectConstants
import com.sharezzorama.smallcity.R
import com.sharezzorama.smallcity.base.BaseFragment
import com.sharezzorama.smallcity.base.Layout
import com.sharezzorama.smallcity.contact.viewmodel.contact.add.AddContactViewModel
import com.sharezzorama.smallcity.data.entity.Address
import com.sharezzorama.smallcity.databinding.FragmentAddContactBinding
import com.sharezzorama.smallcity.placepicker.PlacePickerActivity
import com.sharezzorama.smallcity.placepicker.PlacePickerFragment
import kotlinx.android.synthetic.main.fragment_add_contact.*
import kotlinx.android.synthetic.main.layout_schedule.*
import org.koin.androidx.viewmodel.ext.android.viewModel

@Layout(id = R.layout.fragment_add_contact)
class ContactAddFragment : BaseFragment(), AddContactNavigator {

    companion object {
        const val PICKER_REQUEST_CODE = 1
        const val KEY_ARG_ADRESS = "arg_address"

        fun createInstance(address: Address) = ContactAddFragment().apply {
            arguments = bundleOf(KEY_ARG_ADRESS to address)
        }
    }

    private val saveContactViewModel: AddContactViewModel by viewModel()
    private lateinit var binding: FragmentAddContactBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAddContactBinding.inflate(inflater, container, false)
        binding.viewmodel = saveContactViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        saveContactViewModel.openPlacePickerEvent.observe(this, Observer { openMap() })

        if(arguments?.containsKey(KEY_ARG_ADRESS) == true){
            saveContactViewModel.locationSelected(arguments?.getSerializable(KEY_ARG_ADRESS) as Address)
        }

        //Init phone fields
        vMobilePhone.inputType = InputType.TYPE_CLASS_NUMBER
        vMobilePhone.keyListener = DigitsKeyListener.getInstance("1234567890+-() ")
        MaskedTextChangedListener.Companion.installOn(vMobilePhone, ProjectConstants.MOBILE_PHONE_PATTERN)
        vPhone.inputType = InputType.TYPE_CLASS_NUMBER
        vPhone.keyListener = DigitsKeyListener.getInstance("1234567890-")
        MaskedTextChangedListener.Companion.installOn(vPhone, ProjectConstants.CITY_PHONE_PATTERN)
    }

    override fun openMap() {
        startActivityForResult(Intent(context, PlacePickerActivity::class.java), PICKER_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PICKER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            saveContactViewModel.locationSelected(data?.getSerializableExtra(PlacePickerFragment.KEY_PICKED_PLACE) as Address)
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}