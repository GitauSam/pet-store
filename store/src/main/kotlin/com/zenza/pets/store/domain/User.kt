package com.zenza.pets.store.domain

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
    @ManyToMany
    @JoinTable(
        name = "users_roles",
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")]
    )
    var roles: Collection<Role>? = null
}