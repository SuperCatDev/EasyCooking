@file:OptIn(ExperimentalAnimationApi::class)

package com.example.view_ext.anims

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.ui.graphics.TransformOrigin

val durationDefault = 300
val durationMiddle = 200
val durationFast = 150

fun scaleInDuration(
    duration: Int,
    initialScale: Float = 0f,
    transformOrigin: TransformOrigin = TransformOrigin.Center,
): EnterTransition {
    return scaleIn(
        animationSpec = tween(durationMillis = duration),
        initialScale = initialScale,
        transformOrigin = transformOrigin,
    )
}

fun fadeInDuration(
    duration: Int,
    initialAlpha: Float = 0f,
): EnterTransition {
    return fadeIn(
        animationSpec = tween(durationMillis = duration),
        initialAlpha = initialAlpha,
    )
}

fun scaleOutDuration(
    duration: Int,
    targetScale: Float = 0f,
    transformOrigin: TransformOrigin = TransformOrigin.Center,
): ExitTransition {
    return scaleOut(
        animationSpec = tween(durationMillis = duration),
        targetScale = targetScale,
        transformOrigin = transformOrigin,
    )
}

fun fadeOutDuration(
    duration: Int,
    targetAlpha: Float = 0f,
): ExitTransition {
    return fadeOut(
        animationSpec = tween(durationMillis = duration),
        targetAlpha = targetAlpha,
    )
}