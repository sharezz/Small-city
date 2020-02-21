package com.sharezzorama.smallcity.map.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sharezzorama.smallcity.base.AViewModel
import com.sharezzorama.smallcity.data.entity.Address
import com.sharezzorama.smallcity.datasource.map.AddressDataSource
import com.sharezzorama.smallcity.datasource.map.AddressLocalDataSource
import kotlinx.coroutines.*
import java.util.*

class AddressViewModel(private val addressDataSource: AddressDataSource,
                       private val localSource: AddressLocalDataSource) : AViewModel() {
    val buildingsLiveData = MutableLiveData<List<Address>>()

    fun loadBuildings() {
        viewModelScope.launch {
            try {
                //Local
                val buildings = withContext(Dispatchers.Default) { localSource.getBuildingsAsync() }
                buildingsLiveData.postValue(buildings)
                //Remote
                val lastUpdated = buildings.maxBy { it.updated }?.updated ?: Date(0)
                val remoteBuildings = addressDataSource.getBuildingsAsync(lastUpdated).await()
                buildingsLiveData.postValue(buildings)
                withContext(Dispatchers.Default) { localSource.create(remoteBuildings) }

            } catch (e: Exception) {
                Log.e("BANANA", "Error", e)
            } finally {

            }
        }
    }

     fun getBuilding(id: Int) = runBlocking {
            withContext(Dispatchers.Default) { localSource.getBuildingById(id) }
    }
}