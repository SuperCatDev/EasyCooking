@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)

package com.sc.easycooking.settings.impl.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.consumedWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.outlinedButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.pinnedScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sc.easycooking.settings.impl.R
import com.sc.easycooking.view_ext.insets.WrapWithColoredSystemBars
import com.sc.easycooking.view_ext.views.SwitchLight

@Composable
internal fun SettingsScreenRoute(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
) {
    SettingsScreen(modifier, onBackClick)
}

@Suppress("SameParameterValue")
@Composable
internal fun SettingsScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
) {
    WrapWithColoredSystemBars(
        modifier = modifier,
        navBarColor = MaterialTheme.colorScheme.background,
    ) { innerModifier ->

        Scaffold(
            modifier = innerModifier
                .fillMaxSize()
                .padding(WindowInsets.statusBars.asPaddingValues()),
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            modifier = Modifier,
                            text = stringResource(id = R.string.settings_title)
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onBackClick) {
                            Icon(
                                Icons.Default.ArrowBack,
                                contentDescription = stringResource(id = R.string.settings_title)
                            )
                        }
                    },
                    scrollBehavior = pinnedScrollBehavior()
                )
            },
            content = { paddingValues ->
                Box(
                    modifier = Modifier
                        .padding(
                            PaddingValues(
                                start = 0.dp,
                                end = 0.dp,
                                top = paddingValues.calculateTopPadding(),
                                bottom = paddingValues.calculateBottomPadding()
                            )
                        )
                        .consumedWindowInsets(paddingValues)
                        .fillMaxSize()
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        SettingsBlock(blockTitle = stringResource(id = R.string.block_view)) {

                            Button(
                                onClick = { },
                                modifier = Modifier.fillMaxWidth(),
                                colors = outlinedButtonColors(),
                                contentPadding = PaddingValues(horizontal = 16.dp),
                                shape = ShapeDefaults.Medium
                            ) {
                                Text(
                                    modifier = Modifier.weight(1f),
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    text = stringResource(id = R.string.setting_theme),
                                )

                                Text(
                                    modifier = Modifier.weight(1f),
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    textAlign = TextAlign.End,
                                    text = stringResource(id = R.string.setting_theme_default),
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp)
                            ) {
                                Text(
                                    modifier = Modifier.align(Alignment.CenterStart),
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    text = stringResource(id = R.string.setting_material_you)
                                )

                                SwitchLight(
                                    modifier = Modifier.align(Alignment.CenterEnd),
                                    checked = false,
                                ) { _ ->

                                }

                            }

                            Text(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                text = stringResource(id = R.string.setting_material_you_description),
                                style = MaterialTheme.typography.bodySmall,
                            )
                        }
                    }
                }
            }
        )
    }
}

@Suppress("SameParameterValue")
@Composable
private inline fun SettingsBlock(
    modifier: Modifier = Modifier,
    blockTitle: String,
    block: @Composable ColumnScope.() -> Unit
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = blockTitle,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary,
        )
        block.invoke(this)
    }
}