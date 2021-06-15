package com.zenza.pets.store.domain

import java.sql.Timestamp
import java.time.Instant
import javax.persistence.*

@Entity
class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    var type: String? = null
    var colour: String? = null
    var age: Double? = null
    var status: Int? = 1
    var adopted: Int? = 0
    var createdAt: Timestamp? = null
    var createdBy: String? = "admin"
    var modifiedAt: Timestamp? = Timestamp.from(Instant.now())
    var modifiedBy: String? = "admin"
    var deletedAt: Timestamp? = null
    var deletedBy: String? = null
    @OneToMany(mappedBy = "pet", fetch = FetchType.EAGER)
    lateinit var images: List<PetImageProperties>
}