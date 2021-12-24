package com.devlhse.controller

import com.devlhse.model.Todo
import com.devlhse.repository.TodoRepository
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post

@Controller("/health")
class HealthController(private val todoRepository: TodoRepository) {

    @Get
    fun getHealthStatus(): String {
        return "UP"
    }
}