package com.sc.easycooking.recipes.impl.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.sc.easycooking.recipes.impl.presentation.RecipesListViewModel
import com.sc.easycooking.recipes.impl.presentation.models.RecipeUiModelShort
import kotlinx.coroutines.flow.Flow

@Composable
internal fun RecipesListRoute(
    modifier: Modifier = Modifier,
    viewModel: RecipesListViewModel = hiltViewModel(),
) {
    RecipesListScreen(modifier, viewModel.observeAllRecipes())
}

@Composable
internal fun RecipesListScreen(
    modifier: Modifier = Modifier,
    flow: Flow<PagingData<RecipeUiModelShort>>
) {
    val lazyPagingItems = flow.collectAsLazyPagingItems()

    LazyColumn(modifier = modifier) {
        items(lazyPagingItems) {
            Text("Item is $it")
        }
    }
}
