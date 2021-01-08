package com.emiliorg.myrestaurant.ui.slideshow;

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
import com.emiliorg.myrestaurant.model.Pedido;
import com.emiliorg.myrestaurant.model.Plato;
import com.emiliorg.myrestaurant.ui.pedidoDetalle.PedidoDetalle;
import com.emiliorg.myrestaurant.ui.plato.PlatoDetalles;
import com.emiliorg.myrestaurant.ui.registro.RegistroFragment;
import com.emiliorg.myrestaurant.ui.slideshow.SlideshowFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PedidoListAdapter extends RecyclerView.Adapter<PedidoListAdapter.ViewHolder>{
    private ArrayList<Pedido> list;
    private Context context;
    private Fragment parent;

    public PedidoListAdapter(ArrayList<Pedido> pedido, Context context, Fragment parent) {
        this.list = pedido;
        this.context = context;
        this.parent = parent;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.pedido_item, parent, false);
        PedidoListAdapter.ViewHolder viewHolder = new PedidoListAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Pedido item = list.get(position);
        holder.date.setText(item.getDate());
        holder.price.setText(String.format("%.2f €", item.getTotal()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parent.getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(
                        R.anim.slide_in, //enter
                        R.anim.fade_out, //exit
                        R.anim.fade_in, //popEnter
                        R.anim.slide_out //popExit
                ).replace(R.id.nav_host_fragment, new PedidoDetalle(item)).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView date;
        public TextView price;


        public ViewHolder(@NonNull View itemView){
            super(itemView);
            this.date = itemView.findViewById(R.id.tvPedidoDate);
            this.price = itemView.findViewById(R.id.tvPedidoPrecio);

        }

    }
}
