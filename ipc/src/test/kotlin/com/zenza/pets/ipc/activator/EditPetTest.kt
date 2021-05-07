package com.zenza.pets.ipc.activator

import com.ninjasquad.springmockk.MockkBean
import com.zenza.pets.ipc.utils.exceptions.InvalidInputException
import com.zenza.pets.ipc.utils.exceptions.InvalidParameterException
import com.zenza.pets.store.domain.Pet
import com.zenza.pets.store.repository.PetRepository
import io.mockk.every
import io.mockk.verify
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.io.IOException


@ExtendWith(SpringExtension::class)
@SpringBootTest
internal class EditPetTest {

    @MockkBean lateinit var petRepository: PetRepository
    @Autowired lateinit var editPet: EditPet

    @Test
    fun `should throw InvalidInputException when passed null argument`() {
        assertThatExceptionOfType(InvalidInputException::class.java)
                .isThrownBy { editPet.update(null) }
                .withMessage("%s", "Expected input pet is null")
                .withMessageContaining("Expected input pet is null")
                .withNoCause()
    }

    @Test
    fun `should throw InvalidParameterException when passed argument with null params`() {
        val savedPet = Pet().apply {
            id = 1
            type = "Horse"
            colour = "Black & White"
            age = null
        }

        assertThatExceptionOfType(InvalidParameterException::class.java)
                .isThrownBy { editPet.update(savedPet) }
                .withMessage("%s", "Expected param age of input pet is null")
                .withMessageContaining("Expected param age of input pet is null")
                .withNoCause()
    }

    @Test
    fun `should persist changes to pet entity by delegating to pet repository`() {
        val savedPet = Pet().apply {
            id = 1
            type = "Horse"
            colour = "Black & White"
            age = 0.4
        }

        every { petRepository.save(savedPet) } returns savedPet

        Assertions.assertThat(editPet.update(savedPet)).isEqualTo(savedPet)
        verify { petRepository.save(savedPet) }
    }
}