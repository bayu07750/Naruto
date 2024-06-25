package com.bayu.narutoapp.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.bayu.narutoapp.presentation.screens.details.DetailsScreen
import com.bayu.narutoapp.presentation.screens.home.HomeScreen
import com.bayu.narutoapp.presentation.screens.search.SearchScreen
import com.bayu.narutoapp.presentation.screens.welcome.WelcomeScreen
import com.bayu.narutoapp.util.Constants

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
//        composable(route = SplashScreen.route) {
//            SplashScreen(navController = navController)
//        }
        composable(route = WelcomeScreen.route) {
            WelcomeScreen(
                onFinishButtonClicked = {
                    navController.navigateUp()
                    navController.navigate(HomeScreen.route)
                }
            )
        }
        composable(route = HomeScreen.route) {
            HomeScreen(
                onSearchClicked = {
                    navController.navigate(SearchScreen.route)
                },
                onHeroItemClicked = {
                    navController.navigate(DetailScreen.passHeroId(it.id))
                }
            )
        }
        composable(
            route = DetailScreen.route,
            arguments = listOf(
                navArgument(name = Constants.DETAILS_ARGUMENT_KEY) { type = NavType.IntType },
            ),
            enterTransition = {
                slideInVertically { it } + fadeIn()
            },
            exitTransition = {
                slideOutVertically { it } + fadeOut()
            }
        ) {
            DetailsScreen(
                onBack = {
                    navController.navigateUp()
                }
            )
        }
        composable(route = SearchScreen.route) {
            SearchScreen(
                onBack = {
                    navController.navigateUp()
                },
                onHeroItemClicked = {
                    navController.navigate(DetailScreen.passHeroId(it.id))
                }
            )
        }
    }
}