package com.devlhse.adapters.outbound.persistence

import com.devlhse.adapters.outbound.persistence.entities.CustomUser
import com.devlhse.application.domain.User
import com.devlhse.application.ports.repository.UserRepositoryPort
import jakarta.inject.Singleton
import java.util.Optional

@Singleton
class PostgresUserRepository(private val micronautDataPostgresUserRepository: MicronautDataPostgresUserRepository): UserRepositoryPort {
    override fun save(user: User): User {
        val customUser = CustomUser(id = user.id, email = user.email, password = user.password)
        val savedUser = micronautDataPostgresUserRepository.save(customUser)
        return User(id = savedUser.id, email = savedUser.email, password = savedUser.password, createdAt = savedUser.createdAt)
    }

    override fun findByEmail(email: String): Optional<User> {
        val dbUser = micronautDataPostgresUserRepository.findByEmail(email)
        if(dbUser.isPresent) {
            val presentUser = dbUser.get()
            val mappedUser = User(id = presentUser.id, email = presentUser.email, password = presentUser.password, createdAt = presentUser.createdAt)
            return  Optional.of(mappedUser)
        }
        return Optional.empty()
    }

}