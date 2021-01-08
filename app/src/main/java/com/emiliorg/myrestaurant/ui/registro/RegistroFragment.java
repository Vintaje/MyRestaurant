package com.emiliorg.myrestaurant.ui.registro;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.emiliorg.myrestaurant.R;
import com.emiliorg.myrestaurant.StartActivity;
import com.emiliorg.myrestaurant.ui.login.LoginFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.Executor;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegistroFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistroFragment extends Fragment implements View.OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private CardView btnVolver;
    private CardView btnRegistrar;

    private View root;
    private EditText usuario;
    private EditText pass;
    private EditText email;
    private EditText passcheck;


    //Check datos
    private boolean pwd6; //Tiene minimo 6 caracteres
    private boolean pwdequals; //Tanto el checking como la principal coinciden
    private boolean isEmail; //Email valido
    private boolean nombreok; //Nombre ok
    private TextInputLayout passlayout;
    private TextInputLayout passchecklayout;
    private TextInputLayout emaillayout;
    private TextInputLayout nombrelayout;
    private CardView cvRegistrar;



    public RegistroFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegistroFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegistroFragment newInstance(String param1, String param2) {
        RegistroFragment fragment = new RegistroFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_registro, container, false);
        // Inflate the layout for this fragment
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUiItems();

    }

    public void setUiItems(){
        btnRegistrar = getView().findViewById(R.id.card_viewRegistrarRegistro);
        btnRegistrar.setOnClickListener(this);

        btnVolver = getView().findViewById(R.id.card_viewRegistroVolver);
        btnVolver.setOnClickListener(this);

        usuario = root.findViewById(R.id.etUsuarioRegistro);
        pass = root.findViewById(R.id.etPassRegistro);
        email = root.findViewById(R.id.etEmailRegistro);
        passcheck = root.findViewById(R.id.etRepPassRegistro);

        nombrelayout = root.findViewById(R.id.tilUsuarioRegistro);
        passlayout = root.findViewById(R.id.tilPassRegistro);
        passchecklayout = root.findViewById(R.id.tilRepPassRegistro);
        emaillayout = root.findViewById(R.id.tilEmailRegistro);

        btnRegistrar.setEnabled(false);
        btnRegistrar.setCardBackgroundColor(getResources().getColor(R.color.disabled));
        inputCheck();
    }

    private void inputCheck() {
        usuario.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String nickname = usuario.getText().toString();

                if (nickname.length() >= 4) {
                    nombreok = true;
                    nombrelayout.setBoxStrokeColor(getResources().getColor(R.color.ok));
                    usuario.setError(null);
                } else {
                    nombreok = false;
                    nombrelayout.setBoxStrokeColor(getResources().getColor(R.color.error));
                    usuario.setError("El nombre debe tener minimo 4 caracteres!");
                }
                if(!nickname.matches("[a-zA-Z0-9]+")){
                    nombreok = false;
                    nombrelayout.setBoxStrokeColor(getResources().getColor(R.color.error));
                    usuario.setError("El nombre solo puede ser alfanumerico");
                }
                checkData();
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String mail = email.getText().toString();
                if (mail.matches("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")) {
                    isEmail = true;
                    emaillayout.setBoxStrokeColor(getResources().getColor(R.color.ok));
                    email.setError(null);
                } else {
                    isEmail = false;
                    emaillayout.setBoxStrokeColor(getResources().getColor(R.color.error));
                    email.setError("Debe ser un email valido!");
                }
                checkData();
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String pwd = pass.getText().toString();

                if (pwd.length() >= 6) {
                    pwd6 = true;
                    passlayout.setBoxStrokeColor(getResources().getColor(R.color.ok));
                    pass.setError(null);
                } else {
                    pwd6 = false;
                    passlayout.setBoxStrokeColor(getResources().getColor(R.color.error));
                    pass.setError("La contraseña debe tener minimo 6 caracteres");
                }
                if(!pwd.matches("[a-zA-Z0-9]+")){
                    pwd6 = false;
                    nombrelayout.setBoxStrokeColor(getResources().getColor(R.color.error));
                    usuario.setError("La contraseña solo puede ser alfanumerica");
                }
                checkData();
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        passcheck.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String pwd = passcheck.getText().toString();

                if (pwd.equals(pass.getText().toString())) {
                    pwdequals = true;
                    passchecklayout.setBoxStrokeColor(getResources().getColor(R.color.ok));
                    passcheck.setError(null);
                } else {
                    pwdequals = false;

                    passchecklayout.setBoxStrokeColor(getResources().getColor(R.color.error));
                    passcheck.setError("Las contraseñas deben ser iguales");
                }

                checkData();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    private void checkData() {
        comprobarSiExisteAlias();
        if (nombreok && isEmail && pwd6 && pwdequals) {

            btnRegistrar.setEnabled(true);
            btnRegistrar.setCardBackgroundColor(getResources().getColor(R.color.red));
        } else {
            btnRegistrar.setEnabled(false);
            btnRegistrar.setCardBackgroundColor(getResources().getColor(R.color.disabled));
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.card_viewRegistrarRegistro: {
                registrar();
                break;
            }
            case R.id.card_viewRegistroVolver: {
                irALogin();
                break;
            }
        }
    }

    private void comprobarSiExisteAlias() {
        ((StartActivity)getActivity()).ref.child("users").child(usuario.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    nombrelayout.setBoxStrokeColor(getResources().getColor(R.color.error));
                    usuario.setError("El usuario ya existe");
                    nombreok = false;
                }else{
                    nombrelayout.setBoxStrokeColor(getResources().getColor(R.color.ok));
                    usuario.setError(null);
                    nombreok = true;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void irALogin() {
        getActivity().getSupportFragmentManager().popBackStack();
    }

    private void registrar(){
        ((StartActivity)getActivity()).auth.createUserWithEmailAndPassword(email.getText().toString(), pass.getText().toString()).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Log.d("FIREBASE", "USUARIO CREADO CORRECTAMENTE");
                    Toast.makeText(getContext(),"Usuario creado correctamente",Toast.LENGTH_SHORT).show();
                    ((StartActivity)getActivity()).ref.child("users").child(usuario.getText().toString()).setValue(true);
                    irALogin();
                }else{
                    Log.w("FIREBASE","ERROR AL CREAR USUARIO", task.getException());
                    Toast.makeText(getContext(),"Error Inesperado",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}