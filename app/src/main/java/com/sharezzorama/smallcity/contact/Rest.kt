package com.sharezzorama.smallcity.contact

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.sharezzorama.smallcity.data.entity.Address
import com.sharezzorama.smallcity.data.entity.Contact
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

object Rest {
    private const val BASE_URL = "http://192.168.206.29:8081/api/"
    //private const val BASE_URL = "http://18.188.86.22:8081/api/"

    val apiService = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(RestApi::class.java)

    interface RestApi {
        @GET("contact")
        fun getAllContactsAsync(): Deferred<List<Contact>>

        @GET("contact/search")
        fun searchContactsAsync(@Query("query") query: String, @Query("page") page: Int = 0, @Query("size") size: Int = 40): Deferred<List<Contact>>

        @GET("contact/byBuildingId/{buildingId}")
        fun getContactsByBuildingAsync(@Path("buildingId") buildingId: Int): Deferred<List<Contact>>

        @POST("contact")
        fun createContactAsync(@Body contact: Contact): Deferred<Contact>

        @GET("map/buildings")
        fun getBuildings(): Deferred<List<Address>>

    }
}