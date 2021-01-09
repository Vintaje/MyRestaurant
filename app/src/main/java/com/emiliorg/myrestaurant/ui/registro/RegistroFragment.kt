package com.emiliorg.myrestaurant.ui.registro

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.emiliorg.myrestaurant.R
import com.emiliorg.myrestaurant.StartActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

/**
 * A simple [Fragment] subclass.
 * Use the [RegistroFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegistroFragment : Fragment(), View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null
    private var btnVolver: CardView? = null
    private var btnRegistrar: CardView? = null
    private var root: View? = null
    private var usuario: EditText? = null
    private var pass: EditText? = null
    private var email: EditText? = null
    private var passcheck: EditText? = null

    //Check datos
    private var pwd6 //Tiene minimo 6 caracteres
            = false
    private var pwdequals //Tanto el checking como la principal coinciden
            = false
    private var isEmail //Email valido
            = false
    private var nombreok //Nombre ok
            = false
    private var passlayout: TextInputLayout? = null
    private var passchecklayout: TextInputLayout? = null
    private var emaillayout: TextInputLayout? = null
    private var nombrelayout: TextInputLayout? = null
    private val cvRegistrar: CardView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments?.getString(ARG_PARAM1)
            mParam2 = arguments?.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        root = inflater.inflate(R.layout.fragment_registro, container, false)
        // Inflate the layout for this fragment
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUiItems()
    }

    fun setUiItems() {
        btnRegistrar = root?.findViewById(R.id.card_viewRegistrarRegistro)
        btnRegistrar?.setOnClickListener(this)
        btnVolver = root?.findViewById(R.id.card_viewRegistroVolver)
        btnVolver?.setOnClickListener(this)
        usuario = root?.findViewById(R.id.etUsuarioRegistro)
        pass = root?.findViewById(R.id.etPassRegistro)
        email = root?.findViewById(R.id.etEmailRegistro)
        passcheck = root?.findViewById(R.id.etRepPassRegistro)
        nombrelayout = root?.findViewById(R.id.tilUsuarioRegistro)
        passlayout = root?.findViewById(R.id.tilPassRegistro)
        passchecklayout = root?.findViewById(R.id.tilRepPassRegistro)
        emaillayout = root?.findViewById(R.id.tilEmailRegistro)
        btnRegistrar?.setEnabled(false)
        btnRegistrar?.setCardBackgroundColor(resources.getColor(R.color.disabled))
        inputCheck()
    }

    private fun inputCheck() {
        usuario?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val nickname = usuario?.getText().toString()
                if (nickname.length >= 4) {
                    nombreok = true
                    nombrelayout?.setBoxStrokeColor(resources.getColor(R.color.ok))
                    usuario?.setError(null)
                } else {
                    nombreok = false
                    nombrelayout?.setBoxStrokeColor(resources.getColor(R.color.error))
                    usuario?.setError("El nombre debe tener minimo 4 caracteres!")
                }
                if (!nickname.matches("[a-zA-Z0-9]+".toRegex())) {
                    nombreok = false
                    nombrelayout?.setBoxStrokeColor(resources.getColor(R.color.error))
                    usuario?.setError("El nombre solo puede ser alfanumerico")
                }
                checkData()
            }

            override fun afterTextChanged(s: Editable?) {}
        })
        email?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val mail = email?.getText().toString()
                if (mail.matches("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$".toRegex())) {
                    isEmail = true
                    emaillayout?.setBoxStrokeColor(resources.getColor(R.color.ok))
                    email?.setError(null)
                } else {
                    isEmail = false
                    emaillayout?.setBoxStrokeColor(resources.getColor(R.color.error))
                    email?.setError("Debe ser un email valido!")
                }
                checkData()
            }

            override fun afterTextChanged(s: Editable?) {}
        })
        pass?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val pwd = pass?.getText().toString()
                if (pwd.length >= 6) {
                    pwd6 = true
                    passlayout?.setBoxStrokeColor(resources.getColor(R.color.ok))
                    pass?.setError(null)
                } else {
                    pwd6 = false
                    passlayout?.setBoxStrokeColor(resources.getColor(R.color.error))
                    pass?.setError("La contraseña debe tener minimo 6 caracteres")
                }
                if (!pwd.matches("[a-zA-Z0-9]+".toRegex())) {
                    pwd6 = false
                    nombrelayout?.setBoxStrokeColor(resources.getColor(R.color.error))
                    usuario?.setError("La contraseña solo puede ser alfanumerica")
                }
                checkData()
            }

            override fun afterTextChanged(s: Editable?) {}
        })
        passcheck?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val pwd = passcheck?.getText().toString()
                if (pwd == pass?.getText().toString()) {
                    pwdequals = true
                    passchecklayout?.setBoxStrokeColor(resources.getColor(R.color.ok))
                    passcheck?.setError(null)
                } else {
                    pwdequals = false
                    passchecklayout?.setBoxStrokeColor(resources.getColor(R.color.error))
                    passcheck?.setError("Las contraseñas deben ser iguales")
                }
                checkData()
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun checkData() {
        comprobarSiExisteAlias()
        if (nombreok && isEmail && pwd6 && pwdequals) {
            btnRegistrar?.setEnabled(true)
            btnRegistrar?.setCardBackgroundColor(resources.getColor(R.color.red))
        } else {
            btnRegistrar?.setEnabled(false)
            btnRegistrar?.setCardBackgroundColor(resources.getColor(R.color.disabled))
        }
    }

    override fun onClick(v: View?) {
        when (v?.getId()) {
            R.id.card_viewRegistrarRegistro -> {
                registrar()
            }
            R.id.card_viewRegistroVolver -> {
                irALogin()
            }
        }
    }

    private fun comprobarSiExisteAlias() {
        (activity as StartActivity?)?.ref?.child("users")?.child(usuario?.getText().toString())?.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                nombreok = if (dataSnapshot.exists()) {
                    nombrelayout?.setBoxStrokeColor(resources.getColor(R.color.error))
                    usuario?.setError("El usuario ya existe")
                    false
                } else {
                    nombrelayout?.setBoxStrokeColor(resources.getColor(R.color.ok))
                    usuario?.setError(null)
                    true
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    private fun irALogin() {
        activity?.getSupportFragmentManager()?.popBackStack()
    }

    private fun registrar() {
        (activity as StartActivity?)?.auth?.createUserWithEmailAndPassword(email?.getText().toString(), pass?.getText().toString())?.addOnCompleteListener(requireActivity(), OnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("FIREBASE", "USUARIO CREADO CORRECTAMENTE")
                Toast.makeText(context, "Usuario creado correctamente", Toast.LENGTH_SHORT).show()
                (activity as StartActivity?)?.ref?.child("users")?.child(usuario?.getText().toString())?.setValue(true)
                irALogin()
            } else {
                Log.w("FIREBASE", "ERROR AL CREAR USUARIO", task.exception)
                Toast.makeText(context, "Error Inesperado", Toast.LENGTH_SHORT).show()
            }
        })
    }

    companion object {
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
         * @return A new instance of fragment RegistroFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String?, param2: String?): RegistroFragment? {
            val fragment = RegistroFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}