package com.emiliorg.myrestaurant.ui.Pedido;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.emiliorg.myrestaurant.MainActivity;
import com.emiliorg.myrestaurant.R;
import com.emiliorg.myrestaurant.model.Plato;
import com.emiliorg.myrestaurant.ui.plato.PlatoDetalles;
import com.emiliorg.myrestaurant.ui.registro.RegistroFragment;
import com.emiliorg.myrestaurant.ui.slideshow.SlideshowFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PedidoAdapter extends RecyclerView.Adapter<PedidoAdapter.ViewHolder>{
    private ArrayList<Plato> list;
    private Context context;
    private Fragment parent;
    public boolean detallePedido;

    public PedidoAdapter(ArrayList<Plato> list, Context context, Fragment parent) {
        this.list = list;
        this.context = context;
        this.parent = parent;
        detallePedido = false;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.item_pedidolist, parent, false);
        PedidoAdapter.ViewHolder viewHolder = new PedidoAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Plato item = list.get(position);
        holder.nombre.setText(item.getName());
        holder.price.setText(Float.toString(item.getPrecio()));

        try{
            Picasso.get().load(item.getImage()).into(holder.photo);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        if(detallePedido){
            holder.btnBorrar.setVisibility(View.INVISIBLE);
        }

        holder.btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrarItem(position);
            }
        });

    }

    public void borrarItem(int position){
        list.remove(position);
        notifyDataSetChanged();
        ((PedidoFragment)parent).calcularPrecio();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public CircleImageView photo;
        public TextView nombre;
        public TextView price;
        public Button btnBorrar;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            this.photo = itemView.findViewById(R.id.pedidoPhotoPlato);
            this.nombre = itemView.findViewById(R.id.platoPedidoNombre);
            this.price = itemView.findViewById(R.id.platoPedidoPrecio);
            this.btnBorrar = itemView.findViewById(R.id.btnBorrarItemPedido);
        }

    }
}
