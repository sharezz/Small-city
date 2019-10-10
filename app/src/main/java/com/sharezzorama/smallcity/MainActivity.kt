package com.sharezzorama.smallcity

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.sharezzorama.hideKeyboard
import com.sharezzorama.smallcity.contact.viewmodel.ContactsViewModel
import com.sharezzorama.smallcity.contact.viewmodel.contact.add.AddContactActivity
import com.sharezzorama.smallcity.data.entity.Address
import com.sharezzorama.smallcity.data.entity.Contact
import com.sharezzorama.smallcity.databinding.ActivityMainBinding
import com.sharezzorama.smallcity.map.OnBuildingSelectListener
import com.sharezzorama.smallcity.map.viewmodel.AddressViewModel
import com.sharezzorama.smallcity.utils.PermissionsUtils
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.osmdroid.views.overlay.Marker


class MainActivity : AppCompatActivity(), OnBuildingSelectListener {
    private lateinit var binding: ActivityMainBinding

    private val navController by lazy(LazyThreadSafetyMode.NONE) {
        Navigation.findNavController(this, R.id.nav_host_fragment)
    }

    private val contactsViewModel: ContactsViewModel by viewModel()
    protected val addressViewModel: AddressViewModel by viewModel()
    lateinit var bottomSheetBehavior: BottomSheetBehavior<View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewmodel = contactsViewModel
        binding.lifecycleOwner = this

        bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet_layout)
        PermissionsUtils.requestPermissionsSafely(
                activity = this,
                permissions =
                listOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE), requestCode = 45)
        NavigationUI.setupWithNavController(bottomNavigation, navController)

        contactsViewModel.contactsLiveData
                .observe(this, Observer<List<Contact>> { list ->
                    hideKeyboard()
                })

        contactsViewModel.addContactEvent.observe(this, Observer { addContact(it) })
    }

    private fun addContact(address: Address?) {
        startActivity(Intent(this, AddContactActivity::class.java).apply {
            if (address != null)
                putExtra(AddContactActivity.KEY_ARG_ADDRESS, address)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search, menu)
        menu?.findItem(R.id.action_search)?.actionView?.let { actionView ->
            (actionView as SearchView).apply {
                isSubmitButtonEnabled = true
                queryHint = getString(R.string.search_query_hint)
                this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String): Boolean {
                        binding.building = null
                        openContactsSheet()
                        //hideKeyboard()
                        contactsViewModel.search(query)
                        return true
                    }

                    override fun onQueryTextChange(query: String): Boolean {
                        //contactsViewModel.search(query)
                        return true
                    }
                })
            }
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onBuildingSelected(building: Address, marker: Marker) {
        binding.building = building
        contactsViewModel.getByBuilding(building.id)
        openContactsSheet()
    }

    private fun openContactsSheet() {
        if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    private fun closeContactsSheet() {
        if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }
}
