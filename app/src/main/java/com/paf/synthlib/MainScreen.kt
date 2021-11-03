package com.paf.synthlib

import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.paf.synthlib.Arg.Arg_Preset
import com.paf.synthlib.domain.preset.model.Preset
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
                onClickPreset = { navController.navigateToPreset(it) }
            )
        }
        composable(PresetDetails.name) {
            val args = navController.previousBackStackEntry?.arguments
            PresetDetailsScreen(preset = args?.getParcelable(Arg_Preset))
        }
    }
}

internal fun NavController.navigateToPreset(preset: Preset?) {
    currentBackStackEntry?.arguments?.putParcelable(Arg_Preset, preset)
    navigate(PresetDetails.name)
}

internal object Arg {
    const val Arg_Preset = "Arg_Preset"
    const val Arg_Bitmap = "Arg_Bitmap"
}