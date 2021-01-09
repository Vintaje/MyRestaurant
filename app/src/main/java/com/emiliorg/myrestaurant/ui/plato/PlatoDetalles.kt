package com.emiliorg.myrestaurant.ui.plato

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.emiliorg.myrestaurant.MainActivity
import com.emiliorg.myrestaurant.R
import com.emiliorg.myrestaurant.model.Plato
import com.emiliorg.myrestaurant.ui.gallery.GalleryFragment
import com.squareup.picasso.Picasso

/**
 * A simple [Fragment] subclass.
 * Use the [PlatoDetalles.newInstance] factory method to
 * create an instance of this fragment.
 */
class PlatoDetalles : Fragment {
    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null
    private var platoNombre: TextView? = null
    private var platoFoto: ImageView? = null
    private var platoPrecio: TextView? = null
    private var noVegano: Button? = null
    private var gluten: Button? = null
    private var lactosa: Button? = null
    private var picante: Button? = null
    private var agregarAlPedido: Button? = null
    private var plato: Plato? = null
    var root: View? = null

    constructor() {}
    constructor(plato: Plato?) {
        // Required empty public constructor
        this.plato = plato
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments?.getString(ARG_PARAM1)
            mParam2 = arguments?.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_plato_detalles, container, false)
        initUi()
        loadData()
        return root
    }

    private fun initUi() {
        platoNombre = root?.findViewById(R.id.tvPlatoNombre)
        platoFoto = root?.findViewById(R.id.ivPlatoImage)
        platoPrecio = root?.findViewById(R.id.tvPlatoPrecio)
        noVegano = root?.findViewById(R.id.btnVegano)
        gluten = root?.findViewById(R.id.btnGluten)
        lactosa = root?.findViewById(R.id.btnLactosa)
        picante = root?.findViewById(R.id.btnPicante)
        agregarAlPedido = root?.findViewById(R.id.btnAgregarAlPedido)
        agregarAlPedido?.setOnClickListener(View.OnClickListener {
            (activity as MainActivity?)?.agregarAlPedido(plato)
            activity?.getSupportFragmentManager()?.beginTransaction()?.setCustomAnimations(
                    R.anim.slide_in,  //enter
                    R.anim.fade_out,  //exit
                    R.anim.fade_in,  //popEnter
                    R.anim.slide_out //popExit
            )
                    ?.add(R.id.nav_host_fragment, GalleryFragment(), null)
                    ?.addToBackStack(null)
                    ?.commit()
        })
    }

    private fun loadData() {
        platoNombre?.setText(plato?.getName())
        Picasso.get().load(plato?.getImage()).into(platoFoto)
        platoPrecio?.setText(java.lang.Float.toString(plato?.getPrecio()!!))
        Log.d("PLATOS", plato.toString())
        if ( plato?.getIngredientes()?.isPicante() != true) {
            picante?.setBackgroundColor(root?.getResources()?.getColor(R.color.grey)!!)
        }
        if (plato?.getIngredientes()?.isVegano()!!) {
            noVegano?.setBackgroundColor(root?.getResources()?.getColor(R.color.grey)!!)
        }
        if (plato?.getIngredientes()!!.isGluten() != true) {
            gluten?.setBackgroundColor(root?.getResources()?.getColor(R.color.grey)!!)
        }
        if (!plato?.getIngredientes()!!.isLactosa()) {
            lactosa?.setBackgroundColor(root?.getResources()?.getColor(R.color.grey)!!)
        }
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
         * @return A new instance of fragment PlatoDetalles.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String?, param2: String?): PlatoDetalles? {
            val fragment = PlatoDetalles()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}