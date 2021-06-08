package com.zenza.pets.store

import com.zenza.pets.store.domain.Pet
import com.zenza.pets.store.repository.PetRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

@SpringBootTest
class PetRepositoryTests {

    @Autowired
    lateinit var petRepository: PetRepository

    @Test
    fun savePet() {
        val pet = petRepository.save(Pet().apply {
                type = "Cat"
                colour = "Black & white"
                age = 0.5
            }
        )

        println(pet)
    }

    @Test
    fun indexPets() {
        val pets = petRepository.findAll()

        println(pets.size)

        for (p in pets) {
            println(p)
        }
    }

    @Test
    fun indexPagedPets() {
        val pets = petRepository.findAll(PageRequest.of(0, 5, Sort.by("createdAt").descending()))

        println(pets)
    }

    @AfterEach
    fun tearDown() {

    }

}