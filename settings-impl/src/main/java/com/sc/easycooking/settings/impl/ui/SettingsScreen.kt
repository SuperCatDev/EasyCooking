@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)

package com.sc.easycooking.settings.impl.ui

import android.os.Build
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.consumedWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.outlinedButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.sc.easycooking.settings.api.models.ThemeSetting
import com.sc.easycooking.settings.impl.R
import com.sc.easycooking.settings.impl.presentation.SettingsListViewModel
import com.sc.easycooking.view_ext.insets.WrapWithColoredSystemBars
import com.sc.easycooking.view_ext.views.SwitchLight

@Composable
internal fun SettingsScreenRoute(
    modifier: Modifier = Modifier,
    viewModel: SettingsListViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
) {
    SettingsScreen(modifier, viewModel, onBackClick)
}

@Suppress("SameParameterValue")
@Composable
internal fun SettingsScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingsListViewModel,
    onBackClick: () -> Unit,
) {
    val selectedThemeState = viewModel.observeSelectedTheme().collectAsState()

    val showDialog = remember { mutableStateOf(false) }

    if (showDialog.value) {
        ThemeDialog(selectedThemeState.value, viewModel) {
            showDialog.value = it
        }
    }

    WrapWithColoredSystemBars(
        modifier = modifier,
        navBarColor = MaterialTheme.colorScheme.background,
    ) { innerModifier ->

        val materialYouState = viewModel.observeMaterialYou().collectAsState()

        Scaffold(
            modifier = innerModifier
                .fillMaxSize()
                .padding(WindowInsets.statusBars.asPaddingValues()),
            topBar = {

                // I don't use TopAppBar here because it blinks in a strange way when change a theme
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    IconButton(modifier = Modifier.padding(start = 4.dp), onClick = onBackClick) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = stringResource(id = com.sc.easycooking.view_ext.R.string.back),
                        )
                    }

                    Spacer(modifier = Modifier.padding(start = 4.dp))

                    Text(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        text = stringResource(id = R.string.settings_title),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
                // Uncomment in case TopAppBar blinks are fixed when theme changed
/*
                TopAppBar(
                    modifier = Modifier.background(MaterialTheme.colorScheme.background),
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
                )*/
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
                                onClick = { showDialog.value = true },
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
                                    text = selectedThemeState.value.getStringValue(),
                                )
                            }

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp)
                                ) {
                                    Text(
                                        modifier = Modifier
                                            .align(Alignment.CenterStart)
                                            // space for switch at the end
                                            .padding(end = 36.dp),
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = MaterialTheme.colorScheme.onBackground,
                                        text = stringResource(id = R.string.setting_material_you)
                                    )

                                    SwitchLight(
                                        modifier = Modifier.align(Alignment.CenterEnd),
                                        checked = materialYouState.value,
                                    ) { checked ->
                                        viewModel.changeMaterialYou(checked)
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

@Composable
private fun ThemeDialog(
    selectedTheme: ThemeSetting,
    viewModel: SettingsListViewModel,
    setShowDialog: (Boolean) -> Unit
) {
    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(Modifier.padding(vertical = 16.dp)) {
                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    text = stringResource(id = R.string.setting_theme_dialog_title),
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.padding(top = 8.dp))

                DialogRadioButtonRow(
                    text = stringResource(id = R.string.setting_theme_default),
                    selected = selectedTheme == ThemeSetting.DEFAULT,
                    onClick = {
                        viewModel.changeTheme(ThemeSetting.DEFAULT)
                        setShowDialog(false)
                    },
                )
                DialogRadioButtonRow(
                    text = stringResource(id = R.string.setting_theme_light),
                    selected = selectedTheme == ThemeSetting.LIGHT,
                    onClick = {
                        viewModel.changeTheme(ThemeSetting.LIGHT)
                        setShowDialog(false)
                    },
                )
                DialogRadioButtonRow(
                    text = stringResource(id = R.string.setting_theme_dark),
                    selected = selectedTheme == ThemeSetting.DARK,
                    onClick = {
                        viewModel.changeTheme(ThemeSetting.DARK)
                        setShowDialog(false)
                    },
                )

                Box(modifier = Modifier.fillMaxWidth()) {
                    TextButton(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(horizontal = 16.dp),
                        onClick = { setShowDialog(false) }) {
                        Text(
                            text = stringResource(id = com.sc.easycooking.view_ext.R.string.cancel),
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DialogRadioButtonRow(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = outlinedButtonColors(),
        contentPadding = PaddingValues(horizontal = 0.dp),
        shape = ShapeDefaults.Medium,
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
            RadioButton(
                modifier = Modifier.padding(start = 2.dp),
                selected = selected,
                onClick = onClick,
            )
            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                color = MaterialTheme.colorScheme.onBackground,
                text = text,
                style = MaterialTheme.typography.labelLarge,
            )
        }
    }
}

@Composable
private fun ThemeSetting.getStringValue(): String {
    return when (this) {
        ThemeSetting.DEFAULT -> stringResource(id = R.string.setting_theme_default)
        ThemeSetting.LIGHT -> stringResource(id = R.string.setting_theme_light)
        ThemeSetting.DARK -> stringResource(id = R.string.setting_theme_dark)
    }
}