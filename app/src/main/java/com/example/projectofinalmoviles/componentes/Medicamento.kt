package com.example.projectofinalmoviles.componentes

import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class Medicamento(
    val id: String = UUID.randomUUID().toString(),
    val nombre: String,
    val horas: Int,
    val dosisTotal: Int,
    val dosisRestantes: Int = dosisTotal,
    val proximaDosisTimestamp: Long = System.currentTimeMillis()


)