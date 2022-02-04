package com.devlhse.application.domain

import java.time.LocalDateTime
import java.util.UUID

data class User (
    val id: UUID? = null,
    val email: String,
    val password: String,
    val createdAt: LocalDateTime? = null,
)