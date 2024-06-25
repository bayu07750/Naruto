package com.bayu.narutoapp.presentation.common

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.bayu.narutoapp.R
import com.bayu.narutoapp.domain.model.Hero
import com.bayu.narutoapp.ui.theme.DarkGray
import com.bayu.narutoapp.ui.theme.LightGray
import com.bayu.narutoapp.ui.theme.SMALL_PADDING
import java.net.ConnectException
import java.net.SocketTimeoutException

@Composable
fun EmptyScreen(
    modifier: Modifier = Modifier,
    error: LoadState.Error? = null,
    heroes: LazyPagingItems<Hero>? = null,
) {
    var message by remember(error) {
        mutableStateOf("Find your favorite hero!")
    }
    var icon by remember {
        mutableStateOf(R.drawable.ic_search_document)
    }

    if (error != null) {
        message = parseErrorMessage(error)
        icon = R.drawable.ic_network_error
    }

    var starAnimation by remember { mutableStateOf(false) }
    val alphaAnim by animateFloatAsState(
        targetValue = if (starAnimation) ContentAlpha.disabled else 0f,
        animationSpec = tween(durationMillis = 1000),
        label = "alpha"
    )
    LaunchedEffect(key1 = true, block = { starAnimation = true })

    EmptyContent(
        heroes =heroes,
        message = message,
        icon = icon,
        alpha = alphaAnim,
        modifier = modifier,
        error = error,
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EmptyContent(
    message: String,
    @DrawableRes icon: Int,
    modifier: Modifier = Modifier,
    alpha: Float = 1f,
    error: LoadState.Error? = null,
    heroes: LazyPagingItems<Hero>? = null,
) {
    var refreshing by remember { mutableStateOf(false) }
    val refreshState = rememberPullRefreshState(
        refreshing = refreshing,
        onRefresh = {
            refreshing = true
            heroes?.refresh()
            refreshing = false
        }
    )
    Box(
        modifier = Modifier
            .pullRefresh(state = refreshState, enabled = error != null)
            .then(modifier)
    ) {
        PullRefreshIndicator(
            refreshing = refreshing,
            state = refreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Icon(
                modifier = Modifier
                    .size(120.dp)
                    .alpha(alpha),
                painter = painterResource(id = icon),
                contentDescription = message,
                tint = if (isSystemInDarkTheme()) LightGray else DarkGray
            )
            Text(
                modifier = Modifier
                    .padding(SMALL_PADDING)
                    .alpha(alpha),
                text = message,
                color = if (isSystemInDarkTheme()) LightGray else DarkGray,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium,
                fontSize = MaterialTheme.typography.subtitle1.fontSize,
            )
        }
    }
}

fun parseErrorMessage(error: LoadState.Error): String {
    return when (error.error) {
        is SocketTimeoutException -> {
            "Server Unavailable"
        }

        is ConnectException -> {
            "Internet Unavailable"
        }

        else -> {
            "Unknown Error."
        }
    }
}