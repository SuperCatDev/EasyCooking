package com.sc.easycooking

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sc.easycooking.presentation.WellnessViewModel
import com.sc.easycooking.settings.api.domain.SettingsInteractor
import com.sc.easycooking.settings.api.models.DisplaySettings
import com.sc.easycooking.settings.api.models.ThemeSetting
import com.sc.easycooking.ui.EcApp
import com.sc.easycooking.ui.WellnessTasksList
import com.sc.easycooking.ui.counter.StatefulCounter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var settingsInteractor: SettingsInteractor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val displaySettingsState = settingsInteractor.getDisplaySettingsFlow().collectAsState()
            EcApp(displaySettings = displaySettingsState)
        }
    }
}

@Composable
fun WellnessScreen(
    modifier: Modifier = Modifier,
    wellnessViewModel: WellnessViewModel = viewModel()
) {
    Column(modifier = modifier) {
        StatefulCounter()

        WellnessTasksList(
            list = wellnessViewModel.tasks,
            onCheckedTask = { task, checked ->
                wellnessViewModel.changeTaskChecked(task, checked)
            },
            onCloseTask = { task ->
                wellnessViewModel.remove(task)
            }
        )
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val displaySettingsState = remember {
        mutableStateOf(
            DisplaySettings(
                themeSetting = ThemeSetting.DEFAULT,
                materialYouEnabled = true,
            )
        )
    }

    EcApp(
        displaySettings = displaySettingsState
    )
}