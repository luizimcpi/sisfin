package com.devlhse.adapters.outbound.persistence.entities

import io.micronaut.core.annotation.Introspected
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.GenericGenerator
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
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

    @Column
    @UpdateTimestamp
    val updatedAt: LocalDateTime? = null,

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    val user: CustomUser

)