package com.zenza.pets.ipc.activator

import com.ninjasquad.springmockk.MockkBean
import com.zenza.pets.ipc.activator.pet.FetchPet
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
import java.util.*

@ExtendWith(SpringExtension::class)
@SpringBootTest
internal class FetchPetTest {

    @MockkBean
    private lateinit var petRepository: PetRepository
    @Autowired
    private lateinit var fetchPet: FetchPet

    @Test
    fun `should throw InvalidInputException when input is null`() {
        Assertions.assertThatExceptionOfType(InvalidInputException::class.java)
                .isThrownBy { fetchPet.byId(null) }
                .withMessage("%s", "Expected input pet is null")
                .withMessageContaining("Expected input pet is null")
                .withNoCause()
    }

    @Test
    fun `should throw InvalidParameterException when input parameter is null`() {
        val pet = Pet().apply { id = null }
        Assertions.assertThatExceptionOfType(InvalidParameterException::class.java)
                .isThrownBy { fetchPet.byId(pet) }
                .withMessage("%s", "Expected param id of input pet is null")
                .withMessageContaining("Expected param id of input pet is null")
                .withNoCause()
    }

    @Test
    fun `should call findById function of pet repository`() {
        val pet = Pet().apply {
            id = 1
        }

        every { petRepository.findById(pet.id!!) } returns Optional.of(pet)

        Assertions.assertThat(fetchPet.byId(pet)).isEqualTo(Optional.of(pet).get())
        verify { petRepository.findById(pet.id!!) }
    }

}


























