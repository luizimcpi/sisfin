package com.devlhse.application.ports.service

import com.devlhse.application.domain.Todo
import com.devlhse.application.domain.User
import java.util.UUID

interface TodoServicePort {
    fun findByUser(user: User): List<Todo>
    fun save(todo: Todo): Todo
    fun findByUserAndId(user: User, id: UUID): Todo
    fun update(todo: Todo): Todo
    fun deleteByUserAndId(user: User, id: UUID)
}