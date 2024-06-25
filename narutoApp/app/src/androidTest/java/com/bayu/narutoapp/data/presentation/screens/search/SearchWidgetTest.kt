package com.bayu.narutoapp.data.presentation.screens.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.bayu.narutoapp.presentation.screens.search.SearchWidget
import org.junit.Rule
import org.junit.Test

class SearchWidgetTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun openSearchWidget_addInputText_assertInputText() {
        var text by mutableStateOf("")
        composeTestRule.setContent {
            SearchWidget(
                text = text,
                onTextChange = { text = it },
                onSearchClicked = {},
                onCloseClicked = { }
            )
        }

        composeTestRule.onNodeWithContentDescription(label = "TextField")
            .performTextInput("Naruto")
        composeTestRule.onNodeWithContentDescription(label = "TextField")
            .assertTextEquals("Naruto")
    }

    @Test
    fun openSearchWidget_addInputText_pressCloseButton_assertEmptyInputText() {
        var text by mutableStateOf("")
        composeTestRule.setContent {
            SearchWidget(
                text = text,
                onTextChange = { text = it },
                onSearchClicked = {},
                onCloseClicked = { }
            )
        }

        composeTestRule.onNodeWithContentDescription(label = "TextField")
            .performTextInput("Naruto")
        composeTestRule.onNodeWithContentDescription(label = "CloseButton")
            .performClick()
        composeTestRule.onNodeWithContentDescription(label = "TextField")
            .assertTextContains("")
    }

    @Test
    fun openSearchWidget_addInputText_pressCloseButtonTwice_assertClosedState() {
        var text by mutableStateOf("")
        var searchWidgetShown by mutableStateOf(true)
        composeTestRule.setContent {
            if (searchWidgetShown) {
                SearchWidget(
                    text = text,
                    onTextChange = { text = it },
                    onSearchClicked = {},
                    onCloseClicked = {
                        searchWidgetShown = false
                    }
                )
            }
        }

        composeTestRule.onNodeWithContentDescription(label = "TextField")
            .performTextInput("Naruto")
        composeTestRule.onNodeWithContentDescription(label = "CloseButton")
            .performClick()
        composeTestRule.onNodeWithContentDescription(label = "CloseButton")
            .performClick()
        composeTestRule.onNodeWithContentDescription(label = "SearchWidget")
            .assertDoesNotExist()
    }

    @Test
    fun openSearchWidget_pressInputOnceWhenInputIsEmpty_assertClosedState() {
        var text by mutableStateOf("")
        var searchWidgetShown by mutableStateOf(true)
        composeTestRule.setContent {
            if (searchWidgetShown) {
                SearchWidget(
                    text = text,
                    onTextChange = { text = it },
                    onSearchClicked = {},
                    onCloseClicked = {
                        searchWidgetShown = false
                    }
                )
            }
        }

        composeTestRule.onNodeWithContentDescription(label = "CloseButton")
            .performClick()
        composeTestRule.onNodeWithContentDescription(label = "SearchWidget")
            .assertDoesNotExist()
    }
}