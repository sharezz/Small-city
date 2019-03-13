package com.sharezzorama.smallcity.contact

import com.sharezzorama.smallcity.contact.Rest.BASE_URL
import com.sharezzorama.smallcity.data.entity.Contact
import io.ktor.client.HttpClient
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.get
import io.ktor.client.request.post
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class ContactRemoteDataSource : ContactsDataSource {

    override fun save(contact: Contact) =
            GlobalScope.async {
                HttpClient {
                    install(JsonFeature) {
                        serializer = GsonSerializer {
                            serializeNulls()
                            disableHtmlEscaping()
                        }
                    }
                }.use { client ->
                    client.post<Contact>("$BASE_URL/contact/create")
                }
            }

    override suspend fun getAll() = GlobalScope.async {
        HttpClient {
            install(JsonFeature) {
                serializer = GsonSerializer {
                    serializeNulls()
                    disableHtmlEscaping()
                }
            }
        }.use { client ->
            client.get<List<Contact>>("$BASE_URL/contact")
        }
    }
}