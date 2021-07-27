package com.zenza.pets.store.repository

import com.zenza.pets.store.domain.Pet
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PetRepository: JpaRepository<Pet, Long> {
    override fun findAll(pageable: Pageable): Page<Pet>
    fun findPetById(id: Long): Pet?
    fun findPetsByType(type: String, pageable: Pageable): Page<Pet>
    fun findPetsByTypeContainingOrColourContaining(type: String, colour: String, pageable: Pageable): Page<Pet>
}