package com.example.plugins

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond
import javax.naming.AuthenticationException

fun Application.configureStatusPages() {
    install(StatusPages) {
        status(status = arrayOf(HttpStatusCode.NotFound)) { code ->
            call.respond(
                message = "Page not found.",
                status = code,
            )
        }

//        exception(AuthenticationException::class) { call, _ ->
//            call.respond(
//                message = "We caught an exception!",
//                status = HttpStatusCode.InternalServerError,
//            )
//        }
    }
}