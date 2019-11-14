package com.sharezzorama.smallcity

import android.app.Application
import androidx.room.Room
import com.google.firebase.FirebaseApp
import com.sharezzorama.smallcity.dao.AppDatabase
import com.sharezzorama.smallcity.koin.myModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {

    lateinit var database: AppDatabase

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        database = Room.databaseBuilder(this@MainApplication, AppDatabase::class.java, "database").build()

        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(myModule)
        }

    }
}