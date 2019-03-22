package com.careeranna.boloanna.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.careeranna.boloanna.Helper.Utils;
import com.careeranna.boloanna.R;
import com.careeranna.boloanna.models.Article;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ArticleArrayAdapter extends RecyclerView.Adapter<ArticleArrayAdapter.ViewHolder>{
    public static final String TAG = "ArticleArrayAdapter";

    private ArrayList<Article> articles;
    private Context mContext;

    public OnItemClickListener mListener;

    public ArticleArrayAdapter(ArrayList<Article> articles, Context mContext) {
        this.articles = articles;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_news_inside_category, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        if(articles.get(i).getName() != null) {
            viewHolder.articleTitle.setText(articles.get(i).getName());
        }
        if(articles.get(i).getImage_url() != null) {
            Glide.with(mContext)
                    .load(articles.get(i).getImage_url())
                    .into(viewHolder.articleImage);
        }
        viewHolder.articlePostedOn.setText("Posted "+ Utils.formatDate(articles.get(i).getCreated_at()));
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public interface OnItemClickListener {
        void onArticleSelected(int position);
    }

    public void setOnItemClicklistener(OnItemClickListener listener) {
        mListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView articleTitle, articlePostedOn;

        ImageView articleImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            articleTitle = itemView.findViewById(R.id.articleTitle);
            articlePostedOn = itemView.findViewById(R.id.articlePostedOn);
            articleImage = itemView.findViewById(R.id.articleImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mListener != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            mListener.onArticleSelected(position);
                            notifyDataSetChanged();
                        }
                    }
                }
            });
        }
    }

}
