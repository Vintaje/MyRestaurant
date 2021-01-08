package com.emiliorg.myrestaurant.ui.Pedido;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.emiliorg.myrestaurant.MainActivity;
import com.emiliorg.myrestaurant.R;
import com.emiliorg.myrestaurant.model.Pedido;
import com.emiliorg.myrestaurant.model.Plato;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class PedidoFragment extends Fragment {

    public Pedido pedido;
    public float totalprecio;
    private TextView total;
    private TextView fecha;
    private RecyclerView recyclerView;
    private Button btnCompletar;

    private View root;

    //Recycler Adapter
    private PedidoAdapter adapter;

    public PedidoFragment(Pedido pedido){
        this.pedido = pedido;
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_pedido, container, false);
        initUI();
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Pedido");

        selectIdPedido();
        return root;
    }

    public void selectIdPedido(){
        ((MainActivity)getActivity()).db.getReference("Pedidos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pedido.setId_pedido((int)dataSnapshot.getChildrenCount()+1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        pedido.setStatus(0);
        pedido.setUser_id(((MainActivity)getActivity()).auth.getUid());

    }

    public void initUI(){
        total = root.findViewById(R.id.tvTotalPedido);
        fecha = root.findViewById(R.id.tvFechaPedido);
        btnCompletar = root.findViewById(R.id.btnCompletarPedido);
        recyclerView = root.findViewById(R.id.recyclerPedido);
        Date currentTime = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat(getString(R.string.datetimeFormat));
        pedido.setDate(dateFormat.format(currentTime));
        fecha.setText(pedido.getDate());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new PedidoAdapter(pedido.getItems(), getContext(), this);

        recyclerView.setAdapter(adapter);
        calcularPrecio();

        btnCompletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pedido.getItems().size() > 0) {
                    completarPedido();
                }else{
                    Toast.makeText(getContext(),"El pedido no puede estar vacio", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void calcularPrecio(){
        totalprecio = 0.00f;
        for (Plato plato:pedido.getItems()) {
            totalprecio += plato.getPrecio();
        }

        total.setText(String.format("%.2f €", totalprecio));
        pedido.setTotal(totalprecio);

    }

    public void completarPedido(){
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        ((MainActivity)getActivity()).db.getReference("Pedidos").child(Integer.toString(pedido.getId_pedido())).setValue(pedido);
                        ((MainActivity)getActivity()).pedido.getItems().clear();
                        ((MainActivity)getActivity()).getSupportFragmentManager().popBackStack();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("¿Quieres completar tu pedido?").setPositiveButton("Si", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }
}
