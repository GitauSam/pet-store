package com.zenza.pets.ipc.utils.exceptions

import org.springframework.stereotype.Component
import java.lang.reflect.Constructor

class ExceptionHandler {

    companion object {
        fun <T> throwInvalidInputException (constructor: Constructor<T>, input: String) {
            throw constructor.newInstance("Expected input $input is null") as Throwable
        }

        fun <T> throwInvalidParameterException (constructor: Constructor<T>, param: String, input: String) {
            throw constructor.newInstance("Expected param $param of input $input is null") as Throwable
        }
    }

}