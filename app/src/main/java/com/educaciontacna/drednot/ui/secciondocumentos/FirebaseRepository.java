package com.educaciontacna.drednot.ui.secciondocumentos;

import androidx.annotation.NonNull;

import com.educaciontacna.drednot.data.model.DocumentModel;
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
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseRepository {

    private OnFirestoreTaskComplete onFirestoreTaskComplete;

    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private Query quizRef = firebaseFirestore.collection("documents");
//            .whereEqualTo("visibility", "public");
    private static final String TAG = "FirebaseRepository";
    private List<DocumentModel> documentModelArrayList;

    public FirebaseRepository(OnFirestoreTaskComplete onFirestoreTaskComplete) {
        this.onFirestoreTaskComplete = onFirestoreTaskComplete;
    }

    public void getQuizData() {
        quizRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    onFirestoreTaskComplete.quizListDataAdded(task.getResult().toObjects(DocumentModel.class));
                } else {
                    onFirestoreTaskComplete.onError(task.getException());
                }
            }
        });
    }



    public void getDocumentsRepoFireData() {
        Timestamp timestamp1 = Timestamp.now();
        MyUtilsApp.showLogError(TAG, "timestamp1."+timestamp1);
        firebaseFirestore.collection(MyConstants.DOCUMENTS_COLLECTION_FIRE)
//                .whereEqualTo("fecha",MyUtilsApp.getTodayDateDMY() )
                .get()
                .addOnFailureListener(e -> MyUtilsApp.showLogError(TAG, "Failure  getting documents."))
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            if(task.getResult()==null) onFirestoreTaskComplete.quizListDataAdded(null);

                            MyUtilsApp.showLogError(TAG, "GET DATA DOCUMENTS.");
                            MyUtilsApp.showLogError(TAG, "GET TASK SIZE: "+task.getResult().size());
                            onFirestoreTaskComplete.quizListDataAdded(task.getResult().toObjects(DocumentModel.class));

//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Date creationDate = document.getDate("createdAt");
//                                MyUtilsApp.showLogError(TAG, document.getId() + " => " + document.getData().toString());
//                            }
                        } else {
//                            Log.w(TAG, "Error getting documents.", task.getException());
                            MyUtilsApp.showLogError(TAG, "Error getting documents."+task.getException());
                            onFirestoreTaskComplete.onError(task.getException());
                        }
                    }
                });

    }

    public void getDummyDocuments() {
        try {
            documentModelArrayList=new ArrayList<>();
            documentModelArrayList.add(new DocumentModel(1, 0, "Documento 1", "Ann Caceres", "1 Feb 2021"));
            documentModelArrayList.add(new DocumentModel(2, 1, "Documento 2", "Lugi Br", "14 Mar 2021"));
            documentModelArrayList.add(new DocumentModel(3, 1, "Documento 3", "Mario Cq", "12 Ene 2021"));
            documentModelArrayList.add(new DocumentModel(4, 1, "Documento 4wr", "Ing. lucas", "12 Abr 2021"));
            documentModelArrayList.add(new DocumentModel(5, 0, "Documento 554g", "Ann Adf", "1 May 2021"));
            documentModelArrayList.add(new DocumentModel(6, 0, "Documento 442ds", "Ann", "1 Jul 2021"));
            onFirestoreTaskComplete.quizListDataAdded(documentModelArrayList);
        }catch (Exception ex){
            MyUtilsApp.showLogError(TAG, ex.getMessage());
        }
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

        firebaseFirestore.collection(MyConstants.DOCUMENTS_COLLECTION_FIRE)
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

    public interface OnFirestoreTaskComplete {
        void quizListDataAdded(List<DocumentModel> quizListModelsList);

        void onError(Exception e);
    }

}
