package com.devlhse.application.ports.repository

import com.devlhse.application.domain.Todo
import com.devlhse.application.domain.User
import java.util.Optional
import java.util.UUID

interface TodoRepositoryPort {

    fun save(todo: Todo): Todo

    fun update(todo: Todo): Todo

    fun findByUser(user: User): List<Todo>

    fun findByUserAndId(user: User, id: UUID): Optional<Todo>

    fun delete(todo: Todo)

}