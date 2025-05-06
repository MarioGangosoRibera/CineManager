package com.example.cinemanager.model

import kotlinx.serialization.Serializable

@Serializable
data class Valoracion (
    val idPelicula: Int,
    val idUsuario: String, // Podría ser un identificador simple por ahora
    val puntuacion: Float
)