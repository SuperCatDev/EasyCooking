package com.sc.easycooking.ui

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.consumedWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sc.easycooking.navigation.EcNavHost
import com.sc.easycooking.ui.theme.EasyCookingTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun EcApp(
    appState: EcAppState = rememberEcAppState()
) {
    EasyCookingTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = MaterialTheme.colorScheme.background
        ) { padding ->
            EcNavHost(
                navController = appState.navController,
                modifier = Modifier
                    .padding(padding)
                    .consumedWindowInsets(padding)
            )
        }
    }

}