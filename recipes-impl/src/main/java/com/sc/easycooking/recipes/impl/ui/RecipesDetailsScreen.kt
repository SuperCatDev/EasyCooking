@file:OptIn(ExperimentalMaterial3Api::class)

package com.sc.easycooking.recipes.impl.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.pinnedScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.sc.easycooking.recipes.impl.presentation.RecipesDetailsViewModel
import com.sc.easycooking.recipes.impl.presentation.models.details.ScreenContentState
import com.sc.easycooking.view_ext.insets.WrapWithColoredSystemBars

@Composable
internal fun RecipeDetailsRoute(
    modifier: Modifier = Modifier,
    viewModel: RecipesDetailsViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
) {
    RecipeDetailsScreen(
        modifier = modifier,
        screenState = viewModel.observeScreenState().collectAsState().value,
        onBackClick = onBackClick
    )
}

@Suppress("SameParameterValue")
@Composable
internal fun RecipeDetailsScreen(
    modifier: Modifier = Modifier,
    screenState: ScreenContentState,
    onBackClick: () -> Unit,
) {

    WrapWithColoredSystemBars(
        modifier = modifier,
        navBarColor = MaterialTheme.colorScheme.background,
    ) { innerModifier ->

        Scaffold(
            modifier = innerModifier
                .fillMaxSize()
                .padding(WindowInsets.statusBars.asPaddingValues()),
            topBar = {

                val textForTitle = remember {
                    derivedStateOf {
                        if (screenState.editMode) {
                            "Edit"
                        } else {
                            "New recipe"
                        }
                    }
                }

                TopAppBar(
                    modifier = Modifier.background(MaterialTheme.colorScheme.background),
                    title = {
                        Text(
                            modifier = Modifier,
                            text = textForTitle.value,
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onBackClick) {
                            Icon(
                                Icons.Default.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    },
                    scrollBehavior = pinnedScrollBehavior()
                )
            },
            content = { _ -> }
        )
    }
}