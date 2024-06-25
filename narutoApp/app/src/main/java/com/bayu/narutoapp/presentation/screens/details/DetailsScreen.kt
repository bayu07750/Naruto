package com.bayu.narutoapp.presentation.screens.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bayu.narutoapp.util.Constants.BASE_URL
import com.bayu.narutoapp.util.PaletteGenerator
import kotlinx.coroutines.flow.collectLatest

@Composable
fun DetailsScreen(
    onBack: () -> Unit,
    detailsViewModel: DetailsViewModel = hiltViewModel()
) {
    if (detailsViewModel.colorPalette.isNotEmpty()) {
        detailsViewModel.selectedHero?.let { hero ->
            DetailsContent(
                onBack = onBack,
                hero = hero,
                colors = detailsViewModel.colorPalette
            )
        }
    } else {
        detailsViewModel.generateColorPalette()
    }

    val content = LocalContext.current

    LaunchedEffect(key1 = Unit, block = {
        detailsViewModel.uiEvent.collectLatest { event ->
            when (event) {
                UiEvent.GenerateColorPalette -> {
                    val bitmap = PaletteGenerator.convertImageUrlToBitmap(
                        context = content,
                        imageUrl = "$BASE_URL${detailsViewModel.selectedHero?.image}"
                    )
                    if (bitmap != null) {
                        detailsViewModel.updateColorPallete(colors = PaletteGenerator.extractColorFromBitmap(bitmap))
                    }
                }
            }
        }
    })
}