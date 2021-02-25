package com.educaciontacna.drednot.ui.utils;

import androidx.annotation.NonNull;

import com.educaciontacna.drednot.data.model.DocumentModel;
import com.educaciontacna.drednot.ui.listeners.MyFilter;

import java.util.Iterator;
import java.util.List;

/**
 * Created by rogergcc on 22/02/2021.
 * Copyright Ⓒ 2021 . All rights reserved.
 */

public class ListUtils {
    public interface Filter<T>{
        boolean keepItem(T item);
    }

    public static <T> void filter(@NonNull List<T> items, @NonNull Filter<T> filter) {
        for (Iterator<T> iterator = items.iterator(); iterator.hasNext();){
            if(!filter.keepItem(iterator.next())){
                iterator.remove();
            }
        }
    }

    public static int removeDocumentoBy(MyFilter<DocumentModel> filter, List<DocumentModel> lista) {
        int cantidad =0;
        Iterator<DocumentModel> carsIterator = lista.iterator();
        while (carsIterator.hasNext()) {
            DocumentModel c = carsIterator.next();
            if (filter.shouldRemove(c)) {
                carsIterator.remove();
            }
        }
        return 0;
    }

    public static void removeDocumentos(MyFilter<DocumentModel> filter, List<DocumentModel> lista) {
        Iterator<DocumentModel> carsIterator = lista.iterator();
        while (carsIterator.hasNext()) {
            DocumentModel c = carsIterator.next();
            if (filter.shouldRemove(c)) {
                carsIterator.remove();
            }
        }
    }
}
