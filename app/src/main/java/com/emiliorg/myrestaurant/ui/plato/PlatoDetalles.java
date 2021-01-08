package com.emiliorg.myrestaurant.ui.plato;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.emiliorg.myrestaurant.MainActivity;
import com.emiliorg.myrestaurant.R;
import com.emiliorg.myrestaurant.model.Plato;
import com.emiliorg.myrestaurant.ui.gallery.GalleryFragment;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlatoDetalles#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlatoDetalles extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView platoNombre;
    private ImageView platoFoto;
    private TextView platoPrecio;
    private Button noVegano;
    private Button gluten;
    private Button lactosa;
    private Button picante;

    private Button agregarAlPedido;


    
    private Plato plato;

    public View root;

    public PlatoDetalles(){}

    public PlatoDetalles(Plato plato) {
        // Required empty public constructor
        this.plato = plato;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlatoDetalles.
     */
    // TODO: Rename and change types and number of parameters
    public static PlatoDetalles newInstance(String param1, String param2) {
        PlatoDetalles fragment = new PlatoDetalles();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_plato_detalles, container, false);
        initUi();
        loadData();
        return root;
    }


    private void initUi(){
        platoNombre = root.findViewById(R.id.tvPlatoNombre);
        platoFoto = root.findViewById(R.id.ivPlatoImage);
        platoPrecio = root.findViewById(R.id.tvPlatoPrecio);
        noVegano = root.findViewById(R.id.btnVegano);
        gluten = root.findViewById(R.id.btnGluten);
        lactosa = root.findViewById(R.id.btnLactosa);
        picante = root.findViewById(R.id.btnPicante);
        agregarAlPedido = root.findViewById(R.id.btnAgregarAlPedido);

        agregarAlPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).agregarAlPedido(plato);
                getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(
                        R.anim.slide_in, //enter
                        R.anim.fade_out, //exit
                        R.anim.fade_in, //popEnter
                        R.anim.slide_out //popExit
                )
                        .add(R.id.nav_host_fragment, new GalleryFragment(), null)
                        .addToBackStack(null)
                        .commit();

            }
        });
    }

    private void loadData(){
        platoNombre.setText(plato.getName());
        Picasso.get().load(plato.getImage()).into(platoFoto);
        platoPrecio.setText(Float.toString(plato.getPrecio()));
        Log.d("PLATOS", plato.toString());
        if(!plato.getIngredientes().isPicante()){
            picante.setBackgroundColor(root.getResources().getColor(R.color.grey));
        }
        if(plato.getIngredientes().isVegano()){
            noVegano.setBackgroundColor(root.getResources().getColor(R.color.grey));
        }
        if(!plato.getIngredientes().isGluten()){
            gluten.setBackgroundColor(root.getResources().getColor(R.color.grey));
        }
        if(!plato.getIngredientes().isLactosa()){
            lactosa.setBackgroundColor(root.getResources().getColor(R.color.grey));
        }
    }
}