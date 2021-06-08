package com.zenza.pets.ipc

import com.zenza.pets.ipc.utils.aws.s3.S3StoreFile
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class IpcApplicationTests {

	@Autowired
	lateinit var s3StoreFile: S3StoreFile

	@Test
	fun contextLoads() {
		s3StoreFile.download("phipetsbucket/11487122-591a-4c7e-8fb8-fc9204dc4fa6", "my-exchequer.png")
	}

}
