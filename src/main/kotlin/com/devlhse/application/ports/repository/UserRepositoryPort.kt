package com.devlhse.application.ports.repository

import com.devlhse.application.domain.User
import java.util.Optional

interface UserRepositoryPort {
    fun save(user: User): User
    fun findByEmail(email: String): Optional<User>
}