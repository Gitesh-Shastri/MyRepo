package com.careeranna.boloanna.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.careeranna.boloanna.R;
import com.careeranna.boloanna.models.User;

import java.util.ArrayList;

public class VideosArrayAdapter extends RecyclerView.Adapter<VideosArrayAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<User> users;

    public OnItemClickListener mListener;

    public VideosArrayAdapter(Context mContext, ArrayList<User> users) {
        this.mContext = mContext;
        this.users = users;
    }

    public interface OnItemClickListener {
        void onVideoItemSelected(int position);
    }

    public void setOnItemClicklistener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_video_inside_category, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mListener != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            mListener.onVideoItemSelected(position);
                            notifyDataSetChanged();
                        }
                    }
                }
            });

        }
    }
}
