package com.paf.synthlib

import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.paf.synthlib.Arg.PresetId
import com.paf.synthlib.navigation.Destination.Home
import com.paf.synthlib.navigation.Destination.PresetDetails
import com.paf.synthlib.preset.PresetDetailsScreen

@ExperimentalPermissionsApi
@ExperimentalMaterialApi
@Composable
fun MainScreen() {
    val navController = rememberNavController()
//    val backstackEntry = navController.currentBackStackEntryAsState()
//    var currentScreen = Destination.fromRoute(backstackEntry.value?.destination?.route)

    Scaffold { innerPadding ->
        AppNavHost(navController, modifier = Modifier.padding(innerPadding))
    }
}

@ExperimentalPermissionsApi
@ExperimentalMaterialApi
@Composable
fun AppNavHost(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = Home.name,
        modifier = modifier
    ) {
        composable(Home.name) {
            HomeScreen(
                onClickPreset = { navController.navigateToPreset(it ?: -1) }
            )
        }
        composable(
            route = "${PresetDetails.name}/{$PresetId}",
            arguments = listOf(navArgument(PresetId) {
                type = NavType.LongType
                defaultValue = -1
            })
        ) { backStackEntry ->
            val presetId = backStackEntry.arguments?.getLong(PresetId)
            PresetDetailsScreen(presetId = presetId ?: -1)
        }
    }
}

internal fun NavController.navigateToPreset(presetId: Long?) {
    navigate("${PresetDetails.name}/$presetId")
}

internal object Arg {
    const val PresetId = "PresetId"
    const val Arg_Bitmap = "Arg_Bitmap"
}