package com.example.projectofinalmoviles.componentes


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.projectofinalmoviles.R


@Composable

fun AnadirView(navegante: NavHostController = rememberNavController(),
               viewModel: MedicamentosViewModel) {
    var nombre by remember { mutableStateOf("") }
    var horas by remember { mutableStateOf("") }
    var dosis by remember { mutableStateOf("") }
    Scaffold(
        bottomBar = {
            Row(Modifier.fillMaxWidth()
                .height(120.dp)
                .background(Color(0xFF34344A)),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(verticalArrangement = Arrangement.Center,horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable{navegante.navigate(route = Lista)}) {
                    Image(
                        painter = painterResource(id = R.drawable.outline_alarm_24),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(Color.White),
                        modifier = Modifier.size(40.dp)

                    )

                    Text("Lista",
                        color = Color.White,
                        fontSize = 24.sp)

                }
                Column(verticalArrangement = Arrangement.Center,horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable{navegante.navigate(route = Anadir)}) {
                    Image(
                        painter = painterResource(id = R.drawable.outline_add_24),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(Color.White),
                        modifier = Modifier.size(40.dp)

                    )

                    Text("Añadir",
                        color = Color.White,
                        fontSize = 20.sp)

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
                    "Añadir",
                    fontSize = 48.sp,
                    color = Color.White
                )
            }
            Card(
                modifier = Modifier
                    .width(400.dp)
                    .padding(4.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF34344A)
                )




            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth())
                {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                            .width(180.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(Modifier.height(36.dp))
                        Text(
                            text = "Nuevo Medicamento",
                            fontSize = 36.sp,
                            color = Color.White
                        )
                        Spacer(Modifier.height(36.dp))

                        Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Nombre:",
                                fontSize = 28.sp,
                                color = Color.White
                            )

                            TextField(
                                value = nombre,
                                onValueChange = { nuevoTexto -> nombre = nuevoTexto },
                                label = { Text("Escribe aquí") },
                                modifier = Modifier.height(30.dp)

                            )

                        }

                        Spacer(Modifier.height(36.dp))



                        Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Una dosis cada:",
                                fontSize = 20.sp,
                                color = Color.White
                            )

                            TextField(
                                value = horas,
                                onValueChange = { nuevoTexto -> horas = nuevoTexto },
                                label = { Text("Escribe aquí") },
                                modifier = Modifier.width(150.dp).height(30.dp)
                            )

                            Text(
                                text = "Horas",
                                fontSize = 20.sp,
                                color = Color.White
                            )

                        }
                        Spacer(Modifier.height(36.dp))
                        Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Empiezo con lo suficiente para",
                                fontSize = 24.sp,
                                color = Color.White
                            )
                        }
                        Spacer(Modifier.height(24.dp))
                        Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                            TextField(
                                value = dosis,
                                onValueChange = { nuevoTexto -> dosis = nuevoTexto },
                                label = { Text("Escribe aquí", color = Color.White) },
                                modifier = Modifier.width(150.dp).height(30.dp),
                                textStyle = androidx.compose.ui.text.TextStyle(color = Color.White)
                            )

                            Text(
                                text = "dosis",
                                fontSize = 24.sp,
                                color = Color.White
                            )


                        }
                        Spacer(Modifier.height(36.dp))

                        Button(
                            onClick = {
                                if (nombre.isNotBlank() && horas.isNotBlank() && dosis.isNotBlank()) {
                                    val nuevoMedicamento = Medicamento(
                                        nombre = nombre,
                                        horas = horas.toIntOrNull() ?: 0,
                                        dosisTotal = dosis.toIntOrNull() ?: 0
                                    )
                                    viewModel.agregarMedicamento(nuevoMedicamento)
                                    nombre = ""
                                    horas = ""
                                    dosis = ""
                                    navegante.navigate(route = Lista)
                                }
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Yellow,
                                contentColor = Color.Black
                            )
                        ) {
                            Text("Confirmar")
                        }

                        Spacer(Modifier.height(36.dp))


                    }






                }

            }
        }

    }
}

