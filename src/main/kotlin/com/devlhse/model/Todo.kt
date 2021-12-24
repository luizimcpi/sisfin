package com.devlhse.model

import io.micronaut.core.annotation.Introspected
import org.hibernate.annotations.GenericGenerator
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
@Introspected
data class Todo(
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    val id: UUID?,

    @Column
    val description: String,

    @Column
    val done: Boolean
)