/*
 * Copyright (c) 2020. rogergcc
 */

package com.educaciontacna.drednot.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.IgnoreExtraProperties;
import com.google.firebase.firestore.PropertyName;
import com.google.firebase.firestore.ServerTimestamp;

import org.w3c.dom.Document;

import java.util.Date;


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

    @Exclude
    private int Id;

    @Exclude
    private int documentoEstado;

    @DocumentId
    private String documento_id;

    private String estado;

    private String fecha;

    @PropertyName("nombre")
    private String documentName;

    @PropertyName("encargado")
    private String encargadoDocumento;

    @Exclude
    private String documentoFecha;

    @Exclude
    private int radioTipoNotificado;

    @PropertyName("createdAt")
    @ServerTimestamp
    private Date timestamp;

    @Exclude
    private String documentoEstadoTexto;


    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    public String getDocumento_id() {
        return documento_id;
    }

    public void setDocumento_id(String documento_id) {
        this.documento_id = documento_id;
    }

    public DocumentModel(){

    }
    public DocumentModel(int id, int documentoEstado, String documento_id, String estado, String fecha, String documentName, String encargadoDocumento, String documentoFecha, int radioTipoNotificado, Date timestamp, String documentoEstadoTexto) {
        Id = id;
        this.documentoEstado = documentoEstado;
        this.documento_id = documento_id;
        this.estado = estado;
        this.fecha = fecha;
        this.documentName = documentName;
        this.encargadoDocumento = encargadoDocumento;
        this.documentoFecha = documentoFecha;
        this.radioTipoNotificado = radioTipoNotificado;
        this.timestamp = timestamp;
        this.documentoEstadoTexto = documentoEstadoTexto;
    }

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

    public String getDocumentoEstadoTexto() {
        return documentoEstadoTexto;
    }

    public void setDocumentoEstadoTexto(String documentoEstadoTexto) {
        this.documentoEstadoTexto = documentoEstadoTexto;
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

    @PropertyName("nombre")
    public String getDocumentName() {
        return documentName;
    }
    @PropertyName("nombre")
    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }
    @PropertyName("encargado")
    public String getEncargadoDocumento() {
        return encargadoDocumento;
    }

    @PropertyName("encargado")
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @PropertyName("createdAt")
    private Date getTimestamp() {
        return timestamp;
    }

    @PropertyName("createdAt")
    private void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "DocumentModel{" +
                "Id=" + Id +
                ", documentoEstado=" + documentoEstado +
                ", documento_id='" + documento_id + '\'' +
                ", estado='" + estado + '\'' +
                ", fecha='" + fecha + '\'' +
                ", documentName='" + documentName + '\'' +
                ", encargadoDocumento='" + encargadoDocumento + '\'' +
                ", documentoFecha='" + documentoFecha + '\'' +
                ", radioTipoNotificado=" + radioTipoNotificado +
                ", timestamp=" + timestamp +
                ", documentoEstadoTexto='" + documentoEstadoTexto + '\'' +
                '}';
    }
}
