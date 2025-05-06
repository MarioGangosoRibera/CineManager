package com.example.cinemanager

import ValoracionAdaptador
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cinemanager.model.Pelicula
import com.example.cinemanager.model.Valoracion

class DetallePeliculaActivity : AppCompatActivity() {

    private lateinit var tituloTextView: TextView
    private lateinit var descripcionTextView: TextView
    private lateinit var posterImageView: ImageView // Si decides volver a añadir imágenes
    private lateinit var barraValoracion: RatingBar
    private lateinit var botonEnviarValoracion: Button
    private lateinit var listaValoracionesRecyclerView: RecyclerView
    private lateinit var valoracionAdaptador: ValoracionAdaptador
    private val listaDeValoraciones = mutableListOf<Valoracion>()
    private var idPelicula: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_pelicula)

        // Inicializar vistas
        tituloTextView = findViewById(R.id.detalleTituloTextView)
        descripcionTextView = findViewById(R.id.detalleDescripcionTextView)
        posterImageView = findViewById(R.id.detallePosterImageView) // Si decides volver a añadir imágenes
        barraValoracion = findViewById(R.id.barraValoracion)
        botonEnviarValoracion = findViewById(R.id.botonEnviarValoracion)
        listaValoracionesRecyclerView = findViewById(R.id.listaValoracionesRecyclerView)

        listaValoracionesRecyclerView.layoutManager = LinearLayoutManager(this)
        valoracionAdaptador = ValoracionAdaptador(mutableListOf())
        listaValoracionesRecyclerView.adapter = valoracionAdaptador

        // Obtener el ID de la película de la actividad anterior
        idPelicula = intent.getIntExtra("idPelicula", -1)

        // Simular la obtención de los detalles de la película y sus valoraciones
        if (idPelicula != -1) {
            val pelicula = obtenerPeliculaPorId(idPelicula)
            pelicula?.let {
                tituloTextView.text = it.titulo
                descripcionTextView.text = it.descripcion
                // Si decides volver a añadir imágenes:
                /*Glide.with(this)
                    .load(it.urlPoster)
                    .apply(RequestOptions().placeholder(android.R.drawable.ic_menu_gallery))
                    .into(posterImageView)*/

                // Cargar las valoraciones existentes para esta película
                val valoracionesDePelicula = ValoracionProveedor.getValoracionesPorPelicula(idPelicula)
                valoracionAdaptador.actualizarLista(valoracionesDePelicula.toMutableList())
                actualizarValoracionPromedioLocal(it, valoracionesDePelicula)
            }
        }

        botonEnviarValoracion.setOnClickListener {
            val puntuacion = barraValoracion.rating
            val idUsuario = "usuarioActual" // Deberías obtener el usuario real
            val nuevaValoracion = Valoracion(idPelicula, idUsuario, puntuacion)
            ValoracionProveedor.addValoracion(nuevaValoracion) // Añadir al proveedor
            val valoracionesActualizadas = ValoracionProveedor.getValoracionesPorPelicula(idPelicula)
            valoracionAdaptador.actualizarLista(valoracionesActualizadas.toMutableList())
            actualizarValoracionPromedioLocal(PeliculaProveedor.peliculas.find { it.id == idPelicula }, valoracionesActualizadas)
            barraValoracion.rating = 0f
        }
    }

    // Simulación de obtención de una película por ID
    private fun obtenerPeliculaPorId(id: Int): Pelicula? {
        // En una aplicación real, esto vendría de una base de datos o una API
        return when (id) {
            1 -> Pelicula(1, "El Padrino", "Un épico drama criminal...", "https://image.tmdb.org/t/p/w500/3bhkrj58Vtu7enYsRolD1fZdja1.jpg", 4.8f)
            2 -> Pelicula(2, "El Caballero Oscuro", "El Joker causa el caos...", "https://image.tmdb.org/t/p/w500/qJ2tW6WMUDux911zh6rGI7vRn8T.jpg", 4.5f)
            3 -> Pelicula(3, "Pulp Fiction", "Historias entrelazadas...", "https://image.tmdb.org/t/p/w500/f2g405Cm87nEEbkLhX971Y244g0.jpg", 4.7f)
            else -> null
        }
    }

    private fun actualizarValoracionPromedioLocal(pelicula: Pelicula?, valoraciones: List<Valoracion>) {
        pelicula?.let {
            if (valoraciones.isNotEmpty()) {
                it.valoracionPromedio = valoraciones.sumOf { v -> v.puntuacion.toDouble() }.toFloat() / valoraciones.size
                // Aquí podrías tener una forma de notificar a MainActivity si la lista se muestra allí
                println("Nueva valoración promedio para ${it.titulo}: ${it.valoracionPromedio}")
            } else {
                it.valoracionPromedio = 0f // O algún valor por defecto si no hay valoraciones
            }
        }
    }
}