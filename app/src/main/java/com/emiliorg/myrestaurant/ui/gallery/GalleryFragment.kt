package com.emiliorg.myrestaurant.ui.gallery

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.emiliorg.myrestaurant.MainActivity
import com.emiliorg.myrestaurant.R
import com.emiliorg.myrestaurant.model.Plato
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.util.*

class GalleryFragment : Fragment() {
    private var recyclerView: RecyclerView? = null
    private var platos: ArrayList<Plato?>? = null
    private var adapter: CartaAdapter? = null
    private var root: View? = null
    private var thisFragment: Fragment? = null
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        root = inflater.inflate(R.layout.fragment_gallery, container, false)
        thisFragment = this
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = root?.findViewById(R.id.carta_recycler)
        recyclerView?.setHasFixedSize(true)
        recyclerView?.setLayoutManager(LinearLayoutManager(context))
        platos = ArrayList()
        (activity as MainActivity?)?.db?.getReference("Platos")?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val plato = snapshot.getValue(Plato::class.java)
                    platos?.add(plato)
                    Log.d("PLATOS", plato.toString())
                }
                adapter = CartaAdapter(platos, root?.getContext(), thisFragment)
                recyclerView?.setAdapter(adapter)
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    fun platoDetalle(platoDetalle: Fragment?) {
        activity?.getSupportFragmentManager()?.beginTransaction()?.setCustomAnimations(
                R.anim.slide_in,  //enter
                R.anim.fade_out,  //exit
                R.anim.fade_in,  //popEnter
                R.anim.slide_out //popExit
        )
                ?.replace(R.id.nav_host_fragment, platoDetalle!!, null)
                ?.addToBackStack(null)
                ?.commit()
    }
}