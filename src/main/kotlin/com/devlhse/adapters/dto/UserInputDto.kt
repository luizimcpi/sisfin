package com.devlhse.adapters.dto

import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.Email
import javax.validation.constraints.Size

@Introspected
data class UserInputDto(

    @field:Email
    val email: String,

    @field:Size(min = 8)
    val password: String
)
