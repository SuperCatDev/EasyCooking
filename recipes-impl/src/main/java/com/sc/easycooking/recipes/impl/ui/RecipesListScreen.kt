@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class,
    ExperimentalFoundationApi::class, ExperimentalAnimationApi::class,
    ExperimentalAnimationApi::class
)

package com.sc.easycooking.recipes.impl.ui

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumedWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridScope
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.view_ext.anims.durationMiddle
import com.example.view_ext.anims.fadeInDuration
import com.example.view_ext.anims.fadeOutDuration
import com.example.view_ext.anims.scaleInDuration
import com.example.view_ext.anims.scaleOutDuration
import com.sc.easycooking.recipes.impl.R
import com.sc.easycooking.recipes.impl.presentation.RecipesListViewModel
import com.sc.easycooking.recipes.impl.presentation.models.RecipeUiModelShort
import com.sc.easycooking.recipes.impl.ui.paging.items
import com.sc.easycooking.view_ext.insets.WrapWithColoredSystemBars

@Composable
internal fun RecipesListRoute(
    modifier: Modifier = Modifier,
    viewModel: RecipesListViewModel = hiltViewModel(),
    navigateToSettings: () -> Unit,
    navigateToDetails: (id: Int?, edit: Boolean) -> Unit,
) {
    RecipesListScreen(modifier, viewModel, navigateToSettings, navigateToDetails)
}

@Composable
internal fun RecipesListScreen(
    modifier: Modifier = Modifier,
    viewModel: RecipesListViewModel,
    navigateToSettings: () -> Unit,
    navigateToDetails: (id: Int?, edit: Boolean) -> Unit,
) {
    val selectedItems = viewModel.observeSelectedItems().collectAsState()

    WrapWithColoredSystemBars(
        modifier = modifier,
        navBarColor = MaterialTheme.colorScheme.surfaceColorAtElevation(BottomAppBarDefaults.ContainerElevation)
    ) { innerModifier ->
        Scaffold(
            modifier = innerModifier,
            bottomBar = {
                BottomAppBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp, 16.dp, 0.dp, 0.dp)),
                    actions = {
                        DrawActions(viewModel, selectedItems.value, navigateToSettings)
                    },
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = { viewModel.addClicked() },
                            elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                        ) {
                            Icon(
                                Icons.Filled.Add,
                                contentDescription = stringResource(id = R.string.add_new_recipe)
                            )
                        }
                    },
                )
            },
        ) { innerPadding ->

            val lazyPagingItems = viewModel.observeAllRecipes().collectAsLazyPagingItems()

            val configuration = LocalConfiguration.current

            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 3 else 2),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = modifier
                    .padding(
                        PaddingValues(
                            start = 0.dp,
                            end = 0.dp,
                            top = innerPadding.calculateTopPadding(),
                            bottom = innerPadding.calculateBottomPadding()
                        )
                    )
                    .consumedWindowInsets(innerPadding)
                    .fillMaxSize()
                    .testTag("recipeList:feed"),
            ) {
                recipeListScreen(navigateToDetails, lazyPagingItems, selectedItems.value, viewModel)
            }
        }
    }
}

private fun LazyStaggeredGridScope.recipeListScreen(
    navigateToDetails: (id: Int?, edit: Boolean) -> Unit,
    lazyItems: LazyPagingItems<RecipeUiModelShort>,
    selectedItems: Set<RecipeUiModelShort>,
    viewModel: RecipesListViewModel
) {

    items(lazyItems, key = { it.id }) { item: RecipeUiModelShort? ->
        RecipeItem(item, selectedItems, viewModel, navigateToDetails)
    }
}

