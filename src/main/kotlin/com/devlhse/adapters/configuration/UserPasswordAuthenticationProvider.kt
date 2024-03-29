package com.devlhse.adapters.configuration

import com.devlhse.adapters.outbound.persistence.PostgresUserRepository
import io.micronaut.http.HttpRequest
import io.micronaut.security.authentication.AuthenticationException
import io.micronaut.security.authentication.AuthenticationFailed
import io.micronaut.security.authentication.AuthenticationProvider
import io.micronaut.security.authentication.AuthenticationRequest
import io.micronaut.security.authentication.AuthenticationResponse
import jakarta.inject.Singleton
import org.reactivestreams.Publisher
import reactor.core.publisher.Flux
import reactor.core.publisher.FluxSink


@Singleton
class UserPasswordAuthenticationProvider(private val passwordEncoder: BCryptPasswordEncoderService,
                                         private val userRepository: PostgresUserRepository): AuthenticationProvider {

    override fun authenticate(httpRequest: HttpRequest<*>?,
                              authenticationRequest: AuthenticationRequest<*, *>): Publisher<AuthenticationResponse>? {

        var user = userRepository.findByEmail(authenticationRequest?.identity as String).orElseThrow {
            throw AuthenticationException(AuthenticationFailed("Usuario não encontrada com e-mail: ${authenticationRequest.identity} informado."))
        }

        return Flux.create({ emitter: FluxSink<AuthenticationResponse> ->
            if (authenticationRequest.identity == user.email &&
                passwordEncoder.matches(authenticationRequest.secret as String, user.password)) {
                emitter.next(AuthenticationResponse.success(authenticationRequest.identity as String))
                emitter.complete()
            } else {
                emitter.error(AuthenticationResponse.exception())
            }
        }, FluxSink.OverflowStrategy.ERROR)
    }
}