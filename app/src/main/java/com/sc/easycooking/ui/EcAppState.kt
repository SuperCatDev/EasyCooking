package com.sc.easycooking.ui

import android.os.Trace
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sc.easycooking.navigation.EcNavigationDestination

public inline fun <T> trace(label: String, crossinline block: () -> T): T {
    try {
        Trace.beginSection(label)
        return block()
    } finally {
        Trace.endSection()
    }
}

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
) {
    fun navigate(destination: EcNavigationDestination, route: String? = null) {
        trace("Navigation: $destination") {
            navController.navigate(route ?: destination.route)
            // in case of BottomNavigation - TopLevelDestination is a tab
            /*
            if (destination is TopLevelDestination) {
                navController.navigate(route ?: destination.route) {
                    // Pop up to the start destination of the graph to
                    // avoid building up a large stack of destinations
                    // on the back stack as users select items
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    // Avoid multiple copies of the same destination when
                    // reselecting the same item
                    launchSingleTop = true
                    // Restore state when reselecting a previously selected item
                    restoreState = true
                }
            } else {
                navController.navigate(route ?: destination.route)
            }*/
        }
    }

    fun onBackClick() {
        navController.popBackStack()
    }
}