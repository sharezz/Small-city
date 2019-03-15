package com.sharezzorama.smallcity.contact

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.sharezzorama.smallcity.data.entity.Contact
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

object Rest {
    private const val BASE_URL = "http://192.168.206.29:8081/api/"

    val apiService = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
            .create(RestApi::class.java)

    interface RestApi {
        @GET("contact")
        fun getAllContacts(): Deferred<List<Contact>>

        @POST("contact/create")
        fun createContact(@Body contact: Contact): Deferred<Contact>

    }
}