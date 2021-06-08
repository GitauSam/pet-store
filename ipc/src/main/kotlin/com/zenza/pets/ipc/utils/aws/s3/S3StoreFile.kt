package com.zenza.pets.ipc.utils.aws.s3

import com.amazonaws.AmazonServiceException
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.S3Object
import com.amazonaws.services.s3.model.S3ObjectInputStream
import com.amazonaws.util.IOUtils
import lombok.AllArgsConstructor
import org.springframework.stereotype.Service
import java.io.IOException
import java.io.InputStream
import java.util.*
import java.util.logging.Level
import java.util.logging.Logger

@Service
class S3StoreFile(val amazonS3: AmazonS3) {

    fun upload(path: String?,
               fileName: String?,
               optionalMetaData: Optional<MutableMap<String?, String?>>,
               inputStream: InputStream?) {
        val objectMetadata = ObjectMetadata()
        optionalMetaData.ifPresent { map ->
            if (!map.isEmpty()) {
                map.forEach { (key: String?, value: String?) -> objectMetadata.addUserMetadata(key, value) }
            }
        }

        amazonS3.putObject(path, fileName, inputStream, objectMetadata)
    }

    fun download(path: String?, key: String?): ByteArray? {
        return try {
            val `object`: S3Object = amazonS3.getObject(path, key)
            val objectContent: S3ObjectInputStream = `object`.getObjectContent()
            IOUtils.toByteArray(objectContent)
        } catch (e: AmazonServiceException) {
            throw IllegalStateException("Failed to download the file", e)
        } catch (e: IOException) {
            throw IllegalStateException("Failed to download the file", e)
        }
    }
}