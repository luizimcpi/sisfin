package com.devlhse.adapters.inbound.controller

import com.devlhse.adapters.dto.HealthStatusOutputDto
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@MicronautTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HealthControllerTest {

    @Inject
    @field:Client("/")
    lateinit var httpClient: HttpClient

    @Test
    fun `should return up message`(){
        val response = httpClient.toBlocking().retrieve("/health", HealthStatusOutputDto::class.java)
        assertEquals("UP", response.status)
    }

}