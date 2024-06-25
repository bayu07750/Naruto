package com.bayu.narutoapp.presentation.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bayu.narutoapp.R
import com.bayu.narutoapp.domain.model.Hero
import com.bayu.narutoapp.presentation.components.RatingWidget
import com.bayu.narutoapp.presentation.components.Shimmereffect
import com.bayu.narutoapp.ui.theme.LARGE_PADDING
import com.bayu.narutoapp.ui.theme.MEDIUM_PADDING
import com.bayu.narutoapp.ui.theme.SMALL_PADDING
import com.bayu.narutoapp.ui.theme.topAppBarContentColor
import com.bayu.narutoapp.util.Constants.BASE_URL

@Composable
fun ListContent(
    heroes: LazyPagingItems<Hero>,
    onHeroItemClicked: (Hero) -> Unit,
    modifier: Modifier = Modifier,
) {
    val result = handlePagingResult(heroes = heroes)
    if (result) {
        LazyColumn(
            contentPadding = PaddingValues(SMALL_PADDING),
            verticalArrangement = Arrangement.spacedBy(SMALL_PADDING),
            modifier = modifier,
        ) {
            items(
                count = heroes.itemCount,
                key = heroes.itemKey { it.id },
            ) { index ->
                val hero = heroes[index]
                hero?.let {
                    HeroItem(hero = hero, onItemClicked = { onHeroItemClicked.invoke(it) })
                }
            }
        }
    }
}

@Composable
fun handlePagingResult(
    heroes: LazyPagingItems<Hero>,
): Boolean {
    heroes.apply {
        val error = when {
            loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
            loadState.prepend is LoadState.Error -> loadState.refresh as LoadState.Error
            loadState.append is LoadState.Error -> loadState.refresh as LoadState.Error
            else -> null
        }

        return when {
            loadState.refresh is LoadState.Loading -> {
                Shimmereffect()
                false
            }

            error != null -> {
                EmptyScreen(
                    heroes = heroes,
                    error = error,
                )
                false
            }

            heroes.itemCount < 1 -> {
                EmptyScreen()
                false
            }

            else -> true
        }
    }
}

@Composable
fun HeroItem(
    hero: Hero,
    onItemClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .height(400.dp)
            .clickable(onClick = onItemClicked),
        contentAlignment = Alignment.BottomStart,
    ) {
        Surface(
            shape = RoundedCornerShape(LARGE_PADDING)
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize(),
                model = ImageRequest.Builder(LocalContext.current)
                    .data("$BASE_URL${hero.image}")
                    .placeholder(R.drawable.ic_placeholder)
                    .error(R.drawable.ic_placeholder)
                    .crossfade(true)
                    .build(),
                contentDescription = hero.name,
                contentScale = ContentScale.Crop,
            )
        }
        Surface(
            modifier = Modifier
                .fillMaxHeight(0.4f)
                .fillMaxWidth(),
            color = Color.Black.copy(alpha = ContentAlpha.medium),
            shape = RoundedCornerShape(
                bottomStart = LARGE_PADDING,
                bottomEnd = LARGE_PADDING,
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = MEDIUM_PADDING),
            ) {
                Text(
                    text = hero.name,
                    color = MaterialTheme.colors.topAppBarContentColor,
                    fontSize = MaterialTheme.typography.h5.fontSize,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = hero.about,
                    color = Color.White.copy(alpha = ContentAlpha.medium),
                    fontSize = MaterialTheme.typography.subtitle1.fontSize,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                )
                Row(
                    modifier = Modifier
                        .padding(top = SMALL_PADDING),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    RatingWidget(rating = hero.rating, modifier = Modifier.padding(end = SMALL_PADDING))
                    Text(
                        text = "(${hero.rating})",
                        textAlign = TextAlign.Center,
                        color = Color.White.copy(alpha = ContentAlpha.medium)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHeroItem() {
    HeroItem(
        hero = Hero(
            id = 1,
            name = "Naruto",
            image = "",
            about = "Calon Hokage terkuat dan karakter favorite. Pahlawan dunia shinobi ke 2 dengan mengalahkan si rival Sasuke. Menikah dengan Hinata dan memiliki 2 anak yaitu Boruto dan Himawari.",
            rating = 5.0,
            power = 99,
            month = "08",
            day = "15",
            family = listOf(),
            abilities = listOf(),
            natureTypes = listOf()
        ),
        onItemClicked = {

        },
        modifier = Modifier,
    )
}