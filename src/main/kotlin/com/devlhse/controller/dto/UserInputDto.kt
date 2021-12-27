package com.devlhse.controller.dto

import javax.validation.constraints.Email
import javax.validation.constraints.Size

data class UserInputDto(

    @field:Email
    val email: String,

    @field:Size(min = 8)
    val password: String
)
