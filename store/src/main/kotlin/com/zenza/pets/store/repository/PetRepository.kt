package com.zenza.pets.store.repository

import com.zenza.pets.store.domain.Pet
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PetRepository: JpaRepository<Pet, Long>