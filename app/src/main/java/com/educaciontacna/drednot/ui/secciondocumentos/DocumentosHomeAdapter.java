/*
 * Copyright (c) 2020. rogergcc
 */

package com.educaciontacna.drednot.ui.secciondocumentos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.educaciontacna.drednot.R;
import com.educaciontacna.drednot.databinding.ItemCardDocumentBinding;
import com.educaciontacna.drednot.ui.listeners.IDocumentListener;
import com.educaciontacna.drednot.data.model.DocumentModel;
import com.educaciontacna.drednot.ui.utils.MyConstants;

import java.util.List;

public class DocumentosHomeAdapter extends RecyclerView.Adapter<DocumentosHomeAdapter.MyViewHolder> {


    Context mContext;
    private List<DocumentModel> mData;
    private IDocumentListener IDocumentListener;

//    public DocumentosHomeAdapter(Context mContext, List<DocumentModel> mData, IDocumentListener listener) {
//        this.mContext = mContext;
//        this.mData = mData;
//        this.IDocumentListener = listener;
//    }

    public DocumentosHomeAdapter(Context mContext,  IDocumentListener listener) {
        this.mContext = mContext;

        this.IDocumentListener = listener;
    }


    public void setDocumentsData(List<DocumentModel> documentModelList) {
        this.mData = documentModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        View view = LayoutInflater.from(mContext).inflate(R.layout.item_document_busqueda,viewGroup,false);
//        return new MyViewHolder(view);

        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        ItemCardDocumentBinding itemCardBinding = ItemCardDocumentBinding.inflate(layoutInflater, viewGroup, false);
        return new MyViewHolder(itemCardBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder viewHolder, final int i) {
//        viewHolder.mItem = mData.get(i);
        final int pos = viewHolder.getAdapterPosition();
        //Set ViewTag
        viewHolder.itemView.setTag(pos);

        viewHolder.bindDocument(mData.get(i));

//        viewHolder.itemCardBinding.stagItemCourse.setText(mData.get(i).getCourseTitle());
//        viewHolder.itemCardBinding.stagItemQuantityCourse.setText(mData.get(i).getQuantityCourses());


//      viewHolder.card_item.setBackgroundColor(mContext.getResources().getColor(R.color.color1));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IDocumentListener.ondHomeDocumentClick(mData.get(i));
            }
        });
    }


    @Override
    public long getItemId(int position) {
        DocumentModel courseCard = mData.get(position);
        return courseCard.getId();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
//        return mData.size();
        return mData == null ? 0 : mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ItemCardDocumentBinding itemCardBinding;

        public MyViewHolder(@NonNull ItemCardDocumentBinding cardBinding) {
            super(cardBinding.getRoot());
            this.itemCardBinding = cardBinding;

            //this.itemRecyclerMealBinding.
        }

        void bindDocument(DocumentModel documentModel) {
            switch (documentModel.getEstado()) {
                case MyConstants.ESTADO_PENDIENTE:
                    this.itemCardBinding.tvItemDocumentoEstado.setBackgroundColor( ContextCompat.getColor(itemView.getContext(), R.color.color4));
                    break;
                case MyConstants.ESTADO_NOTIFICADO:
                    this.itemCardBinding.tvItemDocumentoEstado.setBackgroundColor( ContextCompat.getColor(itemView.getContext(), R.color.color2));
                    break;
                default:
                    this.itemCardBinding.tvItemDocumentoEstado.setBackgroundColor( ContextCompat.getColor(itemView.getContext(), R.color.primaryTextColor));
                    break;
            }
            this.itemCardBinding.tvItemNumeroDoc.setText(documentModel.getDocumentName());
            this.itemCardBinding.tvItemAsignado.setText(documentModel.getEncargadoDocumento());
            this.itemCardBinding.tvItemDocumentoEstado.setText(documentModel.getEstado());
        }

    }
}
