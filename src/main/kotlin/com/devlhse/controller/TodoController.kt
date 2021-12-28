package com.devlhse.controller

import com.devlhse.controller.dto.TodoInputDto
import com.devlhse.controller.dto.TodoOutputDto
import com.devlhse.model.Todo
import com.devlhse.service.TodoService
import com.devlhse.service.UserService
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put
import io.micronaut.security.annotation.Secured
import io.micronaut.security.authentication.Authentication
import io.micronaut.security.rules.SecurityRule
import org.slf4j.LoggerFactory
import java.util.UUID

@Controller("/todos")
@Secured(SecurityRule.IS_AUTHENTICATED)
class TodoController(private val todoService: TodoService, private val userService: UserService) {

    private val log = LoggerFactory.getLogger(this::class.java)

    @Get
    fun getTodos(authentication: Authentication): List<TodoOutputDto> {
        val user = userService.findByEmail(authentication.name)
        return todoService.findByUser(user).map {
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
        val user = userService.findByEmail(authentication.name)
        log.info("user in todos ${user.email}")
        val todo = Todo(description = todoInputDto.description, done = todoInputDto.done, user = user)
        val savedTodo = todoService.save(todo)
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
        val user = userService.findByEmail(authentication.name)
        log.info("user in todos ${user.email}")

        val existingTodo = todoService.findByUserAndId(user, id)

        val todo = Todo(
            id = existingTodo.id,
            description = todoInputDto.description,
            done = todoInputDto.done,
            createdAt =  existingTodo.createdAt,
            user = user
        )
        val savedTodo = todoService.update(todo)
        val output = TodoOutputDto(
            id = savedTodo.id!!,
            description = savedTodo.description,
            done = savedTodo.done,
            createdAt = savedTodo.createdAt!!,
            updatedAt = savedTodo.updatedAt!!
        )
        return HttpResponse.ok(output)
    }

    @Delete("/{id}")
    fun deleteTodo(id: UUID, authentication: Authentication): HttpResponse<TodoOutputDto> {
        log.info("deleting todo with id $id")
        val user = userService.findByEmail(authentication.name)

        todoService.deleteByUserAndId(user, id)

        return HttpResponse.noContent()
    }

    @Get("/{id}")
    fun getTodo(id: UUID, authentication: Authentication): HttpResponse<TodoOutputDto> {
        log.info("find todo with id $id")
        val user = userService.findByEmail(authentication.name)

        val todo = todoService.findByUserAndId(user, id)

        val output = TodoOutputDto(
            id = todo.id!!,
            description = todo.description,
            done = todo.done,
            createdAt = todo.createdAt!!,
            updatedAt = todo.updatedAt!!
        )

        return HttpResponse.ok(output)
    }
}