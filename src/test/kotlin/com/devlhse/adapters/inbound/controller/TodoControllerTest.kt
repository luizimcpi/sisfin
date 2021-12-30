package com.devlhse.adapters.inbound.controller

import com.devlhse.adapters.dto.LoginOutputDto
import com.devlhse.adapters.dto.TodoOutputDto
import com.devlhse.adapters.dto.UserOutputDto
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.HttpStatus.CREATED
import io.micronaut.http.MutableHttpRequest
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows

@MicronautTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TodoControllerTest {

    @Inject
    @field:Client("/")
    lateinit var httpClient: HttpClient

    private lateinit var accessToken: String

    @BeforeAll
    fun setUp(){
        val requestBody = "{\"email\": \"teste@email.com\", \"password\": \"12345678\"}"
        val requestLoginBody = "{\"username\": \"teste@email.com\", \"password\": \"12345678\"}"
        val request: HttpRequest<Any> = HttpRequest.POST("/users", requestBody)
        httpClient.toBlocking().exchange(request, Argument.listOf(UserOutputDto::class.java))

        val requestLogin: HttpRequest<Any> = HttpRequest.POST("/login", requestLoginBody)
        val responseLogin =  httpClient.toBlocking().exchange(requestLogin, Argument.listOf(LoginOutputDto::class.java))

        val loginOutputDto = responseLogin.body.get().first() as LoginOutputDto
        accessToken = loginOutputDto.token
    }

    @Test
    fun `when create todo with valid body should success create and return created status`(){
        val requestBody = "{\"description\": \"teste\", \"done\": false}"
        val request: MutableHttpRequest<String>? = HttpRequest.POST("/todos", requestBody).bearerAuth(accessToken)
        val response = httpClient.toBlocking().exchange(request, Argument.listOf(TodoOutputDto::class.java))

        val todoOutputDto = response.body.get().first() as TodoOutputDto

        assertNotNull(response)
        assertNotNull(todoOutputDto)
        assertNotNull(todoOutputDto.createdAt)
        assertNotNull(todoOutputDto.updatedAt)
        assertEquals("teste", todoOutputDto.description)
        assertFalse(todoOutputDto.done)
        assertEquals(CREATED, response.status)
    }

    @Test
    fun `when create todo with empty description in body should return bad request status`(){
        val requestBody = "{\"description\": \"\", \"done\": false}"
        val request: MutableHttpRequest<String>? =
            HttpRequest.POST("/todos", requestBody).header("Authorization", "Bearer $accessToken")

        assertThrows<HttpClientResponseException> {
            httpClient.toBlocking().exchange(request, Argument.listOf(TodoOutputDto::class.java))
        }
    }

    @Test
    fun `when create todo with invalid chars description in body should return bad request status`(){
        val requestBody = "{\"description\": \"te\", \"done\": false}"
        val request: MutableHttpRequest<String>? =
            HttpRequest.POST("/todos", requestBody).header("Authorization", "Bearer $accessToken")

        assertThrows<HttpClientResponseException> {
            httpClient.toBlocking().exchange(request, Argument.listOf(TodoOutputDto::class.java))
        }
    }

    @Test
    fun `when find todo by id with valid id should return ok status`(){
        val requestBody = "{\"description\": \"teste2\", \"done\": false}"
        val request: MutableHttpRequest<String>? =
            HttpRequest.POST("/todos", requestBody).bearerAuth(accessToken)
        val response = httpClient.toBlocking().exchange(request, Argument.listOf(TodoOutputDto::class.java))

        val createdOutputDto = response.body.get().first() as TodoOutputDto

        val requestGet: HttpRequest<Any> = HttpRequest.GET<Any?>("/todos/${createdOutputDto.id}").bearerAuth(accessToken)
        val responseGet = httpClient.toBlocking().exchange(requestGet, Argument.listOf(TodoOutputDto::class.java))

        val todoOutputDto = responseGet.body.get().first() as TodoOutputDto

        assertNotNull(responseGet)
        assertNotNull(todoOutputDto)
        assertNotNull(todoOutputDto.createdAt)
        assertNotNull(todoOutputDto.updatedAt)
        assertEquals("teste2", todoOutputDto.description)
        assertFalse(todoOutputDto.done)
        assertEquals(HttpStatus.OK, responseGet.status)
    }
}