package com.devlhse.application.ports.service

import com.devlhse.application.domain.User
import java.util.Optional

interface UserServicePort {
    fun save(user: User): User
    fun findByEmail(email: String): Optional<User>
}