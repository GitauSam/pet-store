package com.zenza.pets.ipc.utils

import java.util.*


class Util {

    companion object {
        fun generateRandomString(): String {
            val leftLimit = 48 // numeral '0'
            val rightLimit = 122 // letter 'z'
            val targetStringLength = 10L
            val random = Random()

            return random.ints(leftLimit, rightLimit + 1)
                    .filter { i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97) }
                    .limit(targetStringLength)
                    .collect({ StringBuilder() }, java.lang.StringBuilder::appendCodePoint, java.lang.StringBuilder::append)
                    .toString()
        }
    }

}