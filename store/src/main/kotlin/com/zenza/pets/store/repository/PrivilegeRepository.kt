package com.zenza.pets.store.repository

import com.zenza.pets.store.domain.Privilege
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PrivilegeRepository: JpaRepository<Privilege, Long> {
    fun findByName(name: String): Privilege?
}