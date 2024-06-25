package com.bayu.narutoapp.data.remote

import com.bayu.narutoapp.domain.model.ApiResponse
import com.bayu.narutoapp.domain.model.Hero

class FakeNarutoApi : NarutoApi {

    companion object {
        val heroes = listOf(
            Hero(
                id = 1,
                name = "Minato",
                image = "/images/minato.jpg",
                about = "Minato Namikaze (波風ミナト, Namikaze Minato) was the Fourth Hokage of Konohagakure. He was renowned as the \"Yellow Flash of the Leaf\" for his incredible speed and reflexes. Minato was a highly skilled and intelligent shinobi, respected by all who knew him. He was the father of Naruto Uzumaki and played a crucial role in sealing the Nine-Tails inside his son, saving the village from destruction.",
                rating = 4.9,
                power = 95,
                month = "Jan",
                day = "25th",
                family = listOf(
                    "Kushina (Wife)",
                    "Naruto (Son)"
                ),
                abilities = listOf(
                    "Flying Thunder God Technique",
                    "Rasengan",
                    "Summoning Jutsu",
                    "Space–Time Ninjutsu"
                ),
                natureTypes = listOf(
                    "Fire",
                    "Wind"
                )
            ),
            Hero(
                id = 2,
                name = "Naruto",
                image = "/images/naruto.jpg",
                about = "Naruto Uzumaki (うずまきナルト, Uzumaki Naruto) is a shinobi of Konohagakure's Uzumaki clan. He became the jinchūriki of the Nine-Tails on the day of his birth — a fate that caused him to be shunned by most of Konoha throughout his childhood. After joining Team Kakashi, Naruto worked hard to gain the village's acknowledgement all the while chasing his dream to become Hokage.",
                rating = 5.0,
                power = 98,
                month = "Oct",
                day = "10th",
                family = listOf(
                    "Minato",
                    "Kushina",
                    "Boruto",
                    "Himawari",
                    "Hinata"
                ),
                abilities = listOf(
                    "Rasengan",
                    "Rasen-Shuriken",
                    "Shadow Clone",
                    "Senin Mode"
                ),
                natureTypes = listOf(
                    "Wind",
                    "Earth",
                    "Lava",
                    "Fire"
                )
            ),
            Hero(
                id = 3,
                name = "Hinata",
                image = "/images/hinata.jpeg",
                about = "Hinata Hyuga (日向ヒナタ, Hyūga Hinata) is a shinobi of Konohagakure's prestigious Hyuga clan. Initially shy and lacking self-confidence, Hinata's strength and determination grew as she trained to become a skilled ninja. She is known for her Byakugan eyes and gentle personality. Hinata's dedication and loyalty have made her a respected member of the village.",
                rating = 4.8,
                power = 88,
                month = "Dec",
                day = "27th",
                family = listOf(
                    "Hiashi",
                    "Hinabi",
                    "Naruto",
                    "Himawari",
                    "Boruto"
                ),
                abilities = listOf(
                    "Byakugan",
                    "Gentle Fist",
                    "Protective Eight Trigrams Sixty-Four Palms"
                ),
                natureTypes = listOf(
                    "Fire",
                    "Water"
                )
            ),
        )
    }
    override suspend fun getAllHeroes(page: Int): ApiResponse {
        return ApiResponse(
            success = false,
        )
    }

    override suspend fun searchHeroes(name: String): ApiResponse {
        return ApiResponse(
            success = true,
            message = "ok",
            prevPage = null,
            nextPage = null,
            heroes = findHeroes(name),
            lastUpdated = null
        )
    }

    private fun findHeroes(name: String?): List<Hero> {
        val result = mutableListOf<Hero>()
        return if (name.isNullOrEmpty()) {
            emptyList()
        } else {
            heroes.forEach { hero ->
                if (hero.name.lowercase().contains(name, true)) {
                    result.add(hero)
                }
            }
            result
        }
    }
}