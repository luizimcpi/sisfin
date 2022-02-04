package com.devlhse.application.domain

import java.time.LocalDateTime
import java.util.UUID

data class Todo(
    val id: UUID? = null,
    val description: String,
    val done: Boolean,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
    val user: User
)
