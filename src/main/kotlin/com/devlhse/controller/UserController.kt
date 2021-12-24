package com.devlhse.controller

import com.devlhse.model.CustomUser
import com.devlhse.repository.UserRepository
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import org.slf4j.LoggerFactory

@Controller("/users")
class UserController(private val userRepository: UserRepository) {

    private val log = LoggerFactory.getLogger(this::class.java)

    @Post
    fun createUser(@Body user: CustomUser): HttpResponse<CustomUser> {
        log.info("Criando usuario com parametros: $user")
        return HttpResponse.created(userRepository.save(user))
    }
}