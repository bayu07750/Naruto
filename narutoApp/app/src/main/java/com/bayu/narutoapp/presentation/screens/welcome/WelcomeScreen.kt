package com.bayu.narutoapp.presentation.screens.welcome

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bayu.narutoapp.R
import com.bayu.narutoapp.domain.model.OnBoardingPage
import com.bayu.narutoapp.navigation.HomeScreen
import com.bayu.narutoapp.ui.theme.EXTRA_LARGE_PADDING
import com.bayu.narutoapp.ui.theme.SMALL_PADDING
import com.bayu.narutoapp.ui.theme.activeIndicatorColors
import com.bayu.narutoapp.ui.theme.buttonBackgroundColor
import com.bayu.narutoapp.ui.theme.descriptionColor
import com.bayu.narutoapp.ui.theme.inactiveIndicatorColor
import com.bayu.narutoapp.ui.theme.titleColor
import com.bayu.narutoapp.ui.theme.welcomeScreenBackgroundColor

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WelcomeScreen(
    onFinishButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
    welcomeViewModel: WelcomeViewModel = hiltViewModel(),
) {
    val pages = remember {
        listOf(OnBoardingPage.First, OnBoardingPage.Second, OnBoardingPage.Thrid)
    }

    val pagerState = rememberPagerState {
        pages.size
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.welcomeScreenBackgroundColor),
    ) {
        HorizontalPager(
            modifier = Modifier
                .weight(10f),
            state = pagerState,
            verticalAlignment = Alignment.Top,
        ) {
            PagerScreen(onBoardingPage = pages[it])
        }
        HorizontalPagerIndicator(
            pageCount = pages.size,
            currentPage = pagerState.currentPage,
            modifier = Modifier.weight(1f)
        )
        val shouldShowFinishButton by remember {
            derivedStateOf { pagerState.currentPage == pages.lastIndex }
        }
        FinishButton(
            modifier = modifier.weight(1f),
            visible = shouldShowFinishButton,
            onClick = {
                onFinishButtonClicked()
                welcomeViewModel.saveOnBoardingState(true)
            }
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun FinishButton(
    visible: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .padding(horizontal = EXTRA_LARGE_PADDING),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Center,
    ) {
        AnimatedVisibility(
            modifier = Modifier
                .fillMaxWidth(),
            visible = visible,
            enter = scaleIn() + fadeIn(),
            exit = scaleOut() + fadeOut()
        ) {
            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.buttonBackgroundColor,
                    contentColor = Color.White,
                )
            ) {
                Text(text = stringResource(R.string.finish))
            }
        }
    }
}

@Composable
fun HorizontalPagerIndicator(
    pageCount: Int,
    currentPage: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pageCount) { iteration ->
            val color = if (currentPage == iteration) MaterialTheme.colors.activeIndicatorColors
            else MaterialTheme.colors.inactiveIndicatorColor

            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .clip(CircleShape)
                    .background(color)
                    .size(16.dp)

            )
        }
    }
}

@Composable
fun PagerScreen(
    onBoardingPage: OnBoardingPage,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth(.5f)
                .fillMaxHeight(.7f),
            painter = painterResource(id = onBoardingPage.image),
            contentDescription = onBoardingPage.title
        )
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = onBoardingPage.title,
            color = MaterialTheme.colors.titleColor,
            style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Bold),
            textAlign = TextAlign.Center,
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = EXTRA_LARGE_PADDING)
                .padding(top = SMALL_PADDING),
            text = onBoardingPage.description,
            color = MaterialTheme.colors.descriptionColor,
            style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Medium),
            textAlign = TextAlign.Center,
        )
    }
}