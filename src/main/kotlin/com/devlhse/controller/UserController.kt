package com.devlhse.controller

import com.devlhse.configuration.security.BCryptPasswordEncoderService
import com.devlhse.model.CustomUser
import com.devlhse.repository.UserRepository
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import org.slf4j.LoggerFactory

@Controller("/users")
@Secured(SecurityRule.IS_ANONYMOUS)
class UserController(private val userRepository: UserRepository, private val passwordEncoder: BCryptPasswordEncoderService) {

    private val log = LoggerFactory.getLogger(this::class.java)

    @Post
    fun createUser(@Body user: CustomUser): HttpResponse<CustomUser> {
        log.info("Criando usuario com parametros: $user")
        return HttpResponse.created(userRepository.save(
                CustomUser(
                    email = user.email,
                    password = passwordEncoder.encode(user.password)
                )
            )
        )
    }
}