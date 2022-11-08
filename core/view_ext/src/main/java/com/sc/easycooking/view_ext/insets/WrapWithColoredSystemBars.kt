package com.sc.easycooking.view_ext.insets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
inline fun WrapWithColoredSystemBars(
    modifier: Modifier,
    navBarColor: Color?,
    content: @Composable (Modifier) -> Unit = {}
) {
    Box {
        content(Modifier.padding(WindowInsets.navigationBars.asPaddingValues()))

        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(
                    WindowInsets.navigationBars
                        .asPaddingValues()
                        .calculateBottomPadding()
                )
                .also {
                    navBarColor?.let { color ->
                        it.background(color)
                    }
                }
                .align(Alignment.BottomStart)
        )
    }
}