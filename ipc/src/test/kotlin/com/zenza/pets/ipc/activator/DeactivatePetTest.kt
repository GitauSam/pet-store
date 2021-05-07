package com.zenza.pets.ipc.activator

import com.ninjasquad.springmockk.MockkBean
import com.zenza.pets.ipc.utils.exceptions.InvalidInputException
import com.zenza.pets.ipc.utils.exceptions.InvalidParameterException
import com.zenza.pets.store.domain.Pet
import com.zenza.pets.store.repository.PetRepository
import io.mockk.every
import io.mockk.verify
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest
internal class DeactivatePetTest {

    @MockkBean private lateinit var petRepository: PetRepository
    @Autowired private lateinit var deactivatePet: DeactivatePet

    @Test
    fun `should throw InvalidInputException when passed argument is null`() {
        Assertions.assertThatExceptionOfType(InvalidInputException::class.java)
                .isThrownBy { deactivatePet.deactivate(null) }
                .withMessage("%s", "Expected input pet is null")
                .withMessageContaining("Expected input pet is null")
                .withNoCause()
    }

    @Test
    fun `should throw InvalidParameterException when input of passed argument is null`() {
        val savedPet = Pet().apply {
            id = null
            status = 0
            type = "Horse"
            colour = "Black & White"
            age = 0.4
        }

        Assertions.assertThatExceptionOfType(InvalidParameterException::class.java)
                .isThrownBy { deactivatePet.deactivate(savedPet) }
                .withMessage("%s", "Expected param id of input pet is null")
                .withMessageContaining("Expected param id of input pet is null")
                .withNoCause()
    }

    @Test
    fun `should deactivate pet entity by delegating to pet repository`() {
        val savedPet = Pet(

        ).apply {
            id = 1
            status = 0
            type = "Horse"
            colour = "Black & White"
            age = 0.4
        }

        every { petRepository.save(savedPet) } returns savedPet

        Assertions.assertThat(deactivatePet.deactivate(savedPet)).isEqualTo(savedPet)
        verify { petRepository.save(savedPet) }
    }
}