package com.sharezzorama.smallcity.map.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sharezzorama.smallcity.base.AViewModel
import com.sharezzorama.smallcity.data.entity.Address
import com.sharezzorama.smallcity.datasource.map.AddressDataSource
import com.sharezzorama.smallcity.datasource.map.AddressLocalDataSource
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class AddressViewModel(private val addressDataSource: AddressDataSource,
                       private val localSource:AddressLocalDataSource) : AViewModel() {
    val buildingsLiveData = MutableLiveData<Map<Int, Address>>()

    fun loadBuildings() {
        viewModelScope.launch {
            try {

                val buildingsLocal = viewModelScope.async { localSource.getBuildingsAsync() }
                val buildings = addressDataSource.getBuildingsAsync("2020-01-01 01:01:01").await()
                val map = buildings.associateBy { building -> building.id }
                localSource.create(buildings)
                buildingsLiveData.postValue(map)
            } catch (e: Exception) {
                Log.e("BANANA", "Error", e)
            } finally {

            }
        }
    }

    fun getBuilding(id: Int?) = if (id != null) buildingsLiveData.value?.get(id) else null
}