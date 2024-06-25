package com.example

import com.example.di.koinModule
import com.example.models.ApiResponse
import com.example.plugins.configureRouting
import com.example.repository.HeroRepository
import com.example.repository.HeroRepositoryImp
import com.example.repository.NEXT_PAGE_KEY
import com.example.repository.PREV_PAGE_KEY
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.json.Json
import org.junit.After
import org.junit.Before
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.koinApplication
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {

    private lateinit var heroRepository: HeroRepository

    @Before
    fun setUp() {
        heroRepository = HeroRepositoryImp()
        koinApplication {
            startKoin {
                modules(koinModule)
            }
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `access root endpoint, assert correct information`() = testApplication {
        application {
            configureRouting()
        }
        client.get("/").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals("Welcome to Naruto API!", bodyAsText())
        }
    }

    @Test
    fun `access all heroes, assert correct information`() = testApplication {
        application {
            configureRouting()
        }
        client.get("/naruto/heroes").apply {
            assertEquals(HttpStatusCode.OK, status)
            val actionAsJson = Json.decodeFromString<ApiResponse>(bodyAsText())
            val expected = ApiResponse(
                success = true,
                message = "ok",
                prevPage = null,
                nextPage = 2,
                heroes = heroRepository.page1,
                lastUpdated = actionAsJson.lastUpdated,
            )
            assertEquals(expected, actionAsJson)
        }
    }


    @Test
    fun `access all heroes, query second page, assert correct information`() = testApplication {
        application {
            configureRouting()
        }
        client.get("/naruto/heroes?page=2").apply {
            assertEquals(HttpStatusCode.OK, status)
            val actionAsJson = Json.decodeFromString<ApiResponse>(bodyAsText())
            val expected = ApiResponse(
                success = true,
                message = "ok",
                prevPage = 1,
                nextPage = 3,
                heroes = heroRepository.page2,
                lastUpdated = actionAsJson.lastUpdated
            )
            assertEquals(expected, actionAsJson)
        }
    }

    @org.junit.Test
    fun `access all heroes endpoint, query all pages, assert correct information`() = testApplication {
        application {
            configureRouting()
        }
        val pages = 1..5
        val heroes = listOf(
            heroRepository.page1,
            heroRepository.page2,
            heroRepository.page3,
            heroRepository.page4,
            heroRepository.page5,
        )
        pages.forEach { page ->
            client.get("/naruto/heroes?page=$page").apply {
                assertEquals(HttpStatusCode.OK, status)
                val actionAsJson = Json.decodeFromString<ApiResponse>(bodyAsText())
                val expected = ApiResponse(
                    success = true,
                    message = "ok",
                    prevPage = calculatePage(page)[PREV_PAGE_KEY],
                    nextPage = calculatePage(page)[NEXT_PAGE_KEY],
                    heroes = heroes[page - 1],
                    lastUpdated = actionAsJson.lastUpdated,
                )
                assertEquals(expected, actionAsJson)
            }
        }
    }

    @Test
    fun `access all heroes, query non existing page number, assert error`() = testApplication {
        application {
            configureRouting()
        }
        client.get("/naruto/heroes?page=6").apply {
            assertEquals(HttpStatusCode.NotFound, status)
//            val expected = ApiResponse(
//                success = true,
//                message = "Heroes not found.",
//            )
//            val actionAsJson = Json.decodeFromString<ApiResponse>(bodyAsText())
//            assertEquals(expected, actionAsJson)
        }
    }

    @Test
    fun `access all heroes, query invalid page number, assert error`() = testApplication {
        application {
            configureRouting()
        }
        client.get("/naruto/heroes?page=kkk").apply {
            assertEquals(HttpStatusCode.BadRequest, status)
//            val expected = ApiResponse(
//                success = true,
//                message = "Heroes not found.",
//            )
//            val actionAsJson = Json.decodeFromString<ApiResponse>(bodyAsText())
//            assertEquals(expected, actionAsJson)
        }
    }

    @Test
    fun `access search heroes, query hero name, assert single hero result`() = testApplication {
        application {
            configureRouting()
        }
        client.get("/naruto/heroes/search?name=naru").apply {
            assertEquals(HttpStatusCode.OK, status)
            val actualSize = Json.decodeFromString<ApiResponse>(bodyAsText())
                .heroes.size
            assertEquals(1, actualSize)
        }
    }

    @Test
    fun `access search heroes, query hero name, assert multiple hero result`() = testApplication {
        application {
            configureRouting()
        }
        client.get("/naruto/heroes/search?name=sa").apply {
            assertEquals(HttpStatusCode.OK, status)
            val actualSize = Json.decodeFromString<ApiResponse>(bodyAsText())
                .heroes.size
            assertEquals(3, actualSize)
        }
    }

    @Test
    fun `access search heroes, query an empty text, assert empty list as a result`() = testApplication {
        application {
            configureRouting()
        }
        client.get("/naruto/heroes/search?name=").apply {
            assertEquals(HttpStatusCode.OK, status)
            val heroes = Json.decodeFromString<ApiResponse>(bodyAsText())
                .heroes
            assertEquals(emptyList(), heroes)
        }
    }

    @Test
    fun `access search heroes, query non existing hero, assert empty list as a result`() = testApplication {
        application {
            configureRouting()
        }
        client.get("/naruto/heroes/search?name=jiraiya").apply {
            assertEquals(HttpStatusCode.OK, status)
            val heroes = Json.decodeFromString<ApiResponse>(bodyAsText())
                .heroes
            assertEquals(emptyList(), heroes)
        }
    }

    @Test
    fun `access not existing endpoint, assert not found`() = testApplication {
        application {
            configureRouting()
        }
        client.get("/boruto").apply {
            assertEquals(HttpStatusCode.NotFound, status)
            assertEquals("Page not found.", bodyAsText())
        }
    }

    private fun calculatePage(page: Int): Map<String, Int?> {
        var prevPage: Int? = page
        var nextPage: Int? = page

        if (page in 1..4) {
            nextPage = nextPage?.plus(1)
        }

        if (page in 2..5) {
            prevPage = prevPage?.minus(1)
        }

        if (page == 1) {
            prevPage = null
        }

        if (page == 5) {
            nextPage = null
        }

        return mapOf(
            PREV_PAGE_KEY to prevPage,
            NEXT_PAGE_KEY to nextPage,
        )
    }
}
