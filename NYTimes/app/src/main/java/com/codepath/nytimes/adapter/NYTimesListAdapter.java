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
import com.codepath.nytimes.model.NYTimesArticle;

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
    private List<NYTimesArticle> nyTimesArticleList = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public NYTimesListAdapter(Context context, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }
    public void setData(List<NYTimesArticle> nyTimesArticles) {
//        nyTimesArticleList.clear();
//        nyTimesArticleList.addAll(nyTimesArticles);
        nyTimesArticleList = nyTimesArticles;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Todo Butterknife bindings

        ImageView ivStoryThumbnail;
        TextView    tvTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            ivStoryThumbnail = itemView.findViewById(R.id.ivStoryThumbnail);
            tvTitle =   itemView.findViewById(R.id.tvStoryTitle);
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
        Log.d(TAG, "OnBind View Holder and position is : "+position);
        NYTimesArticle nyTimesArticle = nyTimesArticleList.get(position);

        String headline = nyTimesArticle.getHeadlines();
        holder.tvTitle.setText(headline);


        if (nyTimesArticle.getMultimediaList() != null && (nyTimesArticle.getMultimediaList().size() > 0)) {
            Log.d(TAG, "What is the Size = "+nyTimesArticle.getMultimediaList().size());
            String thumbnailUrl = "http://www.nytimes.com/" + nyTimesArticle.getMultimediaList().get(0).getUrl();
            Glide.with(context).load(thumbnailUrl).dontAnimate().into(holder.ivStoryThumbnail);
        }

        //Todo: Setup viewholder for item 
        holder.bind(nyTimesArticle, onItemClickListener);
    }


    @Override
    public int getItemCount() {
        Log.d(TAG, "Item Size = "+nyTimesArticleList.size());
        return nyTimesArticleList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

}