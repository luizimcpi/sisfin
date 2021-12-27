package com.devlhse.controller

import com.devlhse.controller.dto.TodoInputDto
import com.devlhse.controller.dto.TodoOutputDto
import com.devlhse.model.Todo
import com.devlhse.repository.TodoRepository
import com.devlhse.repository.UserRepository
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put
import io.micronaut.security.annotation.Secured
import io.micronaut.security.authentication.Authentication
import io.micronaut.security.authentication.AuthenticationException
import io.micronaut.security.authentication.AuthenticationFailed
import io.micronaut.security.rules.SecurityRule
import org.slf4j.LoggerFactory
import java.util.UUID

@Controller("/todos")
@Secured(SecurityRule.IS_AUTHENTICATED)
class TodoController(private val todoRepository: TodoRepository, private val userRepository: UserRepository) {

    private val log = LoggerFactory.getLogger(this::class.java)

    @Get
    fun getTodos(authentication: Authentication): List<TodoOutputDto> {
        val user = userRepository.findByEmail(authentication.name).orElseThrow {
            throw AuthenticationException(AuthenticationFailed("Usuario não encontrada com e-mail: ${authentication.name} informado."))
        }
        return todoRepository.findByUser(user).map{
            TodoOutputDto(
                id = it.id!!,
                description = it.description,
                done = it.done,
                createdAt = it.createdAt!!,
                updatedAt = it.updatedAt!!
            )
        }
    }

    @Post
    fun addTodo(@Body todoInputDto: TodoInputDto, authentication: Authentication): HttpResponse<TodoOutputDto> {
        val user = userRepository.findByEmail(authentication.name).orElseThrow {
            throw AuthenticationException(AuthenticationFailed("Usuario não encontrada com e-mail: ${authentication.name} informado."))
        }
        log.info("user in todos ${user.email}")
        val todo = Todo(description = todoInputDto.description, done = todoInputDto.done, user = user)
        val savedTodo = todoRepository.save(todo)
        log.info("todo has been saved in todos ${savedTodo.id}")
        val output = TodoOutputDto(
            id = savedTodo.id!!,
            description = savedTodo.description,
            done = savedTodo.done,
            createdAt = savedTodo.createdAt!!,
            updatedAt = savedTodo.updatedAt!!
        )
        return HttpResponse.created(output)
    }

    @Put("/{id}")
    fun updateTodo(id: UUID, @Body todoInputDto: TodoInputDto, authentication: Authentication): HttpResponse<TodoOutputDto> {
        log.info("udpating todo with id $id")
        val user = userRepository.findByEmail(authentication.name).orElseThrow {
            throw AuthenticationException(AuthenticationFailed("Usuario não encontrada com e-mail: ${authentication.name} informado."))
        }
        log.info("user in todos ${user.email}")

        val existingTodo = todoRepository.findByUserAndId(user, id).orElseThrow {
            throw AuthenticationException(AuthenticationFailed("Todo não encontrada com id: $id informado."))
        }

        val todo = Todo(
            id = existingTodo.id,
            description = todoInputDto.description,
            done = todoInputDto.done,
            createdAt =  existingTodo.createdAt,
            user = user
        )
        val savedTodo = todoRepository.update(todo)
        val output = TodoOutputDto(
            id = savedTodo.id!!,
            description = savedTodo.description,
            done = savedTodo.done,
            createdAt = savedTodo.createdAt!!,
            updatedAt = savedTodo.updatedAt!!
        )
        return HttpResponse.ok(output)
    }
}