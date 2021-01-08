package com.emiliorg.myrestaurant.ui.pedidoDetalle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.emiliorg.myrestaurant.MainActivity
import com.emiliorg.myrestaurant.R
import com.emiliorg.myrestaurant.model.Pedido
import com.emiliorg.myrestaurant.ui.Pedido.PedidoAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

/**
 * A simple [Fragment] subclass.
 * Use the [PedidoDetalle.newInstance] factory method to
 * create an instance of this fragment.
 */
class PedidoDetalle : Fragment {
    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null
    var root: View? = null
    private var fecha: TextView? = null
    private var status: ProgressBar? = null
    private var total: TextView? = null
    private var recyclerView: RecyclerView? = null
    private var adapter: PedidoAdapter? = null
    private var pedido: Pedido? = null
    private var thisFragment: Fragment? = null
    private var listener: ValueEventListener? = null

    constructor() {
        // Required empty public constructor
    }

    constructor(pedido: Pedido?) {
        this.pedido = pedido
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments?.getString(ARG_PARAM1)
            mParam2 = arguments?.getString(ARG_PARAM2)
        }
        thisFragment = this
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_pedido_detalle, container, false)
        initUI()
        return root
    }

    fun initUI() {
        recyclerView = root?.findViewById(R.id.recyclerPedidoDetalle)
        fecha = root?.findViewById(R.id.tvFechaPedidoDetalle)
        status = root?.findViewById(R.id.progressBar)
        total = root?.findViewById(R.id.tvTotalPedido)
        adapter = PedidoAdapter(pedido?.getItems(), context, thisFragment)
        adapter?.detallePedido = true
        recyclerView?.setHasFixedSize(true)
        recyclerView?.setLayoutManager(LinearLayoutManager(context))
        recyclerView?.setAdapter(adapter)
        fecha?.setText(pedido?.getDate())
        total?.setText(String.format("%.2f €", pedido?.getTotal()))
        listener = (activity as MainActivity?)?.db?.getReference("Pedidos")?.child(Integer.toString(pedido?.getId_pedido()!!))?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val fb_pedido = dataSnapshot.getValue(Pedido::class.java)
                status?.setProgress(fb_pedido?.getStatus()!!)
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as MainActivity?)?.ref?.removeEventListener(listener!!)
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1: String? = "param1"
        private val ARG_PARAM2: String? = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PedidoDetalle.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String?, param2: String?): PedidoDetalle? {
            val fragment = PedidoDetalle()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}