import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cinemanager.R
import com.example.cinemanager.model.Pelicula

class PeliculaAdaptador(
    private var listaPeliculas: List<Pelicula>,
    private val onItemClick: (Pelicula) -> Unit
) : RecyclerView.Adapter<PeliculaAdaptador.PeliculaViewHolder>() {

    inner class PeliculaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val posterImageView: ImageView = itemView.findViewById(R.id.posterImageView)
        val tituloTextView: TextView = itemView.findViewById(R.id.tituloTextView)
        val valoracionTextView: TextView = itemView.findViewById(R.id.valoracionTextView)

        fun bind(pelicula: Pelicula) {
            tituloTextView.text = pelicula.titulo
            valoracionTextView.text = String.format("Valoración: %.1f", pelicula.valoracionPromedio)

            //Usar imágenes
            /*Glide.with(itemView.context)
                .load(pelicula.urlPoster)
                .apply(RequestOptions().placeholder(android.R.drawable.ic_menu_gallery))
                .into(posterImageView)*/

            itemView.setOnClickListener {
                onItemClick(pelicula)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeliculaViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pelicula, parent, false)
        return PeliculaViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PeliculaViewHolder, position: Int) {
        holder.bind(listaPeliculas[position])
    }

    override fun getItemCount() = listaPeliculas.size

    fun actualizarLista(nuevaLista: List<Pelicula>) {
        listaPeliculas = nuevaLista
        notifyDataSetChanged()
    }
}