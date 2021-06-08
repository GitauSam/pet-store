package com.zenza.pets.config.aws

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AmazonConfig {

    @Bean
    fun s3(): AmazonS3 {
        val awsCredentials = BasicAWSCredentials("AKIASBBYLAOYNYIT2A6E", "UOtvVlyratnbxEWbXnkPbeZscVi7vAm7sJIRwK1e")

        return AmazonS3ClientBuilder
                .standard()
                .withRegion("us-east-2")
                .withCredentials(AWSStaticCredentialsProvider(awsCredentials))
                .build()
    }
}