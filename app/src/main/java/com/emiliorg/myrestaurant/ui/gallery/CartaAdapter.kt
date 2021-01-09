package com.emiliorg.myrestaurant.ui.gallery

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.emiliorg.myrestaurant.R
import com.emiliorg.myrestaurant.model.Plato
import com.emiliorg.myrestaurant.ui.plato.PlatoDetalles
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*

class CartaAdapter(private val list: ArrayList<Plato?>?, private val context: Context?, private val parent: Fragment?) : RecyclerView.Adapter<CartaAdapter.ViewHolder?>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.item_carta_list, parent, false)
        return ViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list?.get(position)
        holder.nombre?.setText(item?.getName())
        holder.price?.setText(java.lang.Float.toString(item?.getPrecio()!!))
        try {
            Picasso.get().load(item?.getImage()).into(holder.photo)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        holder.itemView.setOnClickListener {
            val platoDetalle: Fragment = PlatoDetalles(item)
            (parent as GalleryFragment?)?.platoDetalle(platoDetalle)
        }
    }

    override fun getItemCount(): Int {
        return list?.size!!
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var photo: CircleImageView?
        var nombre: TextView?
        var price: TextView?

        init {
            photo = itemView.findViewById(R.id.platoPhoto)
            nombre = itemView.findViewById(R.id.platoName)
            price = itemView.findViewById(R.id.platoPrice)
        }
    }
}