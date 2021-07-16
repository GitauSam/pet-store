package com.zenza.pets.store.domain

import java.sql.Timestamp
import java.time.Instant
import javax.persistence.*

@Entity
class PetAdoption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    @Column(nullable = false)
    var user_id: Long? = null
    @Column(nullable = false)
    var pet_id: Long? = null
    var status: Int = 0
    var approved: Int = 0
    @Column(name = "approved_at", nullable = false)
    var approvedAt: Timestamp? = null
    @Column(name = "approved_by", nullable = false)
    var approvedBy: String? = null
    @Column(name = "created_at", nullable = false)
    var createdAt: Timestamp? = null
    @Column(name = "created_by", nullable = false)
    var createdBy: String? = null
    @Column(name = "modified_at", nullable = false)
    var modifiedAt: Timestamp? = Timestamp.from(Instant.now())
    @Column(name = "modified_by", nullable = false)
    var modifiedBy: String? = null
    @Column(name = "deleted_at", nullable = false)
    var deletedAt: Timestamp? = null
    @Column(name = "deleted_by", nullable = false)
    var deletedBy: Timestamp? = null
}