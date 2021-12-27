package com.devlhse.controller.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime
import java.util.UUID

data class TodoOutputDto(
    val id: UUID,
    val description: String,
    val done: Boolean,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    val createdAt: LocalDateTime,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    val updatedAt: LocalDateTime
)
