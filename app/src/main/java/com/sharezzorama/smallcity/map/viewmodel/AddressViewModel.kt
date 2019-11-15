package com.sharezzorama.smallcity.map.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sharezzorama.smallcity.base.AViewModel
import com.sharezzorama.smallcity.data.entity.Address
import com.sharezzorama.smallcity.datasource.map.AddressDataSource
import com.sharezzorama.smallcity.datasource.map.AddressLocalDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
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
                withContext(Dispatchers.Default) { localSource.create(remoteBuildings) }

            } catch (e: Exception) {
                Log.e("BANANA", "Error", e)
            } finally {

            }
        }
    }

    fun getBuilding(id: Int?) = runBlocking {
        if (id != null)
            withContext(Dispatchers.Default) { localSource.getBuildingById(id) }
        null
    }
}