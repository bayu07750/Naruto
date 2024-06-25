package com.bayu.narutoapp.presentation.screens.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.bayu.narutoapp.domain.model.Hero
import com.bayu.narutoapp.navigation.SearchScreen
import com.bayu.narutoapp.presentation.common.ListContent
import com.bayu.narutoapp.ui.theme.statusBarColor
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun HomeScreen(
    onSearchClicked: () -> Unit,
    onHeroItemClicked: (Hero) -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    val allHeroes = homeViewModel.getAllHeroes.collectAsLazyPagingItems()

    val systemUiController = rememberSystemUiController()
    val statusBarColor = MaterialTheme.colors.statusBarColor
    SideEffect {
        systemUiController.setStatusBarColor(
            color = statusBarColor
        )
    }

    Scaffold(
        topBar = {
            HomeTopBar(
                onSearchClicked = onSearchClicked,
            )
        }
    ) {
        ListContent(
            heroes = allHeroes,
            onHeroItemClicked = onHeroItemClicked,
            modifier = Modifier.padding(it)
        )
    }
}