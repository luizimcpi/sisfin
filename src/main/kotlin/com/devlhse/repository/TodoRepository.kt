package com.devlhse.repository

import com.devlhse.model.Todo
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository
import java.util.UUID

@Repository
interface TodoRepository: JpaRepository<Todo, UUID>{
}