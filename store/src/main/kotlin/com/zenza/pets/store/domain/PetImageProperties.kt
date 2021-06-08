package com.zenza.pets.store.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class PetImageProperties {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    var pet: Long? = null
    var status: Int? = 1
    var path: String? = "api/src/main/resources/assets/images/pets"
    var fileName: String? = null
    var documentType: String? = null
    var documentFormat: String? = null
}