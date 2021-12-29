package com.devlhse.adapters.inbound.controller

import com.devlhse.adapters.dto.UserOutputDto
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows

@MicronautTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserControllerTest {

    @Inject
    @field:Client("/")
    lateinit var httpClient: HttpClient

    @Test
    fun `should success create user`(){
        val requestBody = "{\"email\": \"teste@email.com\", \"password\": \"12345678\"}"
        val response = httpClient.toBlocking().retrieve(HttpRequest.POST("/users", requestBody), UserOutputDto::class.java)

        assertNotNull(response)
        assertNotNull(response.id)
        assertNotNull(response.createdAt)
        assertEquals("teste@email.com", response.email)
    }

    @Test
    fun `should throw exception when try create users with same email`(){
        val requestBody = "{\"email\": \"teste@email.com\", \"password\": \"12345678\"}"
        assertThrows<HttpClientResponseException> {
            httpClient.toBlocking().retrieve(HttpRequest.POST("/users", requestBody), UserOutputDto::class.java)
            httpClient.toBlocking().retrieve(HttpRequest.POST("/users", requestBody), UserOutputDto::class.java)
        }
    }
}