package com.zenza.pets.store.domain

import java.sql.Timestamp
import java.time.Instant
import javax.persistence.*


@Entity
@Table(name = "users")
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    var firstName: String? = null
    var lastName: String? = null
    var email: String? = null
    var username: String? = null
    var phoneNumber: String? = null
    var password: String? = null
    var status = 0
    var createdAt: Timestamp? = null
    var createdBy: String? = "admin"
    var modifiedAt: Timestamp? = Timestamp.from(Instant.now())
    var modifiedBy: String = "admin"
    var deletedBy: String? = null
    var deletedAt: Timestamp? = Timestamp.from(Instant.now())
    @ManyToMany
    @JoinTable(
        name = "users_roles",
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")]
    )
    var roles: Collection<Role>? = null
}