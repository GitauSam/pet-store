package com.zenza.pets.ipc.utils.exceptions

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class ExceptionHandlerTest {

    @Test
    fun `test correct exception is thrown`() {
        Assertions.assertThatExceptionOfType(InvalidInputException::class.java)
            .isThrownBy { ExceptionHandler.throwInvalidInputException(InvalidInputException::class.java.constructors[0], "car") }
            .withMessage("%s", "Expected input car is null")
            .withMessageContaining("Expected input car is null")
            .withNoCause()
    }

}