package com.emiliorg.myrestaurant.ui.pedidoDetalle;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.emiliorg.myrestaurant.MainActivity;
import com.emiliorg.myrestaurant.R;
import com.emiliorg.myrestaurant.model.Pedido;
import com.emiliorg.myrestaurant.ui.Pedido.PedidoAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PedidoDetalle#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PedidoDetalle extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    View root;

    private TextView fecha;
    private ProgressBar status;
    private TextView total;
    private RecyclerView recyclerView;
    private PedidoAdapter adapter;
    private Pedido pedido;

    private Fragment thisFragment;

    private ValueEventListener listener;



    public PedidoDetalle() {
        // Required empty public constructor
    }

    public PedidoDetalle(Pedido pedido){
        this.pedido = pedido;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PedidoDetalle.
     */
    // TODO: Rename and change types and number of parameters
    public static PedidoDetalle newInstance(String param1, String param2) {
        PedidoDetalle fragment = new PedidoDetalle();
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

        thisFragment = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_pedido_detalle, container, false);
        initUI();
        return root;
    }

    public void initUI(){
        recyclerView = root.findViewById(R.id.recyclerPedidoDetalle);
        fecha = root.findViewById(R.id.tvFechaPedidoDetalle);
        status = root.findViewById(R.id.progressBar);
        total = root.findViewById(R.id.tvTotalPedido);

        adapter = new PedidoAdapter(pedido.getItems(), getContext(), thisFragment);
        adapter.detallePedido = true;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        fecha.setText(pedido.getDate());
        total.setText(String.format("%.2f €", pedido.getTotal()));


        listener = ((MainActivity)getActivity()).db.getReference("Pedidos").child(Integer.toString(pedido.getId_pedido())).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Pedido fb_pedido = dataSnapshot.getValue(Pedido.class);
                status.setProgress(fb_pedido.getStatus());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((MainActivity)getActivity()).ref.removeEventListener(listener);
    }
}