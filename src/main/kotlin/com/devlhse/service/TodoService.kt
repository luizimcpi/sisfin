package com.devlhse.service

import com.devlhse.model.CustomUser
import com.devlhse.model.Todo
import com.devlhse.repository.TodoRepository
import io.micronaut.security.authentication.AuthenticationException
import io.micronaut.security.authentication.AuthenticationFailed
import jakarta.inject.Singleton
import org.slf4j.LoggerFactory
import java.util.UUID

@Singleton
class TodoService(private val todoRepository: TodoRepository) {

    private val log = LoggerFactory.getLogger(this::class.java)

    fun findByUser(user: CustomUser): List<Todo> {
        return todoRepository.findByUser(user)
    }

    fun save(todo: Todo): Todo {
        return todoRepository.save(todo)
    }

    fun findByUserAndId(user: CustomUser, id: UUID): Todo {
        return todoRepository.findByUserAndId(user, id).orElseThrow {
            throw AuthenticationException(AuthenticationFailed("Todo não encontrada com id: $id informado."))
        }
    }

    fun update(todo: Todo): Todo {
        return todoRepository.update(todo)
    }

    fun deleteByUserAndId(user: CustomUser, id: UUID) {
        try {
            val todo = todoRepository.findByUserAndId(user, id).orElseThrow {
                throw AuthenticationException(AuthenticationFailed("Todo não encontrada com id: $id informado."))
            }
            todoRepository.delete(todo)
        }catch (e: Exception){
            log.warn("Não foi possível excluir o todo com id: $id erro: ${e.message}")
            throw AuthenticationException(AuthenticationFailed("Todo não encontrada com id: $id informado."))
        }
    }

}