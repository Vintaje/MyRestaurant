package com.emiliorg.myrestaurant.ui.gallery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CartaAdapter extends RecyclerView.Adapter<CartaAdapter.ViewHolder>{
    private ArrayList<Plato> list;
    private Context context;
    private Fragment parent;

    public CartaAdapter(ArrayList<Plato> list, Context context, Fragment parent) {
        this.list = list;
        this.context = context;
        this.parent = parent;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.item_carta_list, parent, false);
        CartaAdapter.ViewHolder viewHolder = new CartaAdapter.ViewHolder(listItem);
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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment platoDetalle = new PlatoDetalles(item);
                ((GalleryFragment) parent).platoDetalle(platoDetalle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public CircleImageView photo;
        public TextView nombre;
        public TextView price;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            this.photo = itemView.findViewById(R.id.platoPhoto);
            this.nombre = itemView.findViewById(R.id.platoName);
            this.price = itemView.findViewById(R.id.platoPrice);
        }

    }
}
