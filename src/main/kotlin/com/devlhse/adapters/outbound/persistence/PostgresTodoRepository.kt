package com.devlhse.adapters.outbound.persistence

import com.devlhse.adapters.outbound.persistence.entities.CustomUser
import com.devlhse.application.domain.Todo
import com.devlhse.application.domain.User
import com.devlhse.application.ports.repository.TodoRepositoryPort
import jakarta.inject.Singleton
import java.util.Optional
import java.util.UUID
import com.devlhse.adapters.outbound.persistence.entities.Todo as TodoEntity

@Singleton
class PostgresTodoRepository(private val micronautDataPostgresTodoRepository: MicronautDataPostgresTodoRepository): TodoRepositoryPort {
    override fun save(todo: Todo): Todo {
        val customUser = CustomUser(id = todo.user.id, email = todo.user.email, password = todo.user.password, createdAt = todo.user.createdAt)
        val todoEntity = TodoEntity(id = todo.id, description = todo.description, done = todo.done, user = customUser)
        val savedEntity = micronautDataPostgresTodoRepository.save(todoEntity)
        val userOutput = User(id = savedEntity.user.id, email = savedEntity.user.email, password = savedEntity.user.password, createdAt = savedEntity.user.createdAt)
        return Todo(
            id = savedEntity.id,
            description = savedEntity.description,
            done = savedEntity.done,
            createdAt = savedEntity.createdAt,
            updatedAt = savedEntity.updatedAt,
            user = userOutput
        )
    }

    override fun update(todo: Todo): Todo {
        val customUser = CustomUser(id = todo.user.id, email = todo.user.email, password = todo.user.password, createdAt = todo.user.createdAt)
        val todoEntity = TodoEntity(id = todo.id, description = todo.description, done = todo.done, user = customUser)
        val savedEntity = micronautDataPostgresTodoRepository.update(todoEntity)
        val userOutput = User(id = savedEntity.user.id, email = savedEntity.user.email, password = savedEntity.user.password, createdAt = savedEntity.user.createdAt)
        return Todo(
            id = savedEntity.id,
            description = savedEntity.description,
            done = savedEntity.done,
            createdAt = savedEntity.createdAt,
            updatedAt = savedEntity.updatedAt,
            user = userOutput
        )
    }

    override fun findByUser(user: User): List<Todo> {
        val customUser = CustomUser(id = user.id, email = user.email, password = user.password, createdAt = user.createdAt)
        return micronautDataPostgresTodoRepository.findByUser(customUser).map {
            val user = User(id = it.user.id, email = it.user.email, password = it.user.password, createdAt = it.user.createdAt)
            Todo(id = it.id, description = it.description, done = it.done, createdAt = it.createdAt, updatedAt = it.createdAt, user = user)
        }
    }

    override fun findByUserAndId(user: User, id: UUID): Optional<Todo> {
        val customUser = CustomUser(id = user.id, email = user.email, password = user.password, createdAt = user.createdAt)
       return micronautDataPostgresTodoRepository.findByUserAndId(customUser, id).map {
           val user = User(id = it.user.id, email = it.user.email, password = it.user.password, createdAt = it.user.createdAt)
           Todo(id = it.id, description = it.description, done = it.done, createdAt = it.createdAt, updatedAt = it.createdAt, user = user)
       }
    }

    override fun delete(todo: Todo) {
        val customUser = CustomUser(id = todo.user.id, email = todo.user.email, password = todo.user.password, createdAt = todo.user.createdAt)
        val todoEntity = TodoEntity(id = todo.id, description = todo.description, done = todo.done, user = customUser)
        return micronautDataPostgresTodoRepository.delete(todoEntity)
    }
}