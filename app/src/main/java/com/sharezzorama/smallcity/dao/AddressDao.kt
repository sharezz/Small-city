package com.sharezzorama.smallcity.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sharezzorama.smallcity.data.entity.Address

@Dao
abstract class AddressDao {
    @Query("SELECT * FROM addresses")
    abstract fun getAll(): List<Address>

    @Query("SELECT * FROM addresses WHERE id=:id")
    abstract fun findById(id: Int): Address

    @Insert
    abstract fun insert(address: Address)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(addressList: List<Address>)

    open suspend fun getAllAsync(): List<Address> {
        return getAll()
    }

    open suspend fun findByIdAsync(id:Int) = findById(id)
}