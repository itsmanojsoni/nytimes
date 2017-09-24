package com.codepath.nytimes.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.nytimes.R;
import com.codepath.nytimes.model.Multimedia;
import com.codepath.nytimes.model.NYTimesArticle;
import com.codepath.nytimes.util.DynamicHeightImageView;
import com.codepath.nytimes.util.GenerateThumbnailUrl;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by manoj on 9/22/17.
 */
public class NYTimesListAdapter extends
        RecyclerView.Adapter<NYTimesListAdapter.ViewHolder> {

    private static final String TAG = NYTimesListAdapter.class.getSimpleName();

    private Context context;
    private List<NYTimesArticle> nyTimesArticleList;
    private OnItemClickListener onItemClickListener;

    public NYTimesListAdapter(Context context, List<NYTimesArticle> nyTimesArticleList,OnItemClickListener onItemClickListener) {
        this.context = context;
        this.nyTimesArticleList = nyTimesArticleList;
        this.onItemClickListener = onItemClickListener;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Todo Butterknife bindings

        DynamicHeightImageView ivStoryThumbnail;
        TextView    tvTitle;
        TextView    tvCategory;
        TextView    tvDate;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            ivStoryThumbnail = itemView.findViewById(R.id.ivStoryThumbnail);
            tvTitle =   itemView.findViewById(R.id.tvStoryTitle);
            tvCategory = itemView.findViewById(R.id.tvCategory);
        }

        public void bind(final NYTimesArticle model,
                         final OnItemClickListener listener) {


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(getLayoutPosition());

                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.activity_nytimes_list, parent, false);
        ButterKnife.bind(this, view);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NYTimesArticle nyTimesArticle = nyTimesArticleList.get(position);

        String headline = nyTimesArticle.getHeadlines();
        holder.tvTitle.setText(headline);

        String new_desk = nyTimesArticle.getNew_desk();

        if (new_desk != null && !new_desk.isEmpty()) {

            holder.tvCategory.setText(new_desk);
        }

        if (nyTimesArticle.getMultimediaList() != null && (nyTimesArticle.getMultimediaList().size() > 0)) {
            Multimedia photo = nyTimesArticle.getMultimediaList().get(0);
            String thumbnailUrl = GenerateThumbnailUrl.getThumbnail(photo.getUrl());
            holder.ivStoryThumbnail.setHeightRatio(((double)photo.getHeight())/photo.getWidth());
            Glide.with(context).load(thumbnailUrl).dontAnimate().into(holder.ivStoryThumbnail);
        }

        //Todo: Setup viewholder for item 
        holder.bind(nyTimesArticle, onItemClickListener);
    }


    @Override
    public int getItemCount() {
        return nyTimesArticleList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

}