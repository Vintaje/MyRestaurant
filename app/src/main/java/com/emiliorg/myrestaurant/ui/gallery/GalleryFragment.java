package com.emiliorg.myrestaurant.ui.gallery;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.emiliorg.myrestaurant.StartActivity;
import com.emiliorg.myrestaurant.model.Plato;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GalleryFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<Plato> platos;
    private CartaAdapter adapter;
    private View root;
    private Fragment thisFragment;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_gallery, container, false);

        thisFragment = this;
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = root.findViewById(R.id.carta_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        platos = new ArrayList<>();

        ((MainActivity)getActivity()).db.getReference("Platos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Plato plato = snapshot.getValue(Plato.class);
                    platos.add(plato);
                    Log.d("PLATOS", plato.toString());
                }
                adapter = new CartaAdapter(platos, root.getContext(), thisFragment);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void platoDetalle(Fragment platoDetalle){
        getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(
                R.anim.slide_in, //enter
                R.anim.fade_out, //exit
                R.anim.fade_in, //popEnter
                R.anim.slide_out //popExit
        )
                .replace(R.id.nav_host_fragment, platoDetalle, null)
                .addToBackStack(null)
                .commit();
    }

}

