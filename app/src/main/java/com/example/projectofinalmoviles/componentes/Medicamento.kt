package com.example.projectofinalmoviles.componentes


import java.util.UUID

data class Medicamento(
    val id: String = UUID.randomUUID().toString(),
    val nombre: String,
    val horas: Int,
    val dosisTotal: Int
)