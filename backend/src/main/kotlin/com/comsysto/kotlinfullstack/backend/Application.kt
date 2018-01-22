package com.comsysto.kotlinfullstack.backend

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.router

@SpringBootApplication
class ReactiveApplication

fun main(args: Array<String>) {
    SpringApplication.run(ReactiveApplication::class.java, *args)
}