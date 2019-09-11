package com.sharezzorama.smallcity

import android.app.Application
import com.google.firebase.FirebaseApp
import com.sharezzorama.smallcity.koin.myModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)

        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(myModule)
        }

    }
}