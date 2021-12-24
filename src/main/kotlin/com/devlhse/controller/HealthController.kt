package com.devlhse.controller

import com.devlhse.repository.TodoRepository
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule

@Controller("/health")
@Secured(SecurityRule.IS_ANONYMOUS)
class HealthController(private val todoRepository: TodoRepository) {

    @Get
    fun getHealthStatus(): String {
        return "UP"
    }
}