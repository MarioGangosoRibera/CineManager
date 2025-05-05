import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cinemanager.R
import com.example.cinemanager.model.Valoracion

class ValoracionAdaptador(private var listaValoraciones: List<Valoracion>) :
    RecyclerView.Adapter<ValoracionAdaptador.ValoracionViewHolder>() {

    inner class ValoracionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val usuarioTextView: TextView = itemView.findViewById(R.id.usuarioTextView)
        val barraValoracionItem: RatingBar = itemView.findViewById(R.id.barraValoracionItem)

        fun bind(valoracion: Valoracion) {
            usuarioTextView.text = valoracion.idUsuario
            barraValoracionItem.rating = valoracion.puntuacion
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ValoracionViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_valoracion, parent, false)
        return ValoracionViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ValoracionViewHolder, position: Int) {
        holder.bind(listaValoraciones[position])
    }

    override fun getItemCount() = listaValoraciones.size

    fun actualizarLista(nuevaLista: List<Valoracion>) {
        listaValoraciones = nuevaLista
        notifyDataSetChanged()
    }
}