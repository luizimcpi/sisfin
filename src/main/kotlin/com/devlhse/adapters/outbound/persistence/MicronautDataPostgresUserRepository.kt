package com.devlhse.adapters.outbound.persistence

import com.devlhse.adapters.outbound.persistence.entities.CustomUser
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository
import java.util.Optional
import java.util.UUID

@Repository
interface MicronautDataPostgresUserRepository: JpaRepository<CustomUser, UUID> {
    fun findByEmail(email: String): Optional<CustomUser>
}