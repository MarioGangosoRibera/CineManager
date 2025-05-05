package com.example.cinemanager.model

data class Valoracion (
    val idPelicula: Int,
    val idUsuario: String, // Podría ser un identificador simple por ahora
    val puntuacion: Float
)