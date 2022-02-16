package com.example.gestionstock.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestionstock.MainActivity;
import com.example.gestionstock.R;
import com.example.gestionstock.adapters.ArticleListAdapter;
import com.example.gestionstock.adapters.CategoryListAdapter;
import com.example.gestionstock.models.Article;
import com.example.gestionstock.models.Category;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListCategoriesActivity extends AppCompatActivity {




    private ArrayList<Category> categories;
    private ListView listView;
    public static CategoryListAdapter listViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_categories);

        initialize();
    }


    @Override
    protected void onResume() {
        super.onResume();
        fillArrayList();
    }

    private void initialize() {
        categories = new ArrayList<>();
        listView = findViewById(R.id.category_list_view);
        listViewAdapter = new CategoryListAdapter(ListCategoriesActivity.this, categories);
        listView.setAdapter(listViewAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Category category = categories.get(position);
                System.out.println("printing category data from click");
                System.out.println(category.getId());
                System.out.println(category.getTitle());
                Intent i = new Intent(ListCategoriesActivity.this, CategoryEditActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("category", category);
                i.putExtras(bundle);
                startActivity(i);
            }
        });


    }





    private void fillArrayList() {
        listViewAdapter.clear();
        Call<List<Category>> listCall = MainActivity.api.getCategories();

        listCall.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                List<Category> categoriesList;
                categoriesList = response.body();


                for (Category c : categoriesList) {
                    categories.add(c);
                    System.out.println(c.toString());
                }

                listViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                System.out.println(t.getLocalizedMessage());
            }
        });

    }













}
