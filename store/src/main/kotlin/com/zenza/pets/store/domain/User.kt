package com.zenza.pets.store.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class User(
        var firstName: String?,
        var lastName: String?,
        var email: String?,
        var phoneNumber: String?,
        var password: String?,
) {
    @Id var id: String? = null
    var status = 0
}