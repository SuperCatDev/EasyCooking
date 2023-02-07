@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)

package com.sc.easycooking.recipes.impl.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.pinnedScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sc.easycooking.recipes.impl.presentation.RecipesDetailsViewModel
import com.sc.easycooking.recipes.impl.presentation.models.details.MutableScreenContentState
import com.sc.easycooking.recipes.impl.presentation.models.details.ScreenContentState
import com.sc.easycooking.recipes.impl.ui.menu.ExposedDropdownMenu
import com.sc.easycooking.view_ext.insets.WrapWithColoredSystemBars
import kotlinx.coroutines.Dispatchers

@Composable
internal fun RecipeDetailsRoute(
    modifier: Modifier = Modifier,
    viewModel: RecipesDetailsViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
) {
    RecipeDetailsScreen(
        modifier = modifier,
        viewModel = viewModel,
        onBackClick = onBackClick
    )
}

@Suppress("SameParameterValue")
@Composable
internal fun RecipeDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: RecipesDetailsViewModel,
    onBackClick: () -> Unit,
) {

    val screenState: ScreenContentState =
        viewModel.observeScreenState().collectAsState(context = Dispatchers.Main.immediate).value

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
            content = { paddingValues: PaddingValues ->
                when (screenState.mutableState) {
                    is MutableScreenContentState.Content -> RecipeDetailsContent(
                        viewModel,
                        screenState.mutableState,
                        paddingValues,
                    )
                    is MutableScreenContentState.Error -> {}
                    MutableScreenContentState.Loading -> {}
                    MutableScreenContentState.None -> {}
                }
            }
        )
    }
}

@Composable
private fun RecipeDetailsContent(
    viewModel: RecipesDetailsViewModel,
    content: MutableScreenContentState.Content,
    paddingValues: PaddingValues,
) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        NameInput(viewModel, content)
        Spacer(modifier = Modifier.height(16.dp))
        RecipeInput(viewModel, content)
        Spacer(modifier = Modifier.height(16.dp))
        CookingTimeInput(viewModel, content)
        Spacer(modifier = Modifier.height(16.dp))
        CategorySelector(viewModel, content)
    }
}

@Composable
private fun ColumnScope.NameInput(
    viewModel: RecipesDetailsViewModel,
    content: MutableScreenContentState.Content,
) {
    val nameText = content.currentModel.name

    SelectionContainer(Modifier.fillMaxWidth()) {
        OutlinedTextField(
            modifier = Modifier
                .padding(horizontal = 16.dp),
            value = nameText ?: "",
            label = { Text(text = "Name") },
            onValueChange = {
                viewModel.nameChanged(it)
            }
        )
    }
}

@Composable
private fun ColumnScope.RecipeInput(
    viewModel: RecipesDetailsViewModel,
    content: MutableScreenContentState.Content,
) {
    val recipeText = content.currentModel.recipe

    SelectionContainer(Modifier.fillMaxWidth()) {
        OutlinedTextField(
            modifier = Modifier
                .padding(horizontal = 16.dp),
            value = recipeText ?: "",
            label = { Text(text = "Recipe") },
            onValueChange = {
                viewModel.recipeChanged(it)
            }
        )
    }
}

@Composable
private fun ColumnScope.CookingTimeInput(
    viewModel: RecipesDetailsViewModel,
    content: MutableScreenContentState.Content,
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {

        val cookingTime = content.currentModel.cookingTime?.toString() ?: ""

        Text(modifier = Modifier.align(Alignment.CenterVertically), text = "Cooking time: ")
        Spacer(modifier = Modifier.width(16.dp))
        SelectionContainer {
            OutlinedTextField(
                value = cookingTime,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                onValueChange = {
                    viewModel.timeChanged(it)
                }
            )
        }
    }
}

@Composable
private fun ColumnScope.CategorySelector(
    viewModel: RecipesDetailsViewModel,
    content: MutableScreenContentState.Content,
) {
    val listItems = listOf(
        "FavoritesFavoritesFavoritesFavoritesFavoritesFavoritesFavorites",
        "Options",
        "Settings",
        "Share",
        "Favorites",
        "Options",
        "SettingsFavoritesFavoritesFavoritesFavoritesFavorites",
        "Share",
        "FavoritesFavoritesFavoritesFavoritesFavoritesFavorites",
        "Options",
        "Settings",
        "Share"
    )
    var selectedItem by remember {
        mutableStateOf(listItems[0])
    }

    ExposedDropdownMenu("Category", listItems, selectedItem) { index, _ ->
        selectedItem = listItems[index]
    }
}