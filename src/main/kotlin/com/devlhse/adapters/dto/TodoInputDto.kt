package com.devlhse.adapters.dto

import javax.validation.constraints.Size

data class TodoInputDto(

    @field:Size(min = 1, max = 100)
    val description: String,
    val done: Boolean
)
