package com.example.gestionstock.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.gestionstock.MainActivity;
import com.example.gestionstock.R;
import com.example.gestionstock.models.Article;
import com.example.gestionstock.models.Category;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticleEditActivity extends AppCompatActivity {

    Article article;
    EditText label;
    EditText price;
    Button edit, delete;
    ArrayList<String> categoriesTitles = new ArrayList<String>();
    ArrayList<Integer> categoriesIds = new ArrayList<Integer>();
    Spinner spinner;
    ArrayList<Category> categories;
    ArrayAdapter<String> arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_edit);

        initialize();
        onClickListeners();

        categories = new ArrayList<>();



        spinner = (Spinner) findViewById(R.id.spinner2);

        arrayAdapter = new ArrayAdapter<String>(ArticleEditActivity.this,android.R.layout.simple_spinner_item,categoriesTitles);

        // Set the Adapter
        spinner.setAdapter(arrayAdapter);


        fillArrayList();





    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ArticleEditActivity.this.finish();

    }

    private void onClickListeners() {
        edit = findViewById(R.id.update);
        delete = findViewById(R.id.delete);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newLabel = label.getText().toString();
                String newPrice = price.getText().toString();
                article.setLabel(newLabel);
                article.setPrice(Double.parseDouble(newPrice));
                Call<Article> articleCall = MainActivity.api.updateArticle(article.getId(), article);
                articleCall.enqueue(new Callback<Article>() {
                    @Override
                    public void onResponse(Call<Article> call, Response<Article> response) {
                        Toast.makeText(getApplicationContext(), "onResponse", Toast.LENGTH_SHORT);
                        ListArticlesActivity.listViewAdapter.notifyDataSetChanged();
                        ArticleEditActivity.this.finish();
                    }

                    @Override
                    public void onFailure(Call<Article> call, Throwable t) {
                        System.out.println(t.getLocalizedMessage());
                    }
                });
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<Article> call = MainActivity.api.deleteArticle(article.getId());
                call.enqueue(new Callback<Article>() {
                    @Override
                    public void onResponse(Call<Article> call, Response<Article> response) {
                        Toast.makeText(getApplicationContext(), "onResponse", Toast.LENGTH_SHORT);
                        ListArticlesActivity.listViewAdapter.notifyDataSetChanged();
                        ArticleEditActivity.this.finish();
                    }

                    @Override
                    public void onFailure(Call<Article> call, Throwable t) {
                        System.out.println(t.getLocalizedMessage());
                    }
                });
            }
        });


    }

    private void initialize() {
        label = findViewById(R.id.article_label_edit);
        price = findViewById(R.id.article_price_edit);

        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        article = (Article) bundle.getSerializable("article");


        assert article != null;
        label.setText(article.getLabel());
        price.setText(Double.toString(article.getPrice()));


    }


    private void fillArrayList() {

        Call<List<Category>> listCall = MainActivity.api.getCategories();

        listCall.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                List<Category> categoriesList;
                categoriesList = response.body();
                System.out.println("Now printing response body");
                System.out.println(response.body());

                for (Category c : categoriesList) {
                    categories.add(c);

                    categoriesTitles.add(c.getTitle());
                    categoriesIds.add(c.getId());


                }

                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                System.out.println(t.getLocalizedMessage());
            }
        });

    }









}