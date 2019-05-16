package com.sharezzorama.smallcity.data.entity

data class Contact(
        var id: Int? = null,
        var name: String? = null,
        var description: String? = null,
        var phones: List<Phone> = listOf(),
        var categories: List<Category>? = null,
        var tags: List<Tag>? = null
)