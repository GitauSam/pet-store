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
    @Column(name = "created_at")
    var createdAt: Timestamp? = null
    @Column(name = "created_by")
    var createdBy: String? = "admin"
    @Column(name = "modified_at")
    var modifiedAt: Timestamp? = Timestamp.from(Instant.now())
    @Column(name = "modified_by")
    var modifiedBy: String = "admin"
    @Column(name = "deleted_by")
    var deletedBy: String? = null
    @Column(name = "deleted_at")
    var deletedAt: Timestamp? = null
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "users_roles",
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")]
    )
    var roles: Collection<Role>? = null
}