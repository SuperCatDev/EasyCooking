package com.sc.easycooking.recipes.impl.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sc.easycooking.recipes.impl.presentation.RecipesListViewModel

@Composable
fun RecipesListRoute(
    modifier: Modifier = Modifier,
    viewModel: RecipesListViewModel = hiltViewModel(),
) {
    RecipesListScreen(modifier)
}

@Composable
fun RecipesListScreen(modifier: Modifier = Modifier) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.Center) {
        Text(
            modifier = Modifier
                .padding(16.dp),
            text = "Hello world",
        )
    }
}
