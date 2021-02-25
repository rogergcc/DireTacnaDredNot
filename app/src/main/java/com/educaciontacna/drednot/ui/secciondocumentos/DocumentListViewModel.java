package com.educaciontacna.drednot.ui.secciondocumentos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.educaciontacna.drednot.data.model.DocumentModel;

import java.util.ArrayList;
import java.util.List;

public class DocumentListViewModel extends ViewModel implements FirebaseRepository.OnFirestoreTaskComplete {

    private MutableLiveData<List<DocumentModel>> listMutableLiveData = new MutableLiveData<>();

    private FirebaseRepository firebaseRepository = new FirebaseRepository(this);

    //    public LiveData<List<DocumentModel>> getQuizListModelData() {
//        return quizListModelData;
//    }



//    public CustomViewModel(@NonNull Application application) {
//        super(application);
//
//    }

    private MutableLiveData<List<DocumentModel>> liveData =new MutableLiveData<>();
    private MutableLiveData<Integer> liveDataFilter =new MutableLiveData<>();


    public MutableLiveData<List<DocumentModel>> getLiveDataTotal() {

        return liveData;
    }


    public LiveData<List<DocumentModel>> getDocumentsModelData() {
        return listMutableLiveData;
    }

    public DocumentListViewModel() {
//        firebaseRepository.getQuizData();
        firebaseRepository.getDocumentsRepoFireData();

    }

    @Override
    public void quizListDataAdded(List<DocumentModel> quizListModelsList) {
        listMutableLiveData.setValue(quizListModelsList);
        List<Integer> datos = new ArrayList<>();
        datos.clear();
        List<DocumentModel> documentosFiltradosNotificados = new ArrayList<>(quizListModelsList);

        //0 notificado
        //1 pendiente
        //2 total

//        ListUtils.removeDocumentos(document -> {
//            datos.add(quizListModelsList.size());
////                        return car.getEstado() == Color.BLUE;
//            return document.getEstado().equals(MyConstants.ESTADO_PENDIENTE);
//        },documentosFiltradosNotificados);
//
//        int cantTotal = documentosFiltradosNotificados.size();
//        int cantFilPendiente = quizListModelsList.size()-documentosFiltradosNotificados.size();
//        int cantFilNotificado = quizListModelsList.size();
//
//        datos.add(cantFilNotificado);
//        datos.add(cantFilPendiente);
//        datos.add(quizListModelsList.size());

        liveData.setValue(quizListModelsList);
    }

    @Override
    public void onError(Exception e) {

    }
}
