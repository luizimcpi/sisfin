package com.devlhse.controller

import com.devlhse.model.Todo
import com.devlhse.repository.TodoRepository
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post

@Controller("/todos")
class TodoController(private val todoRepository: TodoRepository) {

    @Get
    fun getTodos(): List<Todo> {
        return todoRepository.findAll()
    }

    @Post
    fun addTodo(@Body todo: Todo): HttpResponse<Todo> {
        return HttpResponse.created(todoRepository.save(todo))
    }
}