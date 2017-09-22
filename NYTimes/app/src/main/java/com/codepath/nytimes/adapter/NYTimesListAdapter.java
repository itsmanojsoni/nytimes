package com.codepath.nytimes.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        this.nyTimesArticleList = nyTimesArticles;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Todo Butterknife bindings

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

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
        NYTimesArticle item = nyTimesArticleList.get(position);

        //Todo: Setup viewholder for item 
        holder.bind(item, onItemClickListener);
    }


    @Override
    public int getItemCount() {
        return nyTimesArticleList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

}