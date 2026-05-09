package com.example.projectofinalmoviles.componentes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.projectofinalmoviles.R

@Composable
fun ConfigView(
    navegante: NavHostController = rememberNavController(),
    viewModel: MedicamentosViewModel
) {

    var sonidoSeleccionado by remember { mutableStateOf(viewModel.obtenerSonidoSeleccionado())  }
    var volumen by remember { mutableStateOf(viewModel.obtenerVolumen())  }
    var expanded by remember { mutableStateOf(false) }

    val opcionesSonido = listOf("Predeterminado", "Alternativo")

    Scaffold(
        bottomBar = {
            Row(
                Modifier.fillMaxWidth()
                    .height(120.dp)
                    .background(Color(0xFF34344A)),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable { navegante.navigate(route = Lista) }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.outline_alarm_24),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(Color.White),
                        modifier = Modifier.size(40.dp)
                    )
                    Text(stringResource(R.string.nav_lista), color = Color.White, fontSize = 20.sp)
                }

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable { navegante.navigate(route = Anadir) }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.outline_add_24),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(Color.White),
                        modifier = Modifier.size(40.dp)
                    )
                    Text(stringResource(R.string.nav_anadir), color = Color.White, fontSize = 20.sp)
                }

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.outline_airwave_24),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(Color.White),
                        modifier = Modifier.size(40.dp)
                    )
                    Text(stringResource(R.string.nav_config), color = Color.White, fontSize = 24.sp)
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .background(color = Color(0xFF1D1D23))
                .fillMaxSize()
                .padding(paddingValues)
        ) {


            Row {
                Text(stringResource(R.string.titulo_config), fontSize = 48.sp, color = Color.White)
            }

            Spacer(Modifier.height(16.dp))


            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF34344A))
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                ) {
                    Text(
                        text = stringResource(R.string.config_alarma),
                        fontSize = 32.sp,
                        color = Color.White
                    )

                    Spacer(Modifier.height(48.dp))

                    Text(
                        text = stringResource(R.string.sonido_alarma),
                        fontSize = 24.sp,
                        color = Color.White
                    )

                    Spacer(Modifier.height(16.dp))

                    Box {
                        Button(
                            onClick = { expanded = true },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF4A4A5A),
                                contentColor = Color.White
                            )
                        ) {
                            Text(sonidoSeleccionado, fontSize = 18.sp)
                        }

                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            modifier = Modifier.background(Color(0xFF4A4A5A))
                        ) {
                            opcionesSonido.forEach { opcion ->
                                DropdownMenuItem(
                                    text = { Text(opcion, color = Color.White, fontSize = 16.sp) },
                                    onClick = {
                                        sonidoSeleccionado = opcion
                                        expanded = false

                                        viewModel.guardarPreferenciaSonido(opcion)
                                    }
                                )
                            }
                        }
                    }

                    Spacer(Modifier.height(48.dp))


                    Text(
                        text = stringResource(R.string.volumen_alarma),
                        fontSize = 24.sp,
                        color = Color.White
                    )

                    Spacer(Modifier.height(16.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {

                        Image(
                            painter = painterResource(id = R.drawable.outline_arrow_drop_down_24),
                            contentDescription = "Volumen bajo",
                            colorFilter = ColorFilter.tint(Color.White),
                            modifier = Modifier.size(32.dp)
                        )

                        Slider(
                            value = volumen,
                            onValueChange = { nuevoVolumen ->
                                volumen = nuevoVolumen
                                viewModel.guardarPreferenciaVolumen(nuevoVolumen)
                            },
                            valueRange = 0f..1f,
                            modifier = Modifier
                                .width(200.dp)
                                .padding(horizontal = 16.dp),
                            colors = androidx.compose.material3.SliderDefaults.colors(
                                thumbColor = Color.Yellow,
                                activeTrackColor = Color.Yellow,
                                inactiveTrackColor = Color.Gray
                            )
                        )

                        Image(
                            painter = painterResource(id = R.drawable.outline_arrow_drop_up_24),
                            contentDescription = "Volumen alto",
                            colorFilter = ColorFilter.tint(Color.White),
                            modifier = Modifier.size(32.dp)
                        )
                    }


                    Text(
                        text = "${(volumen * 100).toInt()}%",
                        fontSize = 18.sp,
                        color = Color.Yellow,
                        modifier = Modifier.padding(top = 8.dp)
                    )

                    Spacer(Modifier.height(48.dp))


                    /* Button(
                        onClick = {
                            viewModel.probarSonido(sonidoSeleccionado, volumen)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Yellow,
                            contentColor = Color.Black
                        ),
                        modifier = Modifier.fillMaxWidth(0.6f)
                    ) {
                        Text(stringResource(R.string.probar_sonido), fontSize = 20.sp)
                    } */

                    Spacer(Modifier.height(24.dp))


                    /*Button(
                        onClick = {
                            sonidoSeleccionado = "Predeterminado"
                            volumen = 0.7f
                            viewModel.guardarPreferenciaSonido("Predeterminado")
                            viewModel.guardarPreferenciaVolumen(0.7f)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF4A4A5A),
                            contentColor = Color.White
                        ),
                        modifier = Modifier.fillMaxWidth(0.6f)
                    ) {
                        Text(stringResource(R.string.restablecer), fontSize = 20.sp)
                    }

                     */
                }
            }
        }
    }
}