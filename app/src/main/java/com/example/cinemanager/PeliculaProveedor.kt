package com.example.cinemanager

import android.content.Context
import com.example.cinemanager.model.Pelicula
import kotlinx.serialization.json.Json

class PeliculaProveedor {
    companion object{
        var appContext: Context?= null
        private var _peliculas: MutableList<Pelicula>?=null

        fun inicializar(context: Context) {
            appContext = context.applicationContext
        }

        val peliculas: MutableList<Pelicula>
            get() {
                if (_peliculas==null){
                    _peliculas = cargarPeliculasDesdeJson()
                }
                return _peliculas!!
            }
        private fun cargarPeliculasDesdeJson(): MutableList<Pelicula>{
            return try {
                val context = appContext ?: throw IllegalArgumentException("PeliculaProveedor no ha sido inicializado")
                val inputStream = context.assets.open("peliculas.json")
                val jsonString = inputStream.bufferedReader().use { it.readText() }
                Json.decodeFromString(jsonString)
            } catch (e: Exception){
                println("Error al cargar las peliculas: ${e.message}") // Imprime el mensaje del error
                mutableListOf()
            }
        }
    }
}