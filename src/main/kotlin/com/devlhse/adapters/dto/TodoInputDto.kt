package com.devlhse.adapters.dto

import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Introspected
data class TodoInputDto(

    @field:Size(min = 5, max = 100, message = "Descrição deve ter entre 5 e 100 caracteres")
    @field:NotNull
    @field:NotEmpty
    val description: String,
    val done: Boolean
)
