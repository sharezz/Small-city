package com.sharezzorama.smallcity.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sharezzorama.smallcity.data.entity.Address

@Database(entities = [Address::class], version = 1)
abstract class AppDatabase: RoomDatabase(){
    abstract fun addressDao(): AddressDao
}