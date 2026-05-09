package com.example.projectofinalmoviles.componentes

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable

@Serializable
object Lista

@Serializable
object Anadir

@Serializable
object Configuracion

@Preview(showBackground = true)
@Composable
fun NavManager(){
    val navController = rememberNavController()
    val context = LocalContext.current

    val viewModel: MedicamentosViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MedicamentosViewModel(context) as T
            }
        }
    )

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

        composable<Configuracion> {
            ConfigView(navegante = navController, viewModel)
        }
    }
}