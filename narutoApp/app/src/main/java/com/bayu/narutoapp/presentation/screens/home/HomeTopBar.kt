package com.bayu.narutoapp.presentation.screens.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.bayu.narutoapp.R
import com.bayu.narutoapp.ui.theme.topAppBarBackgroundColor
import com.bayu.narutoapp.ui.theme.topAppBarContentColor

@Composable
fun HomeTopBar(
    modifier: Modifier = Modifier,
    onSearchClicked: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(text = "Explore", color = MaterialTheme.colors.topAppBarContentColor )
        },
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor,
        actions = {
            IconButton(onClick = onSearchClicked) {
                Icon(imageVector = Icons.Default.Search, contentDescription = stringResource(R.string.search_icon))
            }
        }
    )
}

@Preview(uiMode = UI_MODE_NIGHT_YES, name = "Dark")
@Preview(name = "Light")
@Composable
fun HomeTopbarPreview() {
    HomeTopBar {

    }
}