package com.zenza.pets.store.repository

import com.zenza.pets.store.domain.Pet
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface PetRepository: MongoRepository<Pet, String>