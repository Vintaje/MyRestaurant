package com.emiliorg.myrestaurant.ui.Pedido

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.emiliorg.myrestaurant.MainActivity
import com.emiliorg.myrestaurant.R
import com.emiliorg.myrestaurant.model.Pedido
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class PedidoFragment(var pedido: Pedido?) : Fragment() {
    var totalprecio = 0f
    private var total: TextView? = null
    private var fecha: TextView? = null
    private var recyclerView: RecyclerView? = null
    private var btnCompletar: Button? = null
    private var root: View? = null

    //Recycler Adapter
    private var adapter: PedidoAdapter? = null
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        root = inflater.inflate(R.layout.fragment_pedido, container, false)
        initUI()
        (activity as MainActivity)?.getSupportActionBar()?.setTitle("Pedido")
        selectIdPedido()
        return root
    }

    fun selectIdPedido() {
        (activity as MainActivity?)?.db?.getReference("Pedidos")?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                pedido?.setId_pedido( (dataSnapshot.childrenCount.toInt() + 1))
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
        pedido?.setStatus(0)
        pedido?.setUser_id((activity as MainActivity?)?.auth?.uid)
    }

    fun initUI() {
        total = root?.findViewById(R.id.tvTotalPedido)
        fecha = root?.findViewById(R.id.tvFechaPedido)
        btnCompletar = root?.findViewById(R.id.btnCompletarPedido)
        recyclerView = root?.findViewById(R.id.recyclerPedido)
        val currentTime = Calendar.getInstance().time
        val dateFormat: DateFormat = SimpleDateFormat(getString(R.string.datetimeFormat))
        pedido?.setDate(dateFormat.format(currentTime))
        fecha?.setText(pedido?.getDate())
        recyclerView?.setHasFixedSize(true)
        recyclerView?.setLayoutManager(LinearLayoutManager(context))
        adapter = PedidoAdapter(pedido?.getItems(), context, this)
        recyclerView?.setAdapter(adapter)
        calcularPrecio()
        btnCompletar?.setOnClickListener(View.OnClickListener {
            if (pedido?.getItems()?.size!! > 0) {
                completarPedido()
            } else {
                Toast.makeText(context, "El pedido no puede estar vacio", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun calcularPrecio() {
        totalprecio = 0.00f
        for (plato in pedido?.getItems()!!) {
            totalprecio += plato?.getPrecio()!!
        }
        total?.setText(String.format("%.2f €", totalprecio))
        pedido?.setTotal(totalprecio)
    }

    fun completarPedido() {
        val dialogClickListener = DialogInterface.OnClickListener { dialog, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    //Yes button clicked
                    (activity as MainActivity?)?.db?.getReference("Pedidos")?.child(Integer.toString(pedido?.getId_pedido()!!))?.setValue(pedido)
                    (activity as MainActivity?)?.pedido?.getItems()?.clear()
                    (activity as MainActivity?)?.getSupportFragmentManager()?.popBackStack()
                }
                DialogInterface.BUTTON_NEGATIVE -> {
                }
            }
        }
        val builder = AlertDialog.Builder(context)
        builder.setMessage("¿Quieres completar tu pedido?").setPositiveButton("Si", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show()
    }
}