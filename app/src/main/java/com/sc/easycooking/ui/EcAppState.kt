package com.sc.easycooking.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun rememberEcAppState(
    navController: NavHostController = rememberNavController()
): EcAppState {
    return remember(navController) {
        EcAppState(navController)
    }
}

@Stable
class EcAppState(
    val navController: NavHostController,
)