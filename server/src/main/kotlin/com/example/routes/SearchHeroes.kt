package com.example.routes

import com.example.repository.HeroRepository
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import org.koin.ktor.ext.inject

fun Route.searchHeroes() {
    val heroRepository: HeroRepository by inject()
    get("/naruto/heroes/search") {
        val name = call.request.queryParameters["name"]
        call.respond(
            message = heroRepository.searchHeroes(name),
            status = HttpStatusCode.OK,
        )
    }
}