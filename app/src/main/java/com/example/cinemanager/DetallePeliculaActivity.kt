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
        valoracionAdaptador = ValoracionAdaptador(listaDeValoraciones)
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

                // Simular la obtención de valoraciones para esta película
                val valoracionesDePelicula = obtenerValoracionesDePelicula(idPelicula)
                listaDeValoraciones.addAll(valoracionesDePelicula)
                valoracionAdaptador.actualizarLista(listaDeValoraciones)
            }
        }

        botonEnviarValoracion.setOnClickListener {
            val puntuacion = barraValoracion.rating
            // Aquí deberías obtener el ID del usuario real en una aplicación real
            val idUsuario = "usuarioActual"
            val nuevaValoracion = Valoracion(idPelicula, idUsuario, puntuacion)
            listaDeValoraciones.add(nuevaValoracion)
            valoracionAdaptador.actualizarLista(listaDeValoraciones)
            // Aquí también deberías guardar la nueva valoración (por ahora solo la mostramos)
            actualizarValoracionPromedio()
            barraValoracion.rating = 0f // Resetear la barra de valoración después de enviar
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

    // Simulación de obtención de valoraciones para una película
    private fun obtenerValoracionesDePelicula(idPelicula: Int): List<Valoracion> {
        // En una aplicación real, esto vendría de una base de datos
        return when (idPelicula) {
            1 -> listOf(Valoracion(1, "usuario1", 4.5f), Valoracion(1, "usuario2", 5.0f))
            2 -> listOf(Valoracion(2, "usuario3", 4.0f))
            3 -> listOf(Valoracion(3, "usuario4", 5.0f), Valoracion(3, "usuario5", 4.5f))
            else -> emptyList()
        }
    }

    private fun actualizarValoracionPromedio() {
        // En una aplicación real, la actualización de la valoración promedio
        // probablemente se haría en la capa de datos y se reflejaría en la lista principal.
        // Por ahora, solo lo simulamos.
        val peliculaActual = obtenerPeliculaPorId(idPelicula)
        peliculaActual?.let { pelicula ->
            val valoracionesDePelicula = listaDeValoraciones.filter { it.idPelicula == pelicula.id }
            if (valoracionesDePelicula.isNotEmpty()) {
                pelicula.valoracionPromedio = valoracionesDePelicula.sumOf { it.puntuacion.toDouble() }.toFloat() / valoracionesDePelicula.size
                // Aquí podrías tener una forma de notificar al MainActivity para que actualice la lista
                println("Nueva valoración promedio para ${pelicula.titulo}: ${pelicula.valoracionPromedio}")
            }
        }
    }
}