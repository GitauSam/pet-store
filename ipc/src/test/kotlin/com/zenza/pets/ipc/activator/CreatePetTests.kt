package com.zenza.pets.ipc.activator

import com.ninjasquad.springmockk.MockkBean
import com.zenza.pets.ipc.utils.exceptions.InvalidInputException
import com.zenza.pets.ipc.utils.exceptions.InvalidParameterException
import com.zenza.pets.store.domain.Pet
import com.zenza.pets.store.repository.PetRepository
import io.mockk.every
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest
class CreatePetTests {

    @Autowired private lateinit var createPet: CreatePet
    @MockkBean private lateinit var mockPetRepository: PetRepository

    @Test
    fun testForNullInput() {
        Assertions.assertThrows(InvalidInputException::class.java) { createPet.save(null) }
    }

    @Test
    fun testForNullParameters() {
        val pet = Pet(
            type = null,
            colour = "Black & white",
            age = 0.4
        )

        Assertions.assertThrows(InvalidParameterException::class.java) { createPet.save(pet) }
    }

    @Test
    fun `should create pet by delegating to pet repository`() {
        val pet = Pet(
                type = "Turtle",
                colour = "Black & white",
                age = 0.4
        )

        val savedPet = Pet(
                    type = "Turtle",
                    colour = "Black & white",
                    age = 0.4
                ).apply {
                    id = "persisted"
                }

        every { mockPetRepository.save(pet) } returns savedPet

        assertThat(createPet.save(pet)).isEqualTo(savedPet)
        verify { mockPetRepository.save(pet) }
    }
}