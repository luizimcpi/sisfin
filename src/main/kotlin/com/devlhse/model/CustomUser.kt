package com.devlhse.model

import io.micronaut.core.annotation.Introspected
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.GenericGenerator
import java.time.LocalDateTime
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity(name = "users")
@Introspected
data class CustomUser(

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    val id: UUID? = null,

    @Column
    val email: String,

    @Column
    val password: String,

    @Column
    @CreationTimestamp
    val createdAt: LocalDateTime? = null,

)
