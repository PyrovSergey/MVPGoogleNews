package com.example.pyrov.mvpgooglenews.View;

import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pyrov.mvpgooglenews.Model.ArticlesItem;
import com.example.pyrov.mvpgooglenews.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private static final String DATE_PATTERN = "HH:mm  dd MMMM yyyy";

    private ContractActivity.ViewActivity viewActivity;
    private List<ArticlesItem> itemList;

    public NewsAdapter(ContractActivity.ViewActivity viewActivity, List<ArticlesItem> itemList) {
        this.viewActivity = viewActivity;
        this.itemList = itemList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_card, parent, false);
        return new ViewHolder(view);
    }

    public void setData(List<ArticlesItem> list) {
        itemList.addAll(list);
        if (itemList.isEmpty()) {
            viewActivity.showToast("No news");
        }
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ArticlesItem currentItem = itemList.get(position);
        try {
            if (!TextUtils.isEmpty(currentItem.getUrlToImage())) {
                Picasso.get().load(currentItem.getUrlToImage()).placeholder(R.drawable.gn_holder).into(holder.imageView);
            } else {
                Picasso.get().load(R.drawable.gn_holder).into(holder.imageView);
            }
        } catch (NullPointerException e) {
            Picasso.get().load(R.drawable.gn_holder).into(holder.imageView);
        }
        holder.textViewTitle.setText(String.valueOf(currentItem.getTitle()));
        String description = String.valueOf(currentItem.getDescription());
        if (description.equals("null")) {
            description = "";
        }
        holder.textViewDescription.setText(description);
        holder.textViewPublishedAt.setText(getStringDate(currentItem.getPublishedAt()));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewActivity.startBrowser(Uri.parse(currentItem.getUrl()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        CardView cardView;
        ImageView imageView;
        TextView textViewTitle;
        TextView textViewDescription;
        TextView textViewPublishedAt;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            cardView = view.findViewById(R.id.card_view);
            imageView = view.findViewById(R.id.image_news);
            textViewTitle = view.findViewById(R.id.title);
            textViewDescription = view.findViewById(R.id.description);
            textViewPublishedAt = view.findViewById(R.id.publishedAt);
        }
    }

    private String getStringDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN, Locale.getDefault());
        return dateFormat.format(date);
    }
}

