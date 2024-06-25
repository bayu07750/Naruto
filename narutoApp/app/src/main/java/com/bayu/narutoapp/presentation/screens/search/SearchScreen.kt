package com.bayu.narutoapp.presentation.screens.search

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
import com.bayu.narutoapp.presentation.common.ListContent
import com.bayu.narutoapp.ui.theme.statusBarColor
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun SearchScreen(
    onBack: () -> Unit,
    onHeroItemClicked: (Hero) -> Unit,
    searchViewModel: SearchViewModel = hiltViewModel(),
) {
    val heroes = searchViewModel.searchHeroes.collectAsLazyPagingItems()

    val systemUiController = rememberSystemUiController()
    val statusBarColor = MaterialTheme.colors.statusBarColor
    SideEffect {
        systemUiController.setStatusBarColor(
            color = statusBarColor
        )
    }

    Scaffold(
        topBar = {
            SearchTopBar(
                text = searchViewModel.searchQuery,
                onTextChange = searchViewModel::updateSearchQuery,
                onSearchClicked = searchViewModel::searchHeroes,
                onCloseClicked = onBack,
                modifier = Modifier
            )
        }
    ) { innerPadding ->
        ListContent(
            heroes = heroes,
            onHeroItemClicked = onHeroItemClicked,
            modifier = Modifier.padding(innerPadding)
        )
    }
}