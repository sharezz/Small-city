package com.sharezzorama.smallcity

import android.app.Application
import com.google.firebase.FirebaseApp
import com.sharezzorama.smallcity.koin.myModule
import org.koin.android.ext.android.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)

        startKoin(this, listOf(myModule))
    }
}