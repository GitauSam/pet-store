package com.zenza.pets.store.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Pet(
    var type: String?,
    var colour: String?,
    var age: Double?
) {
    @Id var id: String? = null
}