package com.devlhse.adapters.outbound.persistence

import com.devlhse.adapters.outbound.persistence.entities.CustomUser
import com.devlhse.adapters.outbound.persistence.entities.Todo
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository
import java.util.Optional
import java.util.UUID

@Repository
interface MicronautDataPostgresTodoRepository: JpaRepository<Todo, UUID> {
    fun findByUser(user: CustomUser): List<Todo>

    fun findByUserAndId(user: CustomUser, id: UUID): Optional<Todo>
}