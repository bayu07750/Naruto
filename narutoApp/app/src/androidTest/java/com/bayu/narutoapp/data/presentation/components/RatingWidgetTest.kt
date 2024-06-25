package com.bayu.narutoapp.data.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import com.bayu.narutoapp.presentation.components.RatingWidget
import com.bayu.narutoapp.ui.theme.SMALL_PADDING
import org.junit.Rule
import org.junit.Test

class RatingWidgetTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun passZeroPointZeroValue_Assert_FiveEmptyStars() {
        composeTestRule.setContent {
            RatingWidget(rating = 0.0, modifier = Modifier.padding(SMALL_PADDING))
        }

        composeTestRule.onAllNodesWithContentDescription("FilledStart")
            .assertCountEquals(0)
        composeTestRule.onAllNodesWithContentDescription("HalfFilledStart")
            .assertCountEquals(0)
        composeTestRule.onAllNodesWithContentDescription("EmptyStar")
            .assertCountEquals(5)
    }

    @Test
    fun passZeroPointFiveValue_Assert_FourEmptyStarsAndOneHalFilledStar() {
        composeTestRule.setContent {
            RatingWidget(rating = 0.5, modifier = Modifier.padding(SMALL_PADDING))
        }

        composeTestRule.onAllNodesWithContentDescription("FilledStart")
            .assertCountEquals(0)
        composeTestRule.onAllNodesWithContentDescription("HalfFilledStart")
            .assertCountEquals(1)
        composeTestRule.onAllNodesWithContentDescription("EmptyStar")
            .assertCountEquals(4)
    }

    @Test
    fun passZeroPointSizValue_Assert_FourEmptyStarsAndOneFilledStar() {
        composeTestRule.setContent {
            RatingWidget(rating = 0.6, modifier = Modifier.padding(SMALL_PADDING))
        }

        composeTestRule.onAllNodesWithContentDescription("FilledStart")
            .assertCountEquals(1)
        composeTestRule.onAllNodesWithContentDescription("HalfFilledStart")
            .assertCountEquals(0)
        composeTestRule.onAllNodesWithContentDescription("EmptyStar")
            .assertCountEquals(4)
    }

    @Test
    fun passFourPointZeroValue_Assert_FourFilledStarAndOneEmptyStar() {
        composeTestRule.setContent {
            RatingWidget(rating = 4.0, modifier = Modifier.padding(SMALL_PADDING))
        }

        composeTestRule.onAllNodesWithContentDescription("FilledStart")
            .assertCountEquals(4)
        composeTestRule.onAllNodesWithContentDescription("HalfFilledStart")
            .assertCountEquals(0)
        composeTestRule.onAllNodesWithContentDescription("EmptyStar")
            .assertCountEquals(1)
    }

    @Test
    fun passFourPointThreeValue_Assert_FourFilledStarAndOneHalfFilledStart() {
        composeTestRule.setContent {
            RatingWidget(rating = 4.3, modifier = Modifier.padding(SMALL_PADDING))
        }

        composeTestRule.onAllNodesWithContentDescription("FilledStart")
            .assertCountEquals(4)
        composeTestRule.onAllNodesWithContentDescription("HalfFilledStart")
            .assertCountEquals(1)
        composeTestRule.onAllNodesWithContentDescription("EmptyStar")
            .assertCountEquals(0)
    }

    @Test
    fun passNegativeValue_Assert_FiveEmptyStars() {
        composeTestRule.setContent {
            RatingWidget(rating = -4.3, modifier = Modifier.padding(SMALL_PADDING))
        }

        composeTestRule.onAllNodesWithContentDescription("FilledStart")
            .assertCountEquals(0)
        composeTestRule.onAllNodesWithContentDescription("HalfFilledStart")
            .assertCountEquals(0)
        composeTestRule.onAllNodesWithContentDescription("EmptyStar")
            .assertCountEquals(5)
    }

    @Test
    fun passInvalidValue_Assert_FiveEmptyStars() {
        composeTestRule.setContent {
            RatingWidget(rating = 5.5, modifier = Modifier.padding(SMALL_PADDING))
        }

        composeTestRule.onAllNodesWithContentDescription("FilledStart")
            .assertCountEquals(0)
        composeTestRule.onAllNodesWithContentDescription("HalfFilledStart")
            .assertCountEquals(0)
        composeTestRule.onAllNodesWithContentDescription("EmptyStar")
            .assertCountEquals(5)
    }
}