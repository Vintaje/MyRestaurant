package com.emiliorg.myrestaurant.ui.login;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.emiliorg.myrestaurant.MainActivity;
import com.emiliorg.myrestaurant.R;
import com.emiliorg.myrestaurant.StartActivity;
import com.emiliorg.myrestaurant.ui.registro.RegistroFragment;
import com.emiliorg.myrestaurant.utils.netutils;

import java.util.ArrayList;
import java.util.List;

import static android.os.Build.VERSION.SDK_INT;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Button btnEntrar;
    public Button btnRegistrar;

    //DatosUsuario
    private EditText username;
    private EditText password;

    public View root;

    private Button btnMapa;



    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_login, container, false);


        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setButtonAndFields();

        if(!netutils.hayConexion(getActivity())){
            Toast.makeText(getContext(), "No hay conexion", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Setting up fields and button click listeners
     */
    private void setButtonAndFields(){
        btnMapa = root.findViewById(R.id.btnMapa);
        btnMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("geo:38.69239899895243, -4.1092743071446804");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
            }
        });
        btnEntrar = (Button)getView().findViewById(R.id.btnEntrarLogin);
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(filledFields()){
                    firebaseLogin();
                }
            }
        });

        btnRegistrar = (Button) getView().findViewById(R.id.btnRegistroLogin);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(
                                R.anim.slide_in, //enter
                                R.anim.fade_out, //exit
                                R.anim.fade_in, //popEnter
                                R.anim.slide_out //popExit
                        )
                        .replace(R.id.fragment_container_login, RegistroFragment.class, null)
                        .addToBackStack(null)
                        .commit();
            }
        });

        username = root.findViewById(R.id.etUsuarioLogin);
        password = root.findViewById(R.id.etPassLogin);
    }

    public void firebaseLogin(){
        ((StartActivity) getActivity()).auth.signInWithEmailAndPassword(username.getText().toString(), password.getText().toString())
                .addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                loginComplete();
            }
        });
    }

    public boolean filledFields(){
        boolean result = false;
        if(!username.getText().toString().isEmpty() && !password.getText().toString().isEmpty()){
            result = true;
        }
        return result;
    }


    public void loginComplete(){

        Intent intent = new Intent(getContext(), MainActivity.class);
        Bundle userdata = new Bundle();
        userdata.putString("username", username.getText().toString());
        intent.putExtras(userdata);
        startActivity(intent);

    }
}