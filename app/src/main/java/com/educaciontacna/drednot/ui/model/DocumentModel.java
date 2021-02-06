/*
 * Copyright (c) 2020. rogergcc
 */

package com.educaciontacna.drednot.ui.model;

import android.os.Parcel;
import android.os.Parcelable;

public class DocumentModel implements Parcelable {

    public static final Creator<DocumentModel> CREATOR = new Creator<DocumentModel>() {
        @Override
        public DocumentModel createFromParcel(Parcel in) {
            return new DocumentModel(in);
        }

        @Override
        public DocumentModel[] newArray(int size) {
            return new DocumentModel[size];
        }
    };
    private int Id;
    private int documentoEstado;
    private String documentName;
    private String encargadoDocumento;
    private String documentoFecha;
    private int radioTipoNotificado;

    public String getDocumentoEstadoTexto() {
        return documentoEstadoTexto;
    }

    public void setDocumentoEstadoTexto(String documentoEstadoTexto) {
        this.documentoEstadoTexto = documentoEstadoTexto;
    }

    private String documentoEstadoTexto;

    public DocumentModel(int id, int documentoEstado, String documentName, String encargadoDocumento, String documentoFecha) {
        Id = id;
        this.documentoEstado = documentoEstado;
        this.documentName = documentName;
        this.encargadoDocumento = encargadoDocumento;
        this.documentoFecha = documentoFecha;
    }

    public DocumentModel(int documentoEstado, String documentName, String encargadoDocumento) {
        this.documentoEstado = documentoEstado;
        this.documentName = documentName;
        this.encargadoDocumento = encargadoDocumento;
    }

    protected DocumentModel(Parcel in) {
        Id = in.readInt();
        documentoEstado = in.readInt();
        documentName = in.readString();
        encargadoDocumento = in.readString();
        documentoFecha = in.readString();
    }

    public int getRadioTipoNotificado() {
        return radioTipoNotificado;
    }

    public void setRadioTipoNotificado(int radioTipoNotificado) {
        this.radioTipoNotificado = radioTipoNotificado;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getDocumentoEstado() {
        return documentoEstado;
    }

    public void setDocumentoEstado(int documentoEstado) {
        this.documentoEstado = documentoEstado;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getEncargadoDocumento() {
        return encargadoDocumento;
    }

    public void setEncargadoDocumento(String encargadoDocumento) {
        this.encargadoDocumento = encargadoDocumento;
    }

    public String getDocumentoFecha() {
        return documentoFecha;
    }

    public void setDocumentoFecha(String documentoFecha) {
        this.documentoFecha = documentoFecha;
    }

    @Override()
    public boolean equals(Object other) {
        // This is unavoidable, since equals() must accept an Object and not something more derived
        if (other instanceof DocumentModel) {
            // Note that I use equals() here too, otherwise, again, we will check for referential equality.
            // Using equals() here allows the Model class to implement it's own version of equality, rather than
            // us always checking for referential equality.
            DocumentModel courseCard = (DocumentModel) other;
            return courseCard.getId() == (this.getId());
        }

        return false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(Id);
        parcel.writeInt(documentoEstado);
        parcel.writeString(documentName);
        parcel.writeString(encargadoDocumento);
        parcel.writeString(documentoFecha);
    }
}
