package com.example.cinemanager.model

import kotlinx.serialization.Serializable

@Serializable
data class Pelicula(
    val id: Int,
    val titulo: String,
    val descripcion: String,
    val urlPoster: String?, // URL de la imagen del póster
    var valoracionPromedio: Float = 0f // Valoración promedio (inicializada en 0)
)