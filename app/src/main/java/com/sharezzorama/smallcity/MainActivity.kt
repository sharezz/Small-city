package com.sharezzorama.smallcity

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.sharezzorama.smallcity.utils.PermissionsUtils
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val navController by lazy(LazyThreadSafetyMode.NONE) {
        Navigation.findNavController(this, R.id.nav_host_fragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        PermissionsUtils.requestPermissionsSafely(
                activity = this,
                permissions =
                listOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE), requestCode = 45)
        NavigationUI.setupWithNavController(bottomNavigation, navController)
    }
}
