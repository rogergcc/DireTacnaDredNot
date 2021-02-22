package com.educaciontacna.drednot.ui.seccioncuenta;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.educaciontacna.drednot.databinding.FragmentProfileBinding;
import com.educaciontacna.drednot.ui.LoginActivity;
import com.educaciontacna.drednot.ui.helpers.FirebaseManager;
import com.educaciontacna.drednot.ui.utils.MyConstants;
import com.educaciontacna.drednot.ui.utils.MyUtilsApp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

import static android.content.Context.MODE_PRIVATE;
import static com.educaciontacna.drednot.ui.utils.MyConstants.PREFERENCE_CODE_SESSION;
import static com.educaciontacna.drednot.ui.utils.MyConstants.USER_STATUS_DISCONNECTED;


public class ProfileFragment extends Fragment {


    FragmentProfileBinding binding;
    private Context mcontext;
//    private final FirebaseAuth firebaseAuth = new FirebaseManager().auth;
    private FirebaseAuth mAuth;
    private final FirebaseFirestore firebaseFirestoreDB = new FirebaseManager().db;
    private ProgressDialog progressDialog;

    private SharedPreferences.Editor editor;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(getLayoutInflater());
        mcontext = this.getContext();

        View view = binding.getRoot();

        progressDialog = new ProgressDialog(mcontext);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("obteniendo datos...");
        progressDialog.show();

        mAuth = FirebaseAuth.getInstance();
//        firebaseAuth.getCurrentUser().toString();
        if (mAuth.getCurrentUser()==null){
//            MyUtilsApp.showToast(mcontext,"no datos");
            MyUtilsApp.showLogError("ProfileFragment","No datos");

        }
        FirebaseUser user = mAuth.getCurrentUser();

        SharedPreferences.Editor editor;

        SharedPreferences sharedPreferences = mcontext.getSharedPreferences(PREFERENCE_CODE_SESSION, 0);
        editor = sharedPreferences.edit();

        String username = sharedPreferences.getString("userName","NO USERNAME");

//        MyUtilsApp.showLogError("ProfileFragment  :_",username);
        MyUtilsApp.showLogError("ProfileFragment  CODIGO ",user.getUid());
//        MyUtilsApp.showLogError("ProfileFragment getDisplayName ",user.getDisplayName());
        MyUtilsApp.showLogError("ProfileFragment getEmail ",user.getEmail());

        getUserData(user.getUid());

//        MyUtilsApp.showToast(mcontext,mAuth.getCurrentUser().getDisplayName());

//        return inflater.inflate(R.layout.fragment_profile, container, false);
        binding.btnCerraSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                editor.putInt("isLogged",USER_STATUS_DISCONNECTED);
                editor.apply();
                getActivity().finish();

                Intent intent = new Intent(mcontext, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                mcontext.startActivity(intent);

            }
        });

        binding.btnEditar.setOnClickListener(view1 -> {

            binding.inputName.setEnabled(!binding.inputName.isEnabled());
            binding.inputEmail.setEnabled(!binding.inputEmail.isEnabled());
            binding.inputPassword.setEnabled(!binding.inputPassword.isEnabled());
            String getMessageEditar = binding.btnEditar.getText().toString();
            String getMessaButonEditarCancelar = (getMessageEditar.equals("Editar"))?"Cancelar":"Editar";




            binding.btnEditar.setText(getMessaButonEditarCancelar);
        });

        return view;
    }

    private void signOut(String auth_method) {
        SharedPreferences sharedPreferences = mcontext.getSharedPreferences(PREFERENCE_CODE_SESSION, MODE_PRIVATE);
        editor = sharedPreferences.edit();

            editor.putInt("isLogged",USER_STATUS_DISCONNECTED);
            editor.apply();

    }

    public void getUserData(String documentCode){
        DocumentReference docRef = firebaseFirestoreDB
                .collection(MyConstants.USER_COLLECTION_FIRE)
                .document(documentCode);
        docRef.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (!task.isSuccessful()) {
//                    Log.d(TAG, "get failed with ", task.getException());
                    MyUtilsApp.showLogError("ProfileFragment",task.getException().getMessage());
                    progressDialog.dismiss();
                } else {
                    DocumentSnapshot document = task.getResult();
                    if (!document.exists()) {
                        Date creationDate = document.getDate("date");
                        MyUtilsApp.showLogError("ProfileFragment","fecha: "+creationDate);

//                        Log.d(TAG, "No such document");
                        MyUtilsApp.showLogError("ProfileFragment","no existe registro");
                    } else {
//                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                       //MyUtilsApp.showToast(mcontext,document.getData().toString());

                       binding.inputName.setText(document.get("name").toString());
                       binding.inputEmail.setText(document.get("username").toString());
                        //MyUtilsApp.showLogError("ProfileFragment","DocumentSnapshot data: " + document.getData());
                    }
                    progressDialog.dismiss();
                }
            }
        });
    }
}