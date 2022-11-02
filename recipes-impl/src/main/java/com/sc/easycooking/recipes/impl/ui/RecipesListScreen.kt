@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class,
    ExperimentalFoundationApi::class, ExperimentalAnimationApi::class
)

package com.sc.easycooking.recipes.impl.ui

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumedWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.sc.easycooking.recipes.impl.R
import com.sc.easycooking.recipes.impl.presentation.RecipesListViewModel
import com.sc.easycooking.recipes.impl.presentation.models.RecipeUiModelShort
import com.sc.easycooking.recipes.impl.ui.paging.items

@Composable
internal fun RecipesListRoute(
    modifier: Modifier = Modifier,
    viewModel: RecipesListViewModel = hiltViewModel(),
) {
    RecipesListScreen(modifier, viewModel)
}

@Composable
internal fun RecipesListScreen(
    modifier: Modifier = Modifier,
    viewModel: RecipesListViewModel,
) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setNavigationBarColor(
        color = MaterialTheme.colorScheme.surfaceColorAtElevation(BottomAppBarDefaults.ContainerElevation)
    )

    val selectedItems = viewModel.observeSelectedItems().collectAsState()

    Scaffold(
        modifier = modifier,
        bottomBar = {
            BottomAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp)),
                actions = {
                    DrawActions(viewModel, selectedItems.value)
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = { viewModel.addClicked() },
                        elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                    ) {
                        Icon(Icons.Filled.Add, contentDescription = "Add new recipe")
                    }
                }
            )
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { innerPadding ->

        val lazyPagingItems = viewModel.observeAllRecipes().collectAsLazyPagingItems()

        val configuration = LocalConfiguration.current

        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 3 else 2),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = modifier
                .padding(innerPadding)
                .consumedWindowInsets(innerPadding)
                .fillMaxSize()
                .testTag("recipeList:feed"),
        ) {
            recipeListScreen(lazyPagingItems, selectedItems.value, viewModel)
        }
    }
}

private fun LazyStaggeredGridScope.recipeListScreen(
    lazyItems: LazyPagingItems<RecipeUiModelShort>,
    selectedItems: Set<RecipeUiModelShort>,
    viewModel: RecipesListViewModel
) {

    items(lazyItems, key = { it.id }) { item: RecipeUiModelShort? ->
        val selected = selectedItems.contains(item)
        val hapticFeedback = LocalHapticFeedback.current
        val roundSize = 12.dp

        Box(
            modifier = Modifier
                .shadow(
                    elevation = if (selected) 5.dp else 0.dp,
                    shape = RoundedCornerShape(roundSize),
                )
                .background(
                    color = MaterialTheme.colorScheme.background,
                    shape = RoundedCornerShape(roundSize),
                )
                .border(
                    width = if (selected) 2.dp else 1.dp,
                    color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
                    shape = RoundedCornerShape(roundSize),
                )
                .clip(RoundedCornerShape(roundSize))
                .combinedClickable(
                    enabled = item != null,
                    onClick = {
                        item?.let {
                            viewModel.clickedAt(it)
                        }
                    },
                    onLongClick = {
                        hapticFeedback.performHapticFeedback(
                            HapticFeedbackType.LongPress
                        )
                        item?.let {
                            viewModel.longClickedAt(it)
                        }
                    }
                ),
            contentAlignment = Alignment.TopStart,
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                if (item != null) {
                    Text(
                        text = item.name,
                        modifier = Modifier.align(Alignment.Start),
                        style = MaterialTheme.typography.titleMedium
                    )
                    item.ingredients.forEachIndexed { index, ingredient ->
                        Spacer(modifier = Modifier.height(if (index == 0) 16.dp else 2.dp))

                        Row(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = ingredient.name,
                                modifier = Modifier.weight(1f),
                                style = MaterialTheme.typography.bodySmall,
                            )

                            Text(
                                text = "${ingredient.amount} ${ingredient.quantity}",
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.End,
                                style = MaterialTheme.typography.bodySmall,
                            )
                        }

                    }
                } else {
                    Text(
                        text = stringResource(id = R.string.recipe_item_loading),
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

@Composable
private fun DrawActions(
    viewModel: RecipesListViewModel,
    selectedItems: Set<RecipeUiModelShort>
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
            CommonStateBlock(commonBlockVisibilityState)
        }
        commonBlockVisibilityState.currentState && !shouldShowCommon -> {
            commonBlockVisibilityState.targetState = false

            CommonStateBlock(commonBlockVisibilityState)
        }
        selectedBlockVisibilityState.currentState && !shouldShowSelected -> {
            selectedBlockVisibilityState.targetState = false

            SelectedStateBlock(selectedBlockVisibilityState, selectedItemsCount, viewModel)
        }

        shouldShowCommon -> {
            commonBlockVisibilityState.targetState = true

            CommonStateBlock(commonBlockVisibilityState)
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

    val enterTransition = scaleIn() + fadeIn(
        // Fade in with the initial alpha of 0.3f.
        initialAlpha = 0.3f
    )

    val exitTransition = scaleOut() + fadeOut()

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
            Icon(Icons.Default.Clear, contentDescription = "Clear selection")
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
            Icon(Icons.Default.Delete, contentDescription = "Delete")
        }
    }

    AnimatedVisibility(
        visibleState = visibilityState,
        enter = enterTransition,
        exit = exitTransition,
    ) {
        IconButton(onClick = { }) {
            Icon(Icons.Default.Favorite, contentDescription = "Add new recipe")
        }
    }
}

@Composable
private fun CommonStateBlock(
    visibilityState: MutableTransitionState<Boolean>,
) {
    val enterTransition = scaleIn() + fadeIn(
        // Fade in with the initial alpha of 0.3f.
        initialAlpha = 0.3f
    )

    val exitTransition = scaleOut() + fadeOut()

    AnimatedVisibility(
        visibleState = visibilityState,
        enter = enterTransition,
        exit = exitTransition,
    ) {

        IconButton(onClick = { }) {
            Icon(Icons.Default.Search, contentDescription = "Add new recipe")
        }
    }
}
