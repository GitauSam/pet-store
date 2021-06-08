package com.zenza.pets.store.repository

import com.zenza.pets.store.domain.PetImageProperties
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PetImagePropertiesRepository: JpaRepository<PetImageProperties, Long> {
    fun findByPet(pet: Long): PetImageProperties?
    fun findByPetAndDocumentType(pet: Long, docFormat: String): PetImageProperties?
}