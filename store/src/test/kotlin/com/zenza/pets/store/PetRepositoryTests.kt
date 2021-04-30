package com.zenza.pets.store

import com.zenza.pets.store.domain.Pet
import com.zenza.pets.store.repository.PetRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class PetRepositoryTests {

    @Autowired
    lateinit var petRepository: PetRepository

    @Test
    fun savePet() {
        val pet = petRepository.save(Pet(
                type = "Cat",
                colour = "Black & white",
                age = 0.5
        ))

        println(pet)
    }

    @Test
    fun indexPets() {
        val pets = petRepository.findAll()

        for (p in pets) {
            println(p)
        }
    }

    @AfterEach
    fun tearDown() {

    }

}