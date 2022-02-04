package com.devlhse.adapters.inbound.controller

import com.devlhse.adapters.dto.HealthStatusOutputDto
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule

@Secured(SecurityRule.IS_ANONYMOUS)
@Controller("/health")
class HealthController() {

    @Get
    fun getHealthStatus(): HttpResponse<HealthStatusOutputDto> {
        return HttpResponse.ok(HealthStatusOutputDto("UP"))
    }
}