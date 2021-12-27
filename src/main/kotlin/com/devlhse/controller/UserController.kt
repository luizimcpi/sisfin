package com.devlhse.controller

import com.devlhse.configuration.security.BCryptPasswordEncoderService
import com.devlhse.controller.dto.UserInputDto
import com.devlhse.controller.dto.UserOutputDto
import com.devlhse.model.CustomUser
import com.devlhse.repository.UserRepository
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
class UserController(private val userRepository: UserRepository, private val passwordEncoder: BCryptPasswordEncoderService) {

    private val log = LoggerFactory.getLogger(this::class.java)

    @Post
    fun createUser(@Body userInputDto: UserInputDto): HttpResponse<UserOutputDto> {
        log.info("Criando usuario com email: ${userInputDto.email}")
        val user = userRepository.findByEmail(userInputDto.email)

        if(user.isPresent) {
            log.warn("Usuario ja cadastrado com email: ${userInputDto.email}")
            throw AuthenticationException(AuthenticationFailed("Usuario já existente com e-mail: ${userInputDto.email}."))
        }

        val customUser = CustomUser(email = userInputDto.email, password = passwordEncoder.encode(userInputDto.password))
        val savedUser = userRepository.save(customUser)
        log.info("Usuario email ${savedUser.email} criado com sucesso")
        val output = UserOutputDto(id = savedUser.id!!, email = savedUser.email, createdAt = savedUser.createdAt!!)
        return HttpResponse.created(output)
    }
}