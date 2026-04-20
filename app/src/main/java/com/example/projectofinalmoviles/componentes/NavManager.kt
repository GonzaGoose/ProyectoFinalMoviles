package com.example.projectofinalmoviles.componentes

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable

@Serializable
object Lista

@Serializable
object Anadir

@Preview(showBackground = true)
@Composable
fun NavManager(){
    val navController = rememberNavController()
    val viewModel: MedicamentosViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Lista
    ) {
        composable<Lista> {
            ListaView(navegante = navController, viewModel)
        }

        composable<Anadir> {
            AnadirView(navegante = navController, viewModel)
        }
    }
}