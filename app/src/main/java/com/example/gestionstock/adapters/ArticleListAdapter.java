package com.example.gestionstock.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.gestionstock.R;
import com.example.gestionstock.models.Article;

import java.util.List;

public class ArticleListAdapter extends ArrayAdapter<Article>  {

    private final LayoutInflater inflater;
    private final Context context;
    private ViewHolder holder;
    private final List<Article> articles;

    public ArticleListAdapter(Context context, List<Article> articles) {
        super(context, 0, articles);
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.articles = articles;
    }


    @Override
    public int getCount() {
        return articles.size();
    }

    @Override
    public Article getItem(int position) {
        return articles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return articles.get(position).hashCode();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {

            convertView = inflater.inflate(R.layout.list_view_item, null);

            holder = new ViewHolder();
            holder.id = (TextView) convertView.findViewById(R.id.article_id_label);
            holder.label = (TextView) convertView.findViewById(R.id.article_label_label);
            holder.price = (TextView) convertView.findViewById(R.id.article_price_label);
            convertView.setTag(holder);

        } else {
            //Get viewholder we already created
            holder = (ViewHolder) convertView.getTag();
        }

        Article article = articles.get(position);
        if (article != null) {
            holder.id.setText(Integer.toString(article.getId()));
            holder.label.setText(article.getLabel());
            holder.price.setText(Double.toString(article.getPrice()));

        }
        return convertView;
    }

    private static class ViewHolder {
        TextView id;
        TextView label;
        TextView price;

    }

}

