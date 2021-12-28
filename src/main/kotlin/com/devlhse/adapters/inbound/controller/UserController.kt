package com.devlhse.adapters.inbound.controller

import com.devlhse.adapters.configuration.BCryptPasswordEncoderService
import com.devlhse.adapters.dto.UserInputDto
import com.devlhse.adapters.dto.UserOutputDto
import com.devlhse.application.domain.User
import com.devlhse.application.ports.service.UserServicePort
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.security.annotation.Secured
import io.micronaut.security.authentication.AuthenticationException
import io.micronaut.security.authentication.AuthenticationFailed
import io.micronaut.security.rules.SecurityRule
import org.slf4j.LoggerFactory

@Controller("/users")
@Secured(SecurityRule.IS_ANONYMOUS)
class UserController(private val userServicePort: UserServicePort, private val passwordEncoder: BCryptPasswordEncoderService) {

    private val log = LoggerFactory.getLogger(this::class.java)

    @Post
    fun createUser(@Body userInputDto: UserInputDto): HttpResponse<UserOutputDto> {
        log.info("Criando usuario com email: ${userInputDto.email}")
        val user = userServicePort.findByEmail(userInputDto.email)

        if(user.isPresent) {
            log.warn("Usuario ja cadastrado com email: ${userInputDto.email}")
            throw AuthenticationException(AuthenticationFailed("Usuario já existente com e-mail: ${userInputDto.email}."))
        }

        val customUser = User(email = userInputDto.email, password = passwordEncoder.encode(userInputDto.password))
        val savedUser = userServicePort.save(customUser)
        log.info("Usuario email ${savedUser.email} criado com sucesso")
        val output = UserOutputDto(id = savedUser.id!!, email = savedUser.email, createdAt = savedUser.createdAt!!)
        return HttpResponse.created(output)
    }
}