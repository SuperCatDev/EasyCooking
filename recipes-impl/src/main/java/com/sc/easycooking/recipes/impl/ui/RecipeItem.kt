@file:OptIn(ExperimentalFoundationApi::class)

package com.sc.easycooking.recipes.impl.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.sc.easycooking.recipes.impl.R
import com.sc.easycooking.recipes.impl.presentation.RecipesListViewModel
import com.sc.easycooking.recipes.impl.presentation.models.RecipeUiModelShort
import com.sc.easycooking.recipes.impl.ui.hashtag.HashTagRow

@Composable
internal fun RecipeItem(
    item: RecipeUiModelShort?,
    selectedItems: Set<RecipeUiModelShort>,
    viewModel: RecipesListViewModel,
    navigateToDetails: (id: Int?, edit: Boolean) -> Unit,
) {
    val selected = selectedItems.contains(item)
    val hapticFeedback = LocalHapticFeedback.current
    val roundSize = 12.dp

    Column(
        modifier = Modifier
            .shadow(
                elevation = if (selected) 5.dp else 0.dp,
                shape = RoundedCornerShape(roundSize),
            )
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
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
                    item?.let { selectedItem ->
                        viewModel.clickedAt(selectedItem)
                        navigateToDetails(selectedItem.id, false)
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
        horizontalAlignment = Alignment.Start,
    ) {

        if (item != null) {
            Text(
                text = stringResource(id = item.categoryNameId),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }

        Box(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.background,
                    shape = RoundedCornerShape(roundSize),
                )
                .clip(RoundedCornerShape(roundSize))
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

                if (item?.tags?.isNotEmpty() == true) {
                    Spacer(modifier = Modifier.padding(top = 8.dp))
                    HashTagRow(tags = item.tags.map { it.nameId })
                }
            }
        }
    }
}