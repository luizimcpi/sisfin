package com.devlhse.controller

import com.devlhse.repository.TodoRepository
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

@Controller("/health")
class HealthController(private val todoRepository: TodoRepository) {

    @Get
    fun getHealthStatus(): String {
        return "UP"
    }
}