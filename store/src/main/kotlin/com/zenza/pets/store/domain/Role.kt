package com.zenza.pets.store.domain

import lombok.AllArgsConstructor
import lombok.NoArgsConstructor
import javax.persistence.*


@Entity
@AllArgsConstructor
@NoArgsConstructor
class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null
    var name: String? = null
    @ManyToMany(mappedBy = "roles")
    var users: Collection<User>? = null

    @ManyToMany
    @JoinTable(
        name = "roles_privileges",
        joinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "privilege_id", referencedColumnName = "id")]
    )
    var privileges: Collection<Privilege>? = null
}