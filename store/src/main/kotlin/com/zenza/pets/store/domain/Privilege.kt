package com.zenza.pets.store.domain

import lombok.AllArgsConstructor
import lombok.NoArgsConstructor
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.*

@Entity
@AllArgsConstructor
@NoArgsConstructor
class Privilege {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null
    var name: String? = null
//    @ManyToMany(mappedBy = "privileges")
//    var roles: Collection<Role>? = null
}