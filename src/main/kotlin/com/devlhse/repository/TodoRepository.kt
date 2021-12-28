package com.devlhse.repository

import com.devlhse.model.CustomUser
import com.devlhse.model.Todo
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository
import java.util.Optional
import java.util.UUID

@Repository
interface TodoRepository: JpaRepository<Todo, UUID>{
    fun findByUser(user: CustomUser): List<Todo>

    fun findByUserAndId(user: CustomUser, id: UUID): Optional<Todo>

    fun deleteByUserAndId(user: CustomUser, id: UUID)

}