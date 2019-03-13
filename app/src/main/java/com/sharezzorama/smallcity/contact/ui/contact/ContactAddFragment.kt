package com.sharezzorama.smallcity.contact.ui.contact

import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.sharezzorama.smallcity.R
import com.sharezzorama.smallcity.base.BaseFragment
import com.sharezzorama.smallcity.base.Layout
import com.sharezzorama.smallcity.contact.viewmodel.AddContactViewModel
import com.sharezzorama.smallcity.data.entity.Contact
import com.sharezzorama.smallcity.data.entity.Phone
import com.sharezzorama.smallcity.data.entity.PhoneTypeEnum
import kotlinx.android.synthetic.main.fragment_add_contact.*
import org.koin.androidx.viewmodel.ext.android.viewModel

@Layout(id = R.layout.fragment_add_contact)
class ContactAddFragment : BaseFragment() {

    private val saveContactViewModel: AddContactViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnSave.setOnClickListener {
            saveContactViewModel.save(buildContact())
        }
    }

    private fun buildContact() = Contact().apply {
        name = vName.text.toString()
        description = vDescription.text.toString()
        val phones = mutableListOf<Phone>()
        checkPhone(vMobilePhone, phones, PhoneTypeEnum.MOBILE)
        checkPhone(vPhone, phones, PhoneTypeEnum.WORK)

    }

    /*
    Проверяет введено ли значение. Если значение введено добавляет телефон в список
     */
    private fun checkPhone(textView: TextView, phones: MutableList<Phone>, phoneType: PhoneTypeEnum) {
        (if (!textView.text.isNullOrEmpty())
            Phone(textView.text.toString(), phoneType)
        else null)
                ?.let { phone -> phones.add(phone) }
    }

}