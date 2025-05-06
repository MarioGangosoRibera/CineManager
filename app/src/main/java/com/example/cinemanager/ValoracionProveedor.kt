package com.example.cinemanager

import android.content.Context
import com.example.cinemanager.PeliculaProveedor.Companion.appContext
import com.example.cinemanager.model.Valoracion

class ValoracionProveedor {
    companion object{
        private val _valoraciones = mutableListOf<Valoracion>()
        val valoraciones: List<Valoracion> = _valoraciones

        fun inicializar(context: Context){
            appContext = context.applicationContext
        }

        fun addValoracion(valoracion: Valoracion){
            _valoraciones.add(valoracion)
        }

        fun getValoracionesPorPelicula(idPelicula: Int): List<Valoracion>{
            return _valoraciones.filter { it.idPelicula == idPelicula }
        }
    }
}