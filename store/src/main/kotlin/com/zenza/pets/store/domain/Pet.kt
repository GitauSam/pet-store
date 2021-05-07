package com.zenza.pets.store.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Pet {
    @Id
    @GeneratedValue
    var id: Long? = null
    var type: String? = null
    var colour: String? = null
    var age: Double? = null
    var status: Int? = 1
    var adopted: Int? = 0
}