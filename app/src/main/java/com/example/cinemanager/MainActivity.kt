package com.example.cinemanager

import PeliculaAdaptador
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cinemanager.model.Pelicula
import com.example.cinemanager.model.Valoracion

class MainActivity : AppCompatActivity() {

    private lateinit var listaPeliculasRecyclerView: RecyclerView
    private lateinit var peliculaAdaptador: PeliculaAdaptador
    private val listaDePeliculas = mutableListOf<Pelicula>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Asegúrate de que este es el nombre correcto de tu layout

        listaPeliculasRecyclerView = findViewById(R.id.listaPeliculasRecyclerView) // Usa findViewById
        listaPeliculasRecyclerView.layoutManager = LinearLayoutManager(this)

        // Simulación de datos de películas (reemplaza con tu lógica real)
        listaDePeliculas.addAll(listOf(
            Pelicula(1, "El Padrino", "Un épico drama criminal...", "url1", 4.8f),
            Pelicula(2, "El Caballero Oscuro", "El Joker causa el caos...", "url2", 4.5f),
            Pelicula(3, "Pulp Fiction", "Historias entrelazadas...", "url3", 4.7f)
            // Añade más películas aquí
        ))

        peliculaAdaptador = PeliculaAdaptador(listaDePeliculas) { pelicula ->
            // Acción al hacer clic en una película
            val intent = Intent(this, DetallePeliculaActivity::class.java)
            intent.putExtra("idPelicula", pelicula.id)
            startActivity(intent)
        }

        listaPeliculasRecyclerView.adapter = peliculaAdaptador
        actualizarValoracionesPeliculas() // Simulación de actualización de ratings inicial
    }

    private fun actualizarValoracionesPeliculas() {
        val valoraciones = listOf(
            Valoracion(1, "usuario1", 4.5f),
            Valoracion(1, "usuario2", 5.0f),
            Valoracion(2, "usuario3", 4.0f),
            Valoracion(3, "usuario4", 5.0f),
            Valoracion(3, "usuario5", 4.5f)
        )

        listaDePeliculas.forEach { pelicula ->
            val valoracionesDePelicula = valoraciones.filter { it.idPelicula == pelicula.id }
            if (valoracionesDePelicula.isNotEmpty()) {
                pelicula.valoracionPromedio = valoracionesDePelicula.sumOf { it.puntuacion.toDouble() }.toFloat() / valoracionesDePelicula.size
            }
        }
        peliculaAdaptador.actualizarLista(listaDePeliculas)
    }
}