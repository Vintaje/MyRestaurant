package com.emiliorg.myrestaurant.ui.slideshow;

import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.emiliorg.myrestaurant.MainActivity;
import com.emiliorg.myrestaurant.R;
import com.emiliorg.myrestaurant.model.Pedido;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    public View root;

    private RecyclerView recyclerView;
    private ArrayList<Pedido> pedidos;
    private PedidoListAdapter adapter;
    private Fragment thisFragment;

    private ValueEventListener listener;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        pedidos = new ArrayList<>();
        initUI();

        thisFragment = this;
        return root;
    }

    private void initUI() {
        recyclerView = root.findViewById(R.id.rvPedidosList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        loadData();
    }

    private void loadData() {
        pedidos.clear();
        listener = ((MainActivity)getActivity()).db.getReference("Pedidos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Pedido pedido = snapshot.getValue(Pedido.class);

                        if (pedido.getUser_id().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                            pedidos.add(pedido);
                        }
                    }
                    adapter = new PedidoListAdapter(pedidos, getContext(), thisFragment);
                    recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ((MainActivity)getActivity()).ref.removeEventListener(listener);
        onDestroy();
    }
}