@Composable
private fun DrawActions(
    viewModel: RecipesListViewModel,
    selectedItems: Set<RecipeUiModelShort>,
    navigateToSettings: () -> Unit,
) {
    val shouldShowSelected = selectedItems.isNotEmpty()
    val shouldShowCommon = !shouldShowSelected

    val selectedBlockVisibilityState = remember {
        MutableTransitionState(shouldShowSelected)
    }

    val commonBlockVisibilityState = remember {
        MutableTransitionState(shouldShowCommon)
    }

    val selectedItemsCount = remember {
        mutableStateOf(0)
    }

    if (shouldShowSelected) {
        selectedItemsCount.value = selectedItems.size
    }

    when {
        !selectedBlockVisibilityState.isIdle -> {
            SelectedStateBlock(selectedBlockVisibilityState, selectedItemsCount, viewModel)
        }
        !commonBlockVisibilityState.isIdle -> {
            CommonStateBlock(commonBlockVisibilityState, navigateToSettings)
        }
        commonBlockVisibilityState.currentState && !shouldShowCommon -> {
            commonBlockVisibilityState.targetState = false

            CommonStateBlock(commonBlockVisibilityState, navigateToSettings)
        }
        selectedBlockVisibilityState.currentState && !shouldShowSelected -> {
            selectedBlockVisibilityState.targetState = false

            SelectedStateBlock(selectedBlockVisibilityState, selectedItemsCount, viewModel)
        }

        shouldShowCommon -> {
            commonBlockVisibilityState.targetState = true

            CommonStateBlock(commonBlockVisibilityState, navigateToSettings)
        }

        //shouldShowSelected
        else -> {
            selectedBlockVisibilityState.targetState = true

            SelectedStateBlock(selectedBlockVisibilityState, selectedItemsCount, viewModel)
        }
    }
}

@Composable
private fun SelectedStateBlock(
    visibilityState: MutableTransitionState<Boolean>,
    selectedItemsState: State<Int>,
    viewModel: RecipesListViewModel,
) {

    val enterTransition = scaleInDuration(
        duration = durationMiddle,
    ) + fadeInDuration(
        duration = durationMiddle,
        // Fade in with the initial alpha of 0.3f.
        initialAlpha = 0.3f
    )

    val exitTransition = scaleOutDuration(
        duration = durationMiddle,
    ) + fadeOutDuration(
        duration = durationMiddle,
    )

    AnimatedVisibility(
        visibleState = visibilityState,
        enter = enterTransition,
        exit = exitTransition,
    ) {
        Button(
            onClick = { viewModel.clearSelection() },
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = LocalContentColor.current,
            ),
            contentPadding = PaddingValues(horizontal = 12.dp),
            modifier = Modifier
                .padding(start = 12.dp)
        ) {
            Icon(
                Icons.Default.Clear,
                contentDescription = stringResource(id = R.string.clear_selection)
            )
            Text(
                fontSize = MaterialTheme.typography.labelLarge.fontSize,
                text = selectedItemsState.value.toString(),
                modifier = Modifier.padding(6.dp),
            )
        }
    }

    AnimatedVisibility(
        visibleState = visibilityState,
        enter = enterTransition,
        exit = exitTransition,
    ) {
        IconButton(onClick = { viewModel.deleteSelected() }) {
            Icon(Icons.Default.Delete, contentDescription = stringResource(id = R.string.delete))
        }
    }

    AnimatedVisibility(
        visibleState = visibilityState,
        enter = enterTransition,
        exit = exitTransition,
    ) {
        IconButton(onClick = { }) {
            Icon(
                Icons.Default.Favorite,
                contentDescription = stringResource(id = R.string.add_favorite)
            )
        }
    }
}

@Composable
private fun CommonStateBlock(
    visibilityState: MutableTransitionState<Boolean>,
    navigateToSettings: () -> Unit,
) {
    val enterTransition = scaleInDuration(
        duration = durationMiddle,
    ) + fadeInDuration(
        duration = durationMiddle,
        // Fade in with the initial alpha of 0.3f.
        initialAlpha = 0.3f
    )

    val exitTransition = scaleOutDuration(
        duration = durationMiddle,
    ) + fadeOutDuration(
        duration = durationMiddle,
    )

    AnimatedVisibility(
        visibleState = visibilityState,
        enter = enterTransition,
        exit = exitTransition,
    ) {
        IconButton(onClick = { navigateToSettings.invoke() }) {
            Icon(
                Icons.Default.Settings,
                contentDescription = stringResource(id = R.string.settings)
            )
        }
    }

    AnimatedVisibility(
        visibleState = visibilityState,
        enter = enterTransition,
        exit = exitTransition,
    ) {

        IconButton(onClick = { }) {
            Icon(Icons.Default.Search, contentDescription = stringResource(id = R.string.search))
        }
    }
}