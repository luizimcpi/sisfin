package com.devlhse.repository

import com.devlhse.model.CustomUser
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jpa.repository.JpaRepository
import java.util.UUID

@Repository
abstract class UserRepository: JpaRepository<CustomUser, UUID> {
}