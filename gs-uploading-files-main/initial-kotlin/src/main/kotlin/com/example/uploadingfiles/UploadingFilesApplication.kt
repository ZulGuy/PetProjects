package com.example.uploadingfiles

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class UploadingFilesApplication

fun main(args: Array<String>) {
	runApplication<UploadingFilesApplication>(*args)
}