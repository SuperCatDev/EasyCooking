package com.sc.easycooking.recipes.impl.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import com.sc.easycooking.recipes.api.domain.RecipesInteractor
import com.sc.easycooking.recipes.api.navigation.RecipeDetailsDestination
import com.sc.easycooking.recipes.impl.domain.FastAccessDataShare
import com.sc.easycooking.recipes.impl.domain.share_keys.SELECTED_ITEM_SHARE
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class RecipesDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val interactor: RecipesInteractor,
    private val fastShare: FastAccessDataShare,
    application: Application,
) : AndroidViewModel(application) {

    init {
        Log.e(
            "VVV",
            "args we jump for id: ${savedStateHandle.get<Int?>(RecipeDetailsDestination.ID_ARG)} edit: ${
                savedStateHandle.get<Boolean>(RecipeDetailsDestination.EDIT_ARG)
            }  ${fastShare.getItem(SELECTED_ITEM_SHARE)}"
        )
    }
}
