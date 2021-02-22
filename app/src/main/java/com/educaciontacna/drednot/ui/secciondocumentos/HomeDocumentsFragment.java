/*
 * Copyright (c) 2020. rogergcc
 */

package com.educaciontacna.drednot.ui.secciondocumentos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.educaciontacna.drednot.databinding.FragmentHomeDocumentsBinding;
import com.educaciontacna.drednot.ui.helpers.FirebaseManager;
import com.educaciontacna.drednot.ui.listeners.IDocumentListener;
import com.educaciontacna.drednot.ui.model.CourseCard;
import com.educaciontacna.drednot.ui.model.DocumentModel;
import com.educaciontacna.drednot.ui.utils.MyConstants;
import com.educaciontacna.drednot.ui.utils.MyUtilsApp;
import com.educaciontacna.drednot.ui.utils.RandomString;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeDocumentsFragment extends Fragment implements IDocumentListener {

    private final FirebaseFirestore firebaseFirestoreDB = new FirebaseManager().db;
    FragmentHomeDocumentsBinding binding;
    private Context mcontext;
    private ArrayList<DocumentModel> documentModelArrayList;
    private DocumentosHomeAdapter adapter;
    private static final String TAG = "HomeDocumentsFragment";
    public HomeDocumentsFragment() {
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
//        return inflater.inflate(R.layout.fragment_home_documents, container, false);
        binding = FragmentHomeDocumentsBinding.inflate(getLayoutInflater());
        mcontext = this.getContext();
        View view = binding.getRoot();


        binding.rvListDocuments.setLayoutManager(
                new LinearLayoutManager(mcontext, RecyclerView.VERTICAL, false)
        );
        binding.rvListDocuments.setClipToPadding(false);
        binding.rvListDocuments.setHasFixedSize(true);

        documentModelArrayList = new ArrayList<>();

        documentModelArrayList.add(new DocumentModel(1, 0, "Documento 1", "Ann Caceres", "1 Feb 2021"));
        documentModelArrayList.add(new DocumentModel(2, 1, "Documento 2", "Lugi Br", "14 Mar 2021"));
        documentModelArrayList.add(new DocumentModel(3, 1, "Documento 3", "Mario Cq", "12 Ene 2021"));
        documentModelArrayList.add(new DocumentModel(4, 1, "Documento 4wr", "Ing. lucas", "12 Abr 2021"));
        documentModelArrayList.add(new DocumentModel(5, 0, "Documento 554g", "Ann Adf", "1 May 2021"));
        documentModelArrayList.add(new DocumentModel(6, 0, "Documento 442ds", "Ann", "1 Jul 2021"));

        adapter = new DocumentosHomeAdapter(mcontext, documentModelArrayList, this);

//        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.card_margin);
//        binding.rvListDocuments.addItemDecoration(new SpacesItemDecoration(spacingInPixels));

//        MyUtilsApp.showDialogTitleMessage(mcontext,"Bienvenido a DredNot","Active su ubicacion para continuar");
        binding.rvListDocuments.setAdapter(adapter);

//        generatDummyDocs();
//        generatDummyDocs();
        getDocuments();
        binding.tvFechaHoy.setText(MyUtilsApp.getDateDMY());
        return view;

    }

    @Override
    public void onDashboardCourseClick(CourseCard courseCard, ImageView imageView) {

    }
    public void generatDummyDocs(){

        Map<String, Object> document = new HashMap<>();
        String codGenerated = RandomString.generate(4);
        String codGenerated2 = RandomString.generate(3);

        document.put("nombre", "document "+codGenerated);
        document.put("createdAt", new Timestamp(new Date()));
        document.put("encargado", "username_"+RandomString.generate(4));
        document.put("userCode", "USER_"+codGenerated2);
        document.put("estado", "Pendiente");
//        document.put("fechaAsignado", "phone");

        firebaseFirestoreDB.collection(MyConstants.DOCUMENTS_COLLECTION_FIRE)
                .add(document)

                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        MyUtilsApp.showLogError(TAG,"documento creado");
                    }
                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        MyUtilsApp.showLogError(TAG,"algo ocurrio");
                    }
                });
    }

    public void getDocuments() {
//        db.collection("****")
//                .where('display', '==', true)
//                .where('createdAt', '>', beginningDateObject)
//                .get()
//                .then(function(querySnapshot) {console.log(/* ... */)});

        Date firstDay = new Date();
        Timestamp timestamp1 = Timestamp.now();
        MyUtilsApp.showLogError("HomeDocumentsFragment", "timestamp1."+timestamp1);
        firebaseFirestoreDB.collection(MyConstants.DOCUMENTS_COLLECTION_FIRE)
                .whereEqualTo("fecha",MyUtilsApp.getDateDMY() )

                .get()

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        MyUtilsApp.showLogError("HomeDocumentsFragment", "Failure  getting documents.");
                    }
                })
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<DocumentModel> documentModelList = new ArrayList<>();

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Date creationDate = document.getDate("createdAt");

//                                documentModelList.add(new DocumentModel(
//                                        Integer.parseInt(document.getId()),
//
//                                ));
                                MyUtilsApp.showLogError("HomeDocumentsFragment", document.get("createdAt").toString());
                                        MyUtilsApp.showLogError("HomeDocumentsFragment", document.getId() + " => " + document.getData().toString());
                            }
                        } else {
//                            Log.w(TAG, "Error getting documents.", task.getException());
                            MyUtilsApp.showLogError("HomeDocumentsFragment", "Error getting documents.");
                        }
                    }
                });

    }

    @Override
    public void ondHomeDocumentClick(DocumentModel documentModel) {
        Toast.makeText(mcontext, "Documento: " + documentModel.getDocumentName(), Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(mcontext, DetalleDocumentoActivity.class);
        intent.putExtra(MyUtilsApp.EXTRA_DOCUMENTO, documentModel);
//        intent.putExtra(EXTRA_POSITION, position);


        startActivity(intent);
    }
}