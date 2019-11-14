package com.sharezzorama.smallcity.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sharezzorama.smallcity.data.entity.Address
import kotlinx.coroutines.Deferred

@Dao
interface AddressDao {
    /*  @Query("SELECT * FROM addresses")
      abstract suspend fun getAll(): Deferred<List<Address>>*/

    @Insert
     fun insert(address: Address)

    @Insert
     suspend fun insertAll(addressList: List<Address>)
}