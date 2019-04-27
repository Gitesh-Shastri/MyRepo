package com.careeranna.boloanna.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.careeranna.boloanna.LandingActivity;
import com.careeranna.boloanna.R;
import com.careeranna.boloanna.models.NavItems;

import java.util.ArrayList;

public class CustomNavigationListAdapter extends RecyclerView.Adapter<CustomNavigationListAdapter.ViewHolder> implements CustomNavigationListCategoryAdapter.OnItemClickListener{

    private Context mContext;
    private ArrayList<NavItems> navItems;

    CustomNavigationListCategoryAdapter customNavigationListCategoryAdapter;

    public CustomNavigationListAdapter(Context mContext, ArrayList<NavItems> navItems) {
        this.mContext = mContext;
        this.navItems = navItems;
    }

    public OnItemClickListener mListener;

    public void setOnItemClicklistener(OnItemClickListener listener) {
        mListener = listener;
    }
    public interface OnItemClickListener {
        void onNavItemSelected(int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.nav_list_group, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.navItemName.setText(navItems.get(i).getTitle());

        final int sdk = android.os.Build.VERSION.SDK_INT;

        if(navItems.get(i).getTitle().equals("Sign Out")) {
            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                viewHolder.groupLayout.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.border_bottom_for_active_tab));
            } else {
                viewHolder.groupLayout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.border_bottom_for_active_tab));
            }
            viewHolder.navItemName.setTextColor(Color.parseColor("#FFFFFF"));
            viewHolder.navItemName.setGravity(Gravity.CENTER_HORIZONTAL);

        }
        if(navItems.get(i).getCategories().size() == 0 ) {
            viewHolder.arrow_indicator.setVisibility(View.GONE);
        } else {
            customNavigationListCategoryAdapter = new CustomNavigationListCategoryAdapter(mContext, navItems.get(i).getCategories(), i);
            viewHolder.categories_rv.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
            viewHolder.categories_rv.setAdapter(customNavigationListCategoryAdapter);
            customNavigationListCategoryAdapter.setOnItemClicklistener(this);
        }

    }

    @Override
    public int getItemCount() {
        return navItems.size();
    }

    @Override
    public void onCategorySelected(int position, int parent_position) {
        ((LandingActivity)mContext).changeTitle(position, parent_position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView navItemName;

        LinearLayout groupLayout;

        ImageView arrow_indicator;

        RecyclerView categories_rv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            navItemName = itemView.findViewById(R.id.listTitle);
            groupLayout = itemView.findViewById(R.id.group_layout);
            arrow_indicator = itemView.findViewById(R.id.arrow_indicator);

            categories_rv = itemView.findViewById(R.id.navListCategories);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(categories_rv.getVisibility() == View.VISIBLE) {
                        categories_rv.setVisibility(View.GONE);
                    } else {
                        categories_rv.setVisibility(View.VISIBLE);
                    }
                    if(mListener != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            mListener.onNavItemSelected(position);
                            notifyDataSetChanged();
                        }
                    }
                }
            });

        }
    }
}