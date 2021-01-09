package com.emiliorg.myrestaurant.ui.Pedido

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.emiliorg.myrestaurant.R
import com.emiliorg.myrestaurant.model.Plato
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*

class PedidoAdapter(private val list: ArrayList<Plato?>?, private val context: Context?, private val parent: Fragment?) : RecyclerView.Adapter<PedidoAdapter.ViewHolder?>() {
    var detallePedido = false
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.item_pedidolist, parent, false)
        return ViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list?.get(position)
        holder.nombre?.setText(item?.getName())
        holder.price?.setText(java.lang.Float.toString(item!!.getPrecio()))
        try {
            Picasso.get().load(item?.getImage()).into(holder.photo)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        if (detallePedido) {
            holder.btnBorrar?.setVisibility(View.INVISIBLE)
        }
        holder.btnBorrar?.setOnClickListener(View.OnClickListener { borrarItem(position) })
    }

    fun borrarItem(position: Int) {
        list?.removeAt(position)
        notifyDataSetChanged()
        (parent as PedidoFragment?)?.calcularPrecio()
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var photo: CircleImageView?
        var nombre: TextView?
        var price: TextView?
        var btnBorrar: Button?

        init {
            photo = itemView.findViewById(R.id.pedidoPhotoPlato)
            nombre = itemView.findViewById(R.id.platoPedidoNombre)
            price = itemView.findViewById(R.id.platoPedidoPrecio)
            btnBorrar = itemView.findViewById(R.id.btnBorrarItemPedido)
        }
    }
}