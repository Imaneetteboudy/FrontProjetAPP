package com.example.gestionstock.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.gestionstock.R;
import com.example.gestionstock.models.Category;

import java.util.List;


public class CategoryListAdapter extends ArrayAdapter<Category>  {

    private final LayoutInflater inflater;
    private final Context context;
    private ViewHolder holder;
    private final List<Category> categories;

    public CategoryListAdapter(Context context, List<Category> categories) {
        super(context, 0, categories);
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.categories = categories;
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Category getItem(int position) {
        return categories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return categories.get(position).hashCode();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {

            convertView = inflater.inflate(R.layout.list_view_category, null);

            holder = new ViewHolder();
            holder.id = (TextView) convertView.findViewById(R.id.category_id_label);
            holder.title = (TextView) convertView.findViewById(R.id.category_title_label);
            convertView.setTag(holder);

        } else {
            //Get viewholder we already created
            holder = (ViewHolder) convertView.getTag();
        }

        Category category = categories.get(position);
        if (category != null) {
            holder.id.setText(Integer.toString(category.getId()));
            holder.title.setText(category.getTitle());


        }
        return convertView;
    }

    private static class ViewHolder {
        TextView id;
        TextView title;


    }





}
