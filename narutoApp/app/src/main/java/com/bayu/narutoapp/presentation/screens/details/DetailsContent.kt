package com.bayu.narutoapp.presentation.screens.details

import android.graphics.Color.parseColor
import androidx.compose.animation.core.animateDpAsState
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bayu.narutoapp.R
import com.bayu.narutoapp.domain.model.Hero
import com.bayu.narutoapp.presentation.components.InfoBox
import com.bayu.narutoapp.presentation.components.OrderedList
import com.bayu.narutoapp.ui.theme.LARGE_PADDING
import com.bayu.narutoapp.ui.theme.MEDIUM_PADDING
import com.bayu.narutoapp.ui.theme.SMALL_PADDING
import com.bayu.narutoapp.ui.theme.titleColor
import com.bayu.narutoapp.util.Constants
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DetailsContent(
    onBack: () -> Unit,
    hero: Hero,
    colors: Map<String, String>,
    modifier: Modifier = Modifier,
) {
    var vibrant by remember {
        mutableStateOf("#000000")
    }
    var darkVibrant by remember {
        mutableStateOf("#000000")
    }
    var onDarkVibrant by remember {
        mutableStateOf("#FFFFFF")
    }

    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color(parseColor(darkVibrant))
        )
    }


    LaunchedEffect(key1 = colors, key2 = hero, block = {
        colors["vibrant"]?.let { vibrant = it }
        colors["darkVibrant"]?.let { darkVibrant = it }
        colors["onDarkVibrant"]?.let { onDarkVibrant = it }
    })

    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Expanded)
    )
    val currentSheetFraction = scaffoldState.currentSheetFractionAsState()
    val radius by animateDpAsState(
        targetValue = if (currentSheetFraction == 1f) 40.dp else 0.dp, label = "Shape Animation"
    )

    BottomSheetScaffold(
        modifier = modifier,
        scaffoldState = scaffoldState,
        sheetPeekHeight = 130.dp,
        sheetShape = RoundedCornerShape(radius),
        sheetContent = {
            BottomSheetContent(
                hero = hero,
                infoBoxIconColor = Color(parseColor(vibrant)),
                sheetBackgroundColor = Color(parseColor(darkVibrant)),
                contentColor = Color(parseColor(onDarkVibrant))
            )
        },
        content = {
            BackgroundContent(
                heroImage = hero.image,
                contentDescription = hero.name,
                imageFraction = currentSheetFraction,
                backgroundColor = Color(parseColor(darkVibrant)),
                onCloseClicked = onBack
            )
        },
    )
}

@Composable
fun BackgroundContent(
    heroImage: String,
    contentDescription: String,
    modifier: Modifier = Modifier,
    imageFraction: Float = 1f,
    backgroundColor: Color = MaterialTheme.colors.surface,
    onCloseClicked: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(imageFraction)
                .align(Alignment.TopStart),
            model = ImageRequest.Builder(LocalContext.current)
                .data("${Constants.BASE_URL}${heroImage}")
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_placeholder)
                .crossfade(true)
                .build(),
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop,
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
        ) {
            IconButton(
                onClick = onCloseClicked,
                modifier = Modifier.padding(SMALL_PADDING)
            ) {
                Icon(
                    modifier = Modifier.size(32.dp),
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(id = R.string.close_icon),
                    tint = Color.White,
                )
            }
        }
    }
}

@Composable
fun BottomSheetContent(
    hero: Hero,
    modifier: Modifier = Modifier,
    infoBoxIconColor: Color = MaterialTheme.colors.primary,
    sheetBackgroundColor: Color = MaterialTheme.colors.surface,
    contentColor: Color = MaterialTheme.colors.titleColor,
) {
    Column(
        modifier = modifier
            .background(color = sheetBackgroundColor)
            .padding(LARGE_PADDING),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = LARGE_PADDING),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier = Modifier
                    .size(32.dp)
                    .weight(2f),
                painter = painterResource(id = R.drawable.logo),
                contentDescription = stringResource(id = R.string.app_logo),
                tint = contentColor,
            )
            Text(
                text = hero.name,
                color = contentColor,
                fontSize = MaterialTheme.typography.h5.fontSize,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(8f)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = MEDIUM_PADDING),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            InfoBox(
                icon = painterResource(id = R.drawable.bolt),
                iconColor = infoBoxIconColor,
                bigText = "${hero.power}",
                smallText = stringResource(R.string.power),
                textColor = contentColor
            )
            InfoBox(
                icon = painterResource(id = R.drawable.calendar),
                iconColor = infoBoxIconColor,
                bigText = hero.month,
                smallText = stringResource(R.string.month),
                textColor = contentColor
            )
            InfoBox(
                icon = painterResource(id = R.drawable.cake),
                iconColor = infoBoxIconColor,
                bigText = hero.day,
                smallText = stringResource(R.string.birthday),
                textColor = contentColor
            )
        }
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.about),
            color = contentColor,
            fontSize = MaterialTheme.typography.subtitle1.fontSize,
            fontWeight = FontWeight.Bold
        )
        Text(
            modifier = Modifier
                .alpha(ContentAlpha.medium)
                .padding(bottom = MEDIUM_PADDING),
            text = hero.about,
            color = contentColor,
            fontSize = MaterialTheme.typography.body1.fontSize,
            maxLines = 9,
            overflow = TextOverflow.Ellipsis,
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(SMALL_PADDING)
        ) {
            OrderedList(
                title = stringResource(R.string.family),
                items = hero.family,
                textColor = contentColor,
                modifier = Modifier.weight(1f),
            )
            OrderedList(
                title = stringResource(R.string.abilities),
                items = hero.abilities,
                textColor = contentColor,
                modifier = Modifier.weight(1f)
            )
            OrderedList(
                title = stringResource(R.string.nature_types),
                items = hero.natureTypes,
                textColor = contentColor,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetScaffoldState.currentSheetFractionAsState(): Float {
    var fraction by remember(this) {
        mutableStateOf(bottomSheetState.progress)
    }

    LaunchedEffect(key1 = this, block = {
        combine(
            snapshotFlow { bottomSheetState.progress },
            snapshotFlow { bottomSheetState.currentValue },
        ) { progress, currentValue ->
            progress to currentValue
        }.onEach { (progress, currentValue) ->
            val minimumValue = 0.5f
            val maximumValue = 1f
            val value = when {
                progress == 1.0f && currentValue == BottomSheetValue.Expanded -> minimumValue
                progress == 1.0f && currentValue == BottomSheetValue.Collapsed -> maximumValue
                progress < 1.0f && currentValue == BottomSheetValue.Expanded -> (minimumValue + progress).coerceAtMost(
                    maximumValue
                )

                progress < 1.0f && currentValue == BottomSheetValue.Collapsed -> (maximumValue - progress).coerceAtLeast(
                    minimumValue
                )

                else -> progress
            }
            fraction = value
        }.collect()
    })

    return fraction
}
