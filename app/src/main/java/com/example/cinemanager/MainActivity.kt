package com.example.cinemanager

import PeliculaAdaptador
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var listaPeliculasRecyclerView: RecyclerView
    private lateinit var peliculaAdaptador: PeliculaAdaptador
    private val listaDePeliculas = PeliculaProveedor.peliculas // Asegúrate de que PeliculaProveedor esté inicializado antes de acceder a esto

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        PeliculaProveedor.inicializar(applicationContext) // Inicializar el proveedor
        ValoracionProveedor.inicializar(applicationContext)

        listaPeliculasRecyclerView = findViewById(R.id.listaPeliculasRecyclerView)
        listaPeliculasRecyclerView.layoutManager = LinearLayoutManager(this)

        peliculaAdaptador = PeliculaAdaptador(listaDePeliculas) { pelicula ->
            val intent = Intent(this, DetallePeliculaActivity::class.java)
            intent.putExtra("idPelicula", pelicula.id)
            startActivity(intent)
        }

        listaPeliculasRecyclerView.adapter = peliculaAdaptador
        actualizarValoracionesPeliculas() // Asegúrate de que esta función no cause problemas iniciales
    }

    private fun actualizarValoracionesPeliculas() {
        val valoraciones = ValoracionProveedor.valoraciones

        listaDePeliculas.forEach { pelicula ->
            val valoracionesDePelicula = valoraciones.filter { it.idPelicula == pelicula.id }
            if (valoracionesDePelicula.isNotEmpty()) {
                pelicula.valoracionPromedio = valoracionesDePelicula.sumOf { it.puntuacion.toDouble() }.toFloat() / valoracionesDePelicula.size
            } else {
                pelicula.valoracionPromedio = 0f
            }
        }
        peliculaAdaptador.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        peliculaAdaptador.actualizarLista(PeliculaProveedor.peliculas)
    }
}