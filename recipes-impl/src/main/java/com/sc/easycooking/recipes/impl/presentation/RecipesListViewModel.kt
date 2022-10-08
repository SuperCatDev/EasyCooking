package com.sc.easycooking.recipes.impl.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.sc.easycooking.recipes.api.navigation.domain.RecipesInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecipesListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val interactor: RecipesInteractor,
) : ViewModel() {
}