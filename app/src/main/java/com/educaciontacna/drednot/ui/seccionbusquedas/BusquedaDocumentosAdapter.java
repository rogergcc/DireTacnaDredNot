/*
 * Copyright (c) 2020. rogergcc
 */

package com.educaciontacna.drednot.ui.seccionbusquedas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.educaciontacna.drednot.databinding.ItemDocumentBusquedaBinding;
import com.educaciontacna.drednot.ui.listeners.IDocumentListener;
import com.educaciontacna.drednot.data.model.CourseCard;

import java.util.List;

public class BusquedaDocumentosAdapter extends RecyclerView.Adapter<BusquedaDocumentosAdapter._ViewHolder> {

    Context mContext;


    private List<CourseCard> mData;
    private IDocumentListener IDocumentListener;

    public BusquedaDocumentosAdapter(Context mContext, List<CourseCard> mData, IDocumentListener listener) {
        this.mContext = mContext;
        this.mData = mData;
        this.IDocumentListener = listener;
    }

    @NonNull
    @Override
    public BusquedaDocumentosAdapter._ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        View view = LayoutInflater.from(mContext).inflate(R.layout.item_document_busqueda,viewGroup,false);
//        return new MyViewHolder(view);

        LayoutInflater layoutInflater= LayoutInflater.from(viewGroup.getContext());
        ItemDocumentBusquedaBinding itemCardBinding = ItemDocumentBusquedaBinding.inflate(layoutInflater,viewGroup,false);
        return new _ViewHolder(itemCardBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final BusquedaDocumentosAdapter._ViewHolder viewHolder, final int i) {
//        viewHolder.mItem = mData.get(i);
        final int pos = viewHolder.getAdapterPosition();
        //Set ViewTag
        viewHolder.itemView.setTag(pos);

        viewHolder.setPostImage(mData.get(i));

//        viewHolder.itemCardBinding.stagItemCourse.setText(mData.get(i).getCourseTitle());
//        viewHolder.itemCardBinding.stagItemQuantityCourse.setText(mData.get(i).getQuantityCourses());


//      viewHolder.card_item.setBackgroundColor(mContext.getResources().getColor(R.color.color1));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IDocumentListener.onDashboardCourseClick(mData.get(i), viewHolder.itemCardBinding.cardViewImage);
            }
        });
    }


    @Override
    public long getItemId(int position) {
        CourseCard courseCard = mData.get(position);
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

    public static class _ViewHolder extends RecyclerView.ViewHolder{
//        ImageView imageView;
//        TextView course;
//        TextView quantity_courses;
//        CardView card_item;
//        public CourseCard mItem;
//        public MyViewHolder(@NonNull View itemView) {
//            super(itemView);
//            card_item = itemView.findViewById(R.id.card_item);
//            imageView = itemView.findViewById(R.id.card_view_image);
//            course = itemView.findViewById(R.id.stag_item_course);
//            quantity_courses = itemView.findViewById(R.id.stag_item_quantity_course);
//        }

        ItemDocumentBusquedaBinding itemCardBinding;
        public _ViewHolder(@NonNull ItemDocumentBusquedaBinding cardBinding) {
            super(cardBinding.getRoot());
            this.itemCardBinding = cardBinding;

            //this.itemRecyclerMealBinding.
        }

        void setPostImage(CourseCard courseCard){
            this.itemCardBinding.cardViewImage.setImageResource(courseCard.getImageCourse());
            this.itemCardBinding.stagItemCourse.setText(courseCard.getCourseTitle());
            this.itemCardBinding.stagItemQuantityCourse.setText(courseCard.getQuantityCourses());
        }

    }
}
