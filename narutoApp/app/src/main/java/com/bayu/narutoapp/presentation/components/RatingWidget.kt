package com.bayu.narutoapp.presentation.components

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bayu.narutoapp.R
import com.bayu.narutoapp.ui.theme.LightGray
import com.bayu.narutoapp.ui.theme.StarColor

@Composable
fun RatingWidget(
    rating: Double,
    modifier: Modifier = Modifier,
    scaleFactor: Float = 3f,
    spaceBetween: Dp = 6.dp,
) {
    val result = calculateStars(rating = rating)
    val startPathString = stringResource(id = R.string.start_path)
    val startPath = remember {
        PathParser().parsePathString(startPathString).toPath()
    }
    val startPathBounds = remember {
        startPath.getBounds()
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(spaceBetween)
    ) {
        result["filledStars"]?.let {
            repeat(it) {
                FilledStart(
                    startPath = startPath,
                    startPathBounds = startPathBounds,
                    scaleFactor = scaleFactor
                )
            }
        }
        result["halfFilledStars"]?.let {
            repeat(it) {
                HalfFilledStart(
                    startPath = startPath,
                    startPathBounds = startPathBounds,
                    scaleFactor = scaleFactor
                )
            }
        }
        result["emptyStars"]?.let {
            repeat(it) {
                EmptyStar(
                    startPath = startPath,
                    startPathBounds = startPathBounds,
                    scaleFactor = scaleFactor
                )
            }
        }
    }
}

@Composable
fun FilledStart(
    startPath: Path,
    startPathBounds: Rect,
    scaleFactor: Float,
) {
    Canvas(
        modifier = Modifier
            .size(24.dp)
            .semantics {
                contentDescription = "FilledStart"
            },
        onDraw = {
            val canvasSize = size
            scale(scaleFactor) {
                val pathWidth = startPathBounds.width
                val pathHeight = startPathBounds.height
                val left = (canvasSize.width / 2f) - (pathWidth / 1.7f)
                val top = (canvasSize.height / 2f) - (pathHeight / 1.7f)
                translate(left = left, top = top) {
                    drawPath(
                        path = startPath,
                        color = StarColor,
                    )
                }
            }
        }
    )
}

@Composable
fun HalfFilledStart(
    startPath: Path,
    startPathBounds: Rect,
    scaleFactor: Float,
) {
    Canvas(
        modifier = Modifier
            .size(24.dp)
            .semantics {
                contentDescription = "HalfFilledStart"
            },
        onDraw = {
            val canvasSize = size
            scale(scaleFactor) {
                val pathWidth = startPathBounds.width
                val pathHeight = startPathBounds.height
                val left = (canvasSize.width / 2f) - (pathWidth / 1.7f)
                val top = (canvasSize.height / 2f) - (pathHeight / 1.7f)
                translate(left = left, top = top) {
                    drawPath(
                        path = startPath,
                        color = LightGray.copy(alpha = 0.5f),
                    )
                    clipPath(path = startPath) {
                        drawRect(
                            color = StarColor,
                            size = androidx.compose.ui.geometry.Size(
                                width = startPathBounds.maxDimension / 1.7f,
                                height = startPathBounds.maxDimension * scaleFactor,
                            )
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun EmptyStar(
    startPath: Path,
    startPathBounds: Rect,
    scaleFactor: Float,
) {
    Canvas(
        modifier = Modifier
            .size(24.dp)
            .semantics {
                contentDescription = "EmptyStar"
            },
        onDraw = {
            val canvasSize = size
            scale(scaleFactor) {
                val pathWidth = startPathBounds.width
                val pathHeight = startPathBounds.height
                val left = (canvasSize.width / 2f) - (pathWidth / 1.7f)
                val top = (canvasSize.height / 2f) - (pathHeight / 1.7f)
                translate(left = left, top = top) {
                    drawPath(
                        path = startPath,
                        color = LightGray.copy(alpha = 0.5f),
                    )
                }
            }
        }
    )
}

@Composable
fun calculateStars(rating: Double): Map<String, Int> {
    var maxStars by remember { mutableStateOf(5) }
    var filledStars by remember { mutableStateOf(0) }
    var halfFilledStars by remember { mutableStateOf(0) }
    var emptyStars by remember { mutableStateOf(0) }

    LaunchedEffect(key1 = rating, block = {
        val (firstNumber, lastNumber) = rating.toString()
            .split(".")
            .map { it.toInt() }

        if (firstNumber in 0..5 && lastNumber in 0..9) {
            filledStars = firstNumber
            if (lastNumber in 1..5) {
                halfFilledStars++
            }
            if (lastNumber in 6..9) {
                filledStars++
            }
            if (firstNumber == 5 && lastNumber > 0) {
                emptyStars = 5
                filledStars = 0
                halfFilledStars = 0
            }
        } else {
            Log.d("RatingWidget", "Invalid rating number $rating")
        }
    })

    emptyStars = maxStars - (filledStars + halfFilledStars)
    return mapOf(
        "filledStars" to filledStars,
        "halfFilledStars" to halfFilledStars,
        "emptyStars" to emptyStars,
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewFilledStar() {
    val startPathString = stringResource(id = R.string.start_path)
    val startPath = remember {
        PathParser().parsePathString(startPathString).toPath()
    }
    val startPathBounds = remember {
        startPath.getBounds()
    }

    FilledStart(startPath = startPath, startPathBounds = startPathBounds, scaleFactor = 3f)
}

@Preview(showBackground = true)
@Composable
fun PreviewHalfFilledStar() {
    val startPathString = stringResource(id = R.string.start_path)
    val startPath = remember {
        PathParser().parsePathString(startPathString).toPath()
    }
    val startPathBounds = remember {
        startPath.getBounds()
    }

    HalfFilledStart(startPath = startPath, startPathBounds = startPathBounds, scaleFactor = 3f)
}

@Preview(showBackground = true)
@Composable
fun PreviewEmptyStar() {
    val startPathString = stringResource(id = R.string.start_path)
    val startPath = remember {
        PathParser().parsePathString(startPathString).toPath()
    }
    val startPathBounds = remember {
        startPath.getBounds()
    }

    EmptyStar(startPath = startPath, startPathBounds = startPathBounds, scaleFactor = 3f)
}

@Preview(showBackground = true)
@Composable
fun PreviewRatingWidget() {
    Column {
        RatingWidget(rating = 1.0)
        RatingWidget(rating = 1.3)
        RatingWidget(rating = 1.5)
        RatingWidget(rating = 1.8)
        RatingWidget(rating = 3.0)
        RatingWidget(rating = 5.0)
        RatingWidget(rating = 5.3)
        RatingWidget(rating = 6.0)
    }
}
