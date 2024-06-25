package com.bayu.narutoapp.presentation.components

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bayu.narutoapp.ui.theme.EXTRA_SMALL_PADDING
import com.bayu.narutoapp.ui.theme.LARGE_PADDING
import com.bayu.narutoapp.ui.theme.MEDIUM_PADDING
import com.bayu.narutoapp.ui.theme.SMALL_PADDING
import com.bayu.narutoapp.ui.theme.ShimmerDarkGray
import com.bayu.narutoapp.ui.theme.ShimmerLightGray
import com.bayu.narutoapp.ui.theme.ShimmerMediumGray

@Composable
fun Shimmereffect() {
    LazyColumn(
        contentPadding = PaddingValues(SMALL_PADDING),
        verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)
    ) {
        items(5) {
            AnimatedShimmerItem()
        }
    }
}

@Composable
fun AnimatedShimmerItem(
    modifier: Modifier = Modifier,
) {
    val transition = rememberInfiniteTransition(label = "Shimmer Transition")
    val alpha by transition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 500,
                easing = FastOutLinearInEasing,
            ),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "Shimmer Alpha Transition"
    )

    ShimmerItem(alpha = alpha, modifier = modifier)
}

@Composable
fun ShimmerItem(
    modifier: Modifier = Modifier,
    alpha: Float = 1f,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .then(modifier),
        color = if (isSystemInDarkTheme())
            Color.Black else ShimmerLightGray,
        shape = RoundedCornerShape(LARGE_PADDING),
    ) {
        Column(
            modifier = Modifier
                .padding(MEDIUM_PADDING),
            verticalArrangement = Arrangement.Bottom,
        ) {
            Surface(
                modifier = Modifier
                    .alpha(alpha)
                    .fillMaxWidth(.5f)
                    .height(30.dp),
                color = if (isSystemInDarkTheme())
                    ShimmerDarkGray else ShimmerMediumGray,
                shape = RoundedCornerShape(MEDIUM_PADDING),
            ) {}
            Spacer(modifier = Modifier.padding(SMALL_PADDING))
            repeat(3) {
                Surface(
                    modifier = Modifier
                        .alpha(alpha)
                        .fillMaxWidth()
                        .height(15.dp),
                    color = if (isSystemInDarkTheme())
                        ShimmerDarkGray else ShimmerMediumGray,
                    shape = RoundedCornerShape(MEDIUM_PADDING),
                ) {}
                Spacer(modifier = Modifier.padding(EXTRA_SMALL_PADDING))
            }
            Row(modifier = Modifier.fillMaxWidth(),) {
                repeat(5) {
                    Surface(
                        modifier = Modifier
                            .alpha(alpha)
                            .size(20.dp),
                        color = if (isSystemInDarkTheme())
                            ShimmerDarkGray else ShimmerMediumGray,
                        shape = RoundedCornerShape(SMALL_PADDING),
                    ) {}
                    Spacer(modifier = Modifier.padding(SMALL_PADDING))
                }
            }
        }
    }
}