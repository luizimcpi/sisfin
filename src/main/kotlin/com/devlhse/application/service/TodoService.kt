package com.devlhse.application.service

import com.devlhse.application.domain.Todo
import com.devlhse.application.domain.User
import com.devlhse.application.ports.repository.TodoRepositoryPort
import com.devlhse.application.ports.service.TodoServicePort
import io.micronaut.security.authentication.AuthenticationException
import io.micronaut.security.authentication.AuthenticationFailed
import jakarta.inject.Singleton
import org.slf4j.LoggerFactory
import java.util.UUID

@Singleton
class TodoService(private val todoRepositoryPort: TodoRepositoryPort): TodoServicePort {

    private val log = LoggerFactory.getLogger(this::class.java)

    override fun findByUser(user: User): List<Todo> {
        return todoRepositoryPort.findByUser(user)
    }

    override fun save(todo: Todo): Todo {
        return todoRepositoryPort.save(todo)
    }

    override fun findByUserAndId(user: User, id: UUID): Todo {
       return todoRepositoryPort.findByUserAndId(user, id).orElseThrow {
           throw AuthenticationException(AuthenticationFailed("Todo não encontrada com id: $id informado."))
       }
    }

    override fun update(todo: Todo): Todo {
        return todoRepositoryPort.update(todo)
    }

    override fun deleteByUserAndId(user: User, id: UUID) {
        try {
            val todo = todoRepositoryPort.findByUserAndId(user, id).orElseThrow {
                throw AuthenticationException(AuthenticationFailed("Todo não encontrada com id: $id informado."))
            }
            todoRepositoryPort.delete(todo)
        }catch (e: Exception){
            log.warn("Não foi possível excluir o todo com id: $id erro: ${e.message}")
            throw AuthenticationException(AuthenticationFailed("Todo não encontrada com id: $id informado."))
        }
    }
}