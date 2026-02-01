package com.bookpin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class BookpinApplication

fun main(args: Array<String>) {
    runApplication<BookpinApplication>(*args)
}
