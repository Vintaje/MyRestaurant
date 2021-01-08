package com.emiliorg.myrestaurant.ui.slideshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.emiliorg.myrestaurant.MainActivity
import com.emiliorg.myrestaurant.R
import com.emiliorg.myrestaurant.model.Pedido
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.util.*

class SlideshowFragment : Fragment() {
    private val slideshowViewModel: SlideshowViewModel? = null
    var root: View? = null
    private var recyclerView: RecyclerView? = null
    private var pedidos: ArrayList<Pedido?>? = null
    private var adapter: PedidoListAdapter? = null
    private var thisFragment: Fragment? = null
    private var listener: ValueEventListener? = null
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        root = inflater.inflate(R.layout.fragment_slideshow, container, false)
        pedidos = ArrayList()
        initUI()
        thisFragment = this
        return root
    }

    private fun initUI() {
        recyclerView = root?.findViewById(R.id.rvPedidosList)
        recyclerView?.setHasFixedSize(true)
        recyclerView?.setLayoutManager(LinearLayoutManager(context))
        loadData()
    }

    private fun loadData() {
        pedidos?.clear()
        listener = (activity as MainActivity?)?.db?.getReference("Pedidos")?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val pedido = snapshot.getValue(Pedido::class.java)
                    if (pedido?.getUser_id() == FirebaseAuth.getInstance().currentUser?.getUid()) {
                        pedidos?.add(pedido)
                    }
                }
                adapter = PedidoListAdapter(pedidos, context, thisFragment)
                recyclerView?.setAdapter(adapter)
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as MainActivity?)?.ref?.removeEventListener(listener!!)
        onDestroy()
    }
}