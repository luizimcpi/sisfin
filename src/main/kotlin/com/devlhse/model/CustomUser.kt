package com.devlhse.model

import io.micronaut.core.annotation.Introspected
import org.hibernate.annotations.GenericGenerator
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.validation.constraints.Email
import javax.validation.constraints.Size

@Entity(name = "users")
@Introspected
data class CustomUser(

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    val id: UUID? = null,

    @Column
    @field:Email
    val email: String,

    @Column
    @field:Size(min = 8)
    val password: String
)
