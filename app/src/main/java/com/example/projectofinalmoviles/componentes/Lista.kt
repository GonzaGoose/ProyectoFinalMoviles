package com.example.projectofinalmoviles.componentes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.projectofinalmoviles.R


@Composable

fun ListaView(navegante: NavHostController = rememberNavController(),
              viewModel: MedicamentosViewModel) {
    Scaffold(
        bottomBar = {
            Row(Modifier.fillMaxWidth()
                .height(120.dp)
                .background(Color(0xFF34344A)),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(verticalArrangement = Arrangement.Center,horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable{navegante.navigate(route = Lista)}

                ) {
                    Image(
                        painter = painterResource(id = R.drawable.outline_alarm_24),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(Color.White),
                        modifier = Modifier.size(40.dp)

                    )

                    Text("Lista",
                        color = Color.White,
                        fontSize = 20.sp)

                }
                Column(verticalArrangement = Arrangement.Center,horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable{navegante.navigate(route = Anadir)}


                    ) {
                    Image(
                        painter = painterResource(id = R.drawable.outline_add_24),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(Color.White),
                        modifier = Modifier.size(40.dp)

                    )

                    Text("Añadir",
                        color = Color.White,
                        fontSize = 24.sp)

                }
                Column(verticalArrangement = Arrangement.Center,horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = R.drawable.outline_airwave_24),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(Color.White),
                        modifier = Modifier.size(40.dp)

                    )

                    Text("Config.",
                        color = Color.White,
                        fontSize = 24.sp)

                }
            }
        }
    )
    { paddingValues ->
        Column(
            modifier = Modifier.background(color = Color(0xFF1D1D23)).fillMaxSize()
                .padding(paddingValues)
        )
        {
            Row() {
                Text(
                    "Lista",
                    fontSize = 48.sp,
                    color = Color.White
                )
            }
            if (viewModel.medicamentos.isEmpty()) {
                Text(
                    text = "No hay medicamentos agregados",
                    color = Color.White,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                viewModel.medicamentos.forEach { medicamento ->
                    CardAlarma(medicamento = medicamento)
                }
        }

    }
}}

