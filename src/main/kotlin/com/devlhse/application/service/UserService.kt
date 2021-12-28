package com.devlhse.application.service

import com.devlhse.application.domain.User
import com.devlhse.application.ports.repository.UserRepositoryPort
import com.devlhse.application.ports.service.UserServicePort
import jakarta.inject.Singleton
import java.util.Optional

@Singleton
class UserService(private val userRepositoryPort: UserRepositoryPort): UserServicePort {
    override fun save(user: User): User {
        return userRepositoryPort.save(user)
    }

    override fun findByEmail(email: String): Optional<User> {
        return userRepositoryPort.findByEmail(email)
    }
}