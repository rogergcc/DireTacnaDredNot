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
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.educaciontacna.drednot.data.model.CourseCard;
import com.educaciontacna.drednot.data.model.DocumentModel;
import com.educaciontacna.drednot.databinding.FragmentHomeDocumentsBinding;
import com.educaciontacna.drednot.ui.helpers.FirebaseManager;
import com.educaciontacna.drednot.ui.listeners.IDocumentListener;
import com.educaciontacna.drednot.ui.utils.ListUtils;
import com.educaciontacna.drednot.ui.utils.MyConstants;
import com.educaciontacna.drednot.ui.utils.MyUtilsApp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeDocumentsFragment extends Fragment implements IDocumentListener {

    private static final String TAG = "HomeDocumentsFragment";
    private final FirebaseFirestore firebaseFirestoreDB = new FirebaseManager().db;
    FragmentHomeDocumentsBinding binding;
    private Context mcontext;
    private DocumentosHomeAdapter adapter;
    private DocumentListViewModel documentListViewModel;

    public HomeDocumentsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
        observeDocumentViewModel();

        observeLiveData();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }


    public void observeLiveData() {
        documentListViewModel
                .getLiveDataTotal()
                .observe(getViewLifecycleOwner(), new Observer<List<DocumentModel>>() {
                    @Override
                    public void onChanged(@Nullable List<DocumentModel> documentList) {

                        List<DocumentModel> documentosFiltrado = new ArrayList<>(documentList);

                        ListUtils.removeDocumentos(car -> {
//                        return car.getEstado() == Color.BLUE;
                            return car.getEstado().equals(MyConstants.ESTADO_PENDIENTE);
                        }, documentosFiltrado);

//                        displayTextView.setText(integer.toString());
                        String tNotificado = "Notificados: " + documentosFiltrado.size();
                        String tPendiente = "Pendientes: " + (documentList.size() - documentosFiltrado.size());
                        String tTotal = "Total : " + documentList.size();

                        binding.tvNotificacion.setText(tNotificado);
                        binding.tvPendidente.setText(tPendiente);
                        binding.tvTotal.setText(tTotal);
                    }
                });
    }

    private void observeDocumentViewModel() {
//        documentListViewModel = new ViewModelProvider(getActivity()).get(DocumentListViewModel.class);
        documentListViewModel = new ViewModelProvider(this).get(DocumentListViewModel.class);

        documentListViewModel.getDocumentsModelData().observe(getViewLifecycleOwner(), new Observer<List<DocumentModel>>() {
            @Override
            public void onChanged(List<DocumentModel> documentModelList) {
                //Load RecyclerView
                int cantTotal = documentModelList.size();
                int cantFilPendiente = 0;
                int cantFilNotificado = 0;

                MyUtilsApp.showLog(TAG, "Size: " + documentModelList.size());
                //listView.startAnimation(fadeInAnim);
                //listProgress.startAnimation(fadeOutAnim);
                List<DocumentModel> documentosToday = new ArrayList<>(documentModelList);

                ListUtils.removeDocumentos(car -> {
//                        return car.getEstado() == Color.BLUE;
                    return !car.getFecha().equals(MyUtilsApp.getTodayDateDMY());
                }, documentosToday);


                cantFilNotificado = documentosToday.size();
                cantFilPendiente = cantTotal - cantFilNotificado;

//                binding.tvNotificacion.setText(cantFilNotificado);
//                binding.tvPendidente.setText(cantFilPendiente);

                adapter.setDocumentsData(documentosToday);

                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_home_documents, container, false);
        binding = FragmentHomeDocumentsBinding.inflate(getLayoutInflater());
        mcontext = this.getContext();
//        View view = binding.getRoot();

        //initView();

//        observeDocumentViewModel();

        return binding.getRoot();
    }

    public void initView() {

        binding.rvListDocuments.setLayoutManager(
                new LinearLayoutManager(mcontext, RecyclerView.VERTICAL, false)
        );

        binding.rvListDocuments.setClipToPadding(false);
        binding.rvListDocuments.setHasFixedSize(true);

        adapter = new DocumentosHomeAdapter(mcontext, this);

//        MyUtilsApp.showDialogTitleMessage(mcontext,"Bienvenido a DredNot","Active su ubicacion para continuar");
        binding.rvListDocuments.setAdapter(adapter);

        //getDocuments();

        binding.tvFechaHoy.setText(MyUtilsApp.getTodayDateDMY());
        binding.tvNotificacion.setText("Notificados: -");
        binding.tvPendidente.setText("Pendientes: -");
        binding.tvTotal.setText("Total: -");
    }

    @Override
    public void onDashboardCourseClick(CourseCard courseCard, ImageView imageView) {

    }


    public void getDocuments() {
//        db.collection("****")
//                .where('display', '==', true)
//                .where('createdAt', '>', beginningDateObject)
//                .get()
//                .then(function(querySnapshot) {console.log(/* ... */)});

        Date firstDay = new Date();
        Timestamp timestamp1 = Timestamp.now();
        MyUtilsApp.showLogError("HomeDocumentsFragment", "timestamp1." + timestamp1);
        firebaseFirestoreDB.collection(MyConstants.DOCUMENTS_COLLECTION_FIRE)
                .whereEqualTo("fecha", MyUtilsApp.getTodayDateDMY())

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