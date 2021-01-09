package com.emiliorg.myrestaurant.ui.slideshow

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.emiliorg.myrestaurant.R
import com.emiliorg.myrestaurant.model.Pedido
import com.emiliorg.myrestaurant.ui.pedidoDetalle.PedidoDetalle
import java.util.*

class PedidoListAdapter(private val list: ArrayList<Pedido?>?, private val context: Context?, private val parent: Fragment?) : RecyclerView.Adapter<PedidoListAdapter.ViewHolder?>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.pedido_item, parent, false)
        return ViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list?.get(position)
        holder.date?.setText(item?.getDate())
        holder.price?.setText(String.format("%.2f €", item?.getTotal()))
        holder.itemView.setOnClickListener {
            parent?.getActivity()?.getSupportFragmentManager()?.beginTransaction()?.setCustomAnimations(
                    R.anim.slide_in,  //enter
                    R.anim.fade_out,  //exit
                    R.anim.fade_in,  //popEnter
                    R.anim.slide_out //popExit
            )?.replace(R.id.nav_host_fragment, PedidoDetalle(item))?.commit()
        }
    }

    override fun getItemCount(): Int {
        return list?.size!!
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var date: TextView?
        var price: TextView?

        init {
            date = itemView.findViewById(R.id.tvPedidoDate)
            price = itemView.findViewById(R.id.tvPedidoPrecio)
        }
    }
}