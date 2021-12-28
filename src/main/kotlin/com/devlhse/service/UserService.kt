package com.devlhse.service

import com.devlhse.model.CustomUser
import com.devlhse.repository.UserRepository
import io.micronaut.security.authentication.AuthenticationException
import io.micronaut.security.authentication.AuthenticationFailed
import jakarta.inject.Singleton

@Singleton
class UserService(private val userRepository: UserRepository) {

    fun findByEmail(email: String): CustomUser {
        return userRepository.findByEmail(email).orElseThrow {
            throw AuthenticationException(AuthenticationFailed("Usuario não encontrado com e-mail: $email informado."))
        }
    }
}