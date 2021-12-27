package com.devlhse.model

import io.micronaut.core.annotation.Introspected
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.GenericGenerator
import java.time.LocalDateTime
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType.EAGER
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity(name = "todos")
@Introspected
data class Todo(

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    val id: UUID? = null,

    @Column
    val description: String,

    @Column
    val done: Boolean,

    @Column
    @CreationTimestamp
    val createdAt: LocalDateTime? = null,

    @ManyToOne(fetch = EAGER)
    val user: CustomUser

)