package com.example.gestionstock.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestionstock.MainActivity;
import com.example.gestionstock.R;
import com.example.gestionstock.adapters.ArticleListAdapter;
import com.example.gestionstock.models.Article;
import com.example.gestionstock.models.Category;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryEditActivity extends AppCompatActivity {

    Category category;
    EditText title;
    Button edit, delete;
    private ArrayList<Article> articles;
    private ListView listView;
    public static ArticleListAdapter listViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_edit);

        initialize();
        onClickListeners();

        articles = new ArrayList<>();
        listView = findViewById(R.id.category_articles_view);
        listViewAdapter = new ArticleListAdapter(CategoryEditActivity.this, articles);
        listView.setAdapter(listViewAdapter);

        fillArrayList();

    }



    private void onClickListeners() {
        edit = findViewById(R.id.update);
        delete = findViewById(R.id.delete);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newTitle = title.getText().toString();
                category.setTitle(newTitle);
                Call<Category> categoryCall = MainActivity.api.updateCategory(category.getId(), category);
                categoryCall.enqueue(new Callback<Category>() {
                    @Override
                    public void onResponse(Call<Category> call, Response<Category> response) {
                        Toast.makeText(getApplicationContext(), "onResponse", Toast.LENGTH_SHORT);
                        ListCategoriesActivity.listViewAdapter.notifyDataSetChanged();
                        System.out.println("printing edit category response body");
                        System.out.println(response.body().toString());
                        CategoryEditActivity.this.finish();
                    }

                    @Override
                    public void onFailure(Call<Category> call, Throwable t) {
                        System.out.println(t.getLocalizedMessage());
                    }
                });
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<Category> call = MainActivity.api.deleteCategory(category.getId());
                call.enqueue(new Callback<Category>() {
                    @Override
                    public void onResponse(Call<Category> call, Response<Category> response) {
                        Toast.makeText(getApplicationContext(), "onResponse", Toast.LENGTH_SHORT);
                        ListCategoriesActivity.listViewAdapter.notifyDataSetChanged();
                        CategoryEditActivity.this.finish();
                    }

                    @Override
                    public void onFailure(Call<Category> call, Throwable t) {
                        System.out.println(t.getLocalizedMessage());
                    }
                });
            }
        });


    }

    private void initialize() {
        title = findViewById(R.id.category_title_edit);

        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        category = (Category) bundle.getSerializable("category");


        assert category != null;
        title.setText(category.getTitle());

    }


    private void fillArrayList() {
        listViewAdapter.clear();
        Call<List<Article>> listCall = MainActivity.api.getCategoryArticles(category.getId());

        listCall.enqueue(new Callback<List<Article>>() {
            @Override
            public void onResponse(Call<List<Article>> call, Response<List<Article>> response) {
                List<Article> articlesList;
                articlesList = response.body();


                for (Article a : articlesList) {
                    articles.add(a);
                    System.out.println(a.toString());
                }

                listViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Article>> call, Throwable t) {
                System.out.println(t.getLocalizedMessage());
            }
        });

    }



}
