package com.emiliorg.myrestaurant.ui.login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.emiliorg.myrestaurant.MainActivity
import com.emiliorg.myrestaurant.R
import com.emiliorg.myrestaurant.StartActivity
import com.emiliorg.myrestaurant.ui.registro.RegistroFragment
import com.emiliorg.myrestaurant.utils.netutils
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null
    var btnEntrar: Button? = null
    var btnRegistrar: Button? = null

    //DatosUsuario
    private var username: EditText? = null
    private var password: EditText? = null
    var root: View? = null
    private var btnMapa: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments?.getString(ARG_PARAM1)
            mParam2 = arguments?.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

            root = inflater.inflate(R.layout.fragment_login, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setButtonAndFields()
        if (!netutils.hayConexion(activity)) {
            Toast.makeText(context, "No hay conexion", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Setting up fields and button click listeners
     */
    private fun setButtonAndFields() {
        btnMapa = root?.findViewById(R.id.btnMapa)
        (btnMapa as Button).setOnClickListener{
            val gmmIntentUri = Uri.parse("https://www.google.es/maps/place/Kebab+Hut+Comida+Turca+Cafeteria/@38.6921192,-4.1093346,20.25z/data=!4m5!3m4!1s0xd6b8cf6134905d5:0x9bf5cd235cc23d83!8m2!3d38.6919261!4d-4.1091945")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            if (mapIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
                startActivity(mapIntent)
            }
        }
        btnEntrar = view?.findViewById<View?>(R.id.btnEntrarLogin) as Button
        (btnEntrar as Button).setOnClickListener{
            if (filledFields()) {
                firebaseLogin()
            }
        }
        btnRegistrar = view?.findViewById<View?>(R.id.btnRegistroLogin) as Button
        (btnRegistrar as Button).setOnClickListener{

            activity?.getSupportFragmentManager()?.beginTransaction()
                    ?.setCustomAnimations(
                            R.anim.slide_in,  //enter
                            R.anim.fade_out,  //exit
                            R.anim.fade_in,  //popEnter
                            R.anim.slide_out //popExit
                    )
                    ?.replace(R.id.fragment_container_login, RegistroFragment::class.java, null)
                    ?.addToBackStack(null)
                    ?.commit()
        }
        username = root?.findViewById(R.id.etUsuarioLogin)
        password = root?.findViewById(R.id.etPassLogin)
    }

    fun firebaseLogin() {
        (activity as StartActivity?)?.auth?.signInWithEmailAndPassword(username?.text.toString(), password?.text.toString())
                ?.addOnCompleteListener { task: Task<AuthResult?>? ->
                    if (task?.isSuccessful()!!) {
                        loginComplete()
                    }
                }
    }

    fun filledFields(): Boolean {
        var result = false
        if (!username?.text.toString().isEmpty() && !password?.text.toString().isEmpty()) {
            result = true
        }
        return result
    }

    fun loginComplete() {
        val intent = Intent(context, MainActivity::class.java)
        val userdata = Bundle()
        userdata.putString("username", username?.text.toString());
        intent.putExtras(userdata)
        startActivity(intent)
    }

    companion object {
        const val REQUEST_ID_MULTIPLE_PERMISSIONS = 1

        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1: String? = "param1"
        private val ARG_PARAM2: String? = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String?, param2: String?): LoginFragment? {
            val fragment = LoginFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}