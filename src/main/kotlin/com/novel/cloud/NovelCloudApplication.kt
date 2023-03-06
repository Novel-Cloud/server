package com.novel.cloud

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class NovelCloudApplication

fun main(args: Array<String>) {
	runApplication<NovelCloudApplication>(*args)
}
