package com.zenza.pets.ipc

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.zenza.pets"])
class IpcApplication

fun main(args: Array<String>) {
	runApplication<IpcApplication>(*args)
}
