package com.emiliorg.myrestaurant;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Menu;
import android.widget.Toast;

import com.emiliorg.myrestaurant.model.Pedido;
import com.emiliorg.myrestaurant.model.Plato;
import com.emiliorg.myrestaurant.ui.Pedido.PedidoFragment;
import com.emiliorg.myrestaurant.ui.slideshow.SlideshowFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    public DatabaseReference ref;
    public FirebaseDatabase db;
    public FirebaseAuth auth;

    public Pedido pedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        Toast.makeText(this, "Logeado correctamente", Toast.LENGTH_SHORT).show();
        db = FirebaseDatabase.getInstance(ConstantsMR.FIREBASE_DB);
        ref = db.getReference();
        //cargarPlatos();
        auth = FirebaseAuth.getInstance();
        pedido = new Pedido();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.btnCarro:
                getSupportFragmentManager().beginTransaction().setCustomAnimations(
                        R.anim.slide_in, //enter
                        R.anim.fade_out, //exit
                        R.anim.fade_in, //popEnter
                        R.anim.slide_out //popExit
                ).replace(R.id.nav_host_fragment, new PedidoFragment(pedido)).addToBackStack(null).commit();
        }
        return false;
    }


    public void agregarAlPedido(Plato plato){
        pedido.addPlato(plato);
        Log.d("PEDIDO", pedido.toString());
        Toast.makeText(this, "Agregado al Pedido", Toast.LENGTH_SHORT).show();

    }

}