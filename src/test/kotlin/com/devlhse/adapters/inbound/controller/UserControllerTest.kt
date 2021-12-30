package com.devlhse.adapters.inbound.controller

import com.devlhse.adapters.dto.UserOutputDto
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
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
    fun `should create user and return status 201`(){
        val requestBody = "{\"email\": \"teste@email.com\", \"password\": \"12345678\"}"
        val request: HttpRequest<Any> = HttpRequest.POST("/users", requestBody)
        val response = httpClient.toBlocking().exchange(request, Argument.listOf(UserOutputDto::class.java))

        assertNotNull(response)
        val userOutputDto = response.body.get().first() as UserOutputDto
        assertNotNull(userOutputDto)
        assertNotNull(userOutputDto.createdAt)
        assertEquals("teste@email.com", userOutputDto.email)
        assertEquals(HttpStatus.CREATED, response.status)
    }

    @Test
    fun `should throw exception when try create users with same email`(){
        val requestBody = "{\"email\": \"teste@email.com\", \"password\": \"12345678\"}"
        assertThrows<HttpClientResponseException> {
            httpClient.toBlocking().retrieve(HttpRequest.POST("/users", requestBody), UserOutputDto::class.java)
            httpClient.toBlocking().retrieve(HttpRequest.POST("/users", requestBody), UserOutputDto::class.java)
        }
    }

    @Test
    fun `should throw exception when try create user with invalid request email`(){
        val requestBody = "{\"email\": \"teste\", \"password\": \"12345678\"}"
        assertThrows<HttpClientResponseException> {
            httpClient.toBlocking().retrieve(HttpRequest.POST("/users", requestBody), UserOutputDto::class.java)
        }
    }
}