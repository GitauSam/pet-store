package com.zenza.pets.store.repository

import com.zenza.pets.store.domain.PetAdoption
import org.springframework.data.jpa.repository.JpaRepository

interface PetAdoptionRepository: JpaRepository<PetAdoption, Long>