package com.sc.easycooking.recipes.impl.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecipesListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
}