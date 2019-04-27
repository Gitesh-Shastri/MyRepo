package com.careeranna.boloanna.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.careeranna.boloanna.R;
import com.careeranna.boloanna.models.Category;

import java.util.ArrayList;

public class CustomNavigationListCategoryAdapter extends RecyclerView.Adapter<CustomNavigationListCategoryAdapter.ViewHolder>{

    private Context mContext;
    private ArrayList<Category> categories;
    private int parent_position;

    public  OnItemClickListener mListener;

    public void setOnItemClicklistener(OnItemClickListener listener) {
        mListener = listener;
    }
    public interface OnItemClickListener {
        void onCategorySelected(int position, int parent_position);
    }

    public CustomNavigationListCategoryAdapter(Context mContext, ArrayList<Category> categories, int parent_position) {
        this.mContext = mContext;
        this.categories = categories;
        this.parent_position = parent_position;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.nav_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.category_name.setText(categories.get(i).getName());

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView category_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            category_name = itemView.findViewById(R.id.expandableListItem);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mListener != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            mListener.onCategorySelected(position, parent_position);
                            notifyDataSetChanged();
                        }
                    }
                }
            });
        }
    }
}
