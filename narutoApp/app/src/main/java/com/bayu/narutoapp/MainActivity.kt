package com.bayu.narutoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bayu.narutoapp.domain.use_cases.UseCases
import com.bayu.narutoapp.navigation.HomeScreen
import com.bayu.narutoapp.navigation.NavGraph
import com.bayu.narutoapp.navigation.WelcomeScreen
import com.bayu.narutoapp.ui.theme.NarutoAppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController

    @Inject
    lateinit var useCases: UseCases

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            NarutoAppTheme {
                val completed by useCases.readOnBoardingUseCase().collectAsStateWithLifecycle(initialValue = false)
                navController = rememberNavController()
                NavGraph(
                    navController = navController,
                    startDestination = if (completed) HomeScreen.route else WelcomeScreen.route
                )
            }
        }
    }
}
