package com.emiliorg.myrestaurant

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.emiliorg.myrestaurant.model.Pedido
import com.emiliorg.myrestaurant.model.Plato
import com.emiliorg.myrestaurant.ui.Pedido.PedidoFragment
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    private var mAppBarConfiguration: AppBarConfiguration? = null
    var ref: DatabaseReference? = null
    var db: FirebaseDatabase? = null
    var auth: FirebaseAuth? = null
    var pedido: Pedido? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar?>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val drawer = findViewById<DrawerLayout?>(R.id.drawer_layout)
        val navigationView = findViewById<NavigationView?>(R.id.nav_view)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build()
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration!!)
        NavigationUI.setupWithNavController(navigationView, navController)
        Toast.makeText(this, "Logeado correctamente", Toast.LENGTH_SHORT).show()
        db = FirebaseDatabase.getInstance(ConstantsMR.FIREBASE_DB.toString())
        ref = db?.getReference()
        //cargarPlatos();
        auth = FirebaseAuth.getInstance()
        pedido = Pedido()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        return (NavigationUI.navigateUp(navController, mAppBarConfiguration!!)
                || super.onSupportNavigateUp())
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item!!.getItemId()) {
            R.id.btnCarro -> supportFragmentManager.beginTransaction().setCustomAnimations(
                    R.anim.slide_in,  //enter
                    R.anim.fade_out,  //exit
                    R.anim.fade_in,  //popEnter
                    R.anim.slide_out //popExit
            ).replace(R.id.nav_host_fragment, PedidoFragment(pedido)).addToBackStack(null).commit()
        }
        return false
    }

    fun agregarAlPedido(plato: Plato?) {
        pedido?.addPlato(plato)
        Log.d("PEDIDO", pedido.toString())
        Toast.makeText(this, "Agregado al Pedido", Toast.LENGTH_SHORT).show()
    }
}