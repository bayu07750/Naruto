package com.bayu.narutoapp.navigation

sealed class Screen(
    val route: String,
)

//object SplashScreen : Screen("splash_screen")
data object WelcomeScreen : Screen("welcome_screen")
data object HomeScreen: Screen("home_screen")
data object DetailScreen: Screen("detail_screen/{heroId}") {
    fun passHeroId(heroId: Int): String = "detail_screen/$heroId"
}
data object SearchScreen: Screen("search_screen")