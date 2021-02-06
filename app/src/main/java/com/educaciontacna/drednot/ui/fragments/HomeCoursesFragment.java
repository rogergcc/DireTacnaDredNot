/*
 * Copyright (c) 2020. rogergcc
 */

package com.educaciontacna.drednot.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.educaciontacna.drednot.databinding.FragmentHomeCoursesBinding;
import com.educaciontacna.drednot.ui.adapter.DocumentosHomeAdapter;
import com.educaciontacna.drednot.ui.documentodetalle.GuardarDocumentoActivity;
import com.educaciontacna.drednot.ui.helpers.MyUtilsApp;
import com.educaciontacna.drednot.ui.listeners.IDocumentListener;
import com.educaciontacna.drednot.ui.model.CourseCard;
import com.educaciontacna.drednot.ui.model.DocumentModel;

import java.util.ArrayList;

public class HomeCoursesFragment extends Fragment implements IDocumentListener {

    FragmentHomeCoursesBinding binding;

    private Context mcontext;
    private ArrayList<DocumentModel> documentModelArrayList;
    private DocumentosHomeAdapter adapter;
    public HomeCoursesFragment() {
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
//        return inflater.inflate(R.layout.fragment_home_courses, container, false);
        binding = FragmentHomeCoursesBinding.inflate(getLayoutInflater());
        mcontext = this.getContext();
        View view = binding.getRoot();

        binding.rvListDocuments.setLayoutManager(
                new LinearLayoutManager(mcontext, RecyclerView.VERTICAL,false)
        );
        binding.rvListDocuments.setClipToPadding(false);
        binding.rvListDocuments.setHasFixedSize(true);

        documentModelArrayList = new ArrayList<>();

        documentModelArrayList.add(new DocumentModel(1, 0, "Documento 1","Ann Caceres", "1 Feb 2021"));
        documentModelArrayList.add(new DocumentModel(2, 1, "Documento 2","Lugi Br", "14 Mar 2021"));
        documentModelArrayList.add(new DocumentModel(3, 1, "Documento 3","Mario Cq", "12 Ene 2021"));
        documentModelArrayList.add(new DocumentModel(4, 1, "Documento 4wr","Ing. lucas", "12 Abr 2021"));
        documentModelArrayList.add(new DocumentModel(5, 0, "Documento 554g","Ann Adf", "1 May 2021"));
        documentModelArrayList.add(new DocumentModel(6, 0, "Documento 442ds","Ann", "1 Jul 2021"));

        adapter = new DocumentosHomeAdapter(mcontext, documentModelArrayList, this);

//        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.card_margin);
//        binding.rvListDocuments.addItemDecoration(new SpacesItemDecoration(spacingInPixels));


        binding.rvListDocuments.setAdapter(adapter);
        return view;

    }

    @Override
    public void onDashboardCourseClick(CourseCard courseCard, ImageView imageView) {

    }

    @Override
    public void ondHomeDocumentClick(DocumentModel documentModel) {
        Toast.makeText(mcontext, "Documento: "+documentModel.getDocumentName(), Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(mcontext, GuardarDocumentoActivity.class);
        intent.putExtra(MyUtilsApp.EXTRA_DOCUMENTO,  documentModel);
//        intent.putExtra(EXTRA_POSITION, position);


        startActivity(intent);
    }
}