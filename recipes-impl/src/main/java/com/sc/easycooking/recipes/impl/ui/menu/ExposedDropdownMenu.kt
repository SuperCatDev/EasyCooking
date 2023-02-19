@file:OptIn(ExperimentalMaterial3Api::class)

package com.sc.easycooking.recipes.impl.ui.menu

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sc.easycooking.view_ext.views.FlatDropdownMenu
import kotlinx.coroutines.flow.filter

@Composable
fun ExposedDropdownMenu(
    label: String,
    items: List<String>,
    selected: String = items[0],
    onItemSelected: (Int, String) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    LaunchedEffect(interactionSource) {
        interactionSource.interactions
            .filter { it is PressInteraction.Release }
            .collect {
                expanded = !expanded
            }
    }
    ExposedDropdownMenuStack(
        textField = {
            OutlinedTextField(
                value = selected,
                label = { Text(label) },
                onValueChange = {},
                interactionSource = interactionSource,
                readOnly = true,
                trailingIcon = {
                    val rotation by animateFloatAsState(if (expanded) 180F else 0F)
                    Icon(
                        rememberVectorPainter(Icons.Default.ArrowDropDown),
                        contentDescription = "Dropdown Arrow",
                        Modifier.rotate(rotation),
                    )
                }
            )
        },
        dropdownMenu = { boxWidth, itemHeight ->
            Box(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .wrapContentSize(Alignment.TopEnd),
            ) {

                FlatDropdownMenu(
                    modifier = Modifier
                        .heightIn(0.dp, 300.dp),
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    items.forEachIndexed { index, item ->
                        DropdownMenuItem(
                            modifier = Modifier
                                .let {
                                    when {
                                        items.size <= 1 -> it
                                        index == 0 -> it.padding(0.dp, 0.dp, 0.dp, 2.dp)
                                        index == items.size - 1 -> it.padding(
                                            0.dp,
                                            2.dp,
                                            0.dp,
                                            0.dp
                                        )
                                        else -> it.padding(vertical = 2.dp)
                                    }
                                }
                                .width(boxWidth / 2)
                                .background(
                                    MaterialTheme.colorScheme.surfaceVariant,
                                    RoundedCornerShape(16.dp)
                                )
                                .clip(RoundedCornerShape(16.dp)),
                            onClick = {
                                expanded = false
                                onItemSelected(index, item)
                            },
                            text = {
                                Text(item)
                            }
                        )
                    }
                }


            }
        }
    )
}

@Composable
private fun ExposedDropdownMenuStack(
    textField: @Composable () -> Unit,
    dropdownMenu: @Composable (boxWidth: Dp, itemHeight: Dp) -> Unit
) {
    SubcomposeLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) { constraints ->
        val textFieldPlaceable =
            subcompose(ExposedDropdownMenuSlot.TextField, textField).first().measure(constraints)

        val dropdownPlaceable = subcompose(ExposedDropdownMenuSlot.Dropdown) {
            dropdownMenu(textFieldPlaceable.width.toDp(), textFieldPlaceable.height.toDp())
        }.first().measure(constraints)

        layout(textFieldPlaceable.width, textFieldPlaceable.height) {
            textFieldPlaceable.placeRelative(0, 0)
            dropdownPlaceable.placeRelative(0, textFieldPlaceable.height)
        }
    }
}

private enum class ExposedDropdownMenuSlot { TextField, Dropdown }
