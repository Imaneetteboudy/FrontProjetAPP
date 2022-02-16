package com.example.gestionstock.activity;

import androidx.appcompat.app.AppCompatActivity;

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

public class ArticleNewActivity extends AppCompatActivity {

    Article article;
    Category category;
    EditText label;
    EditText price;
    Button save;
    ArrayList<String> categoriesTitles = new ArrayList<String>();
    ArrayList<Integer> categoriesIds = new ArrayList<Integer>();
    Spinner spinner;
    ArrayList<Category> categories;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_new);

        label = findViewById(R.id.article_label_new);
        price = findViewById(R.id.article_price_new);

        categories = new ArrayList<>();



        spinner = (Spinner) findViewById(R.id.spinner1);

        arrayAdapter = new ArrayAdapter<String>(ArticleNewActivity.this,android.R.layout.simple_spinner_item,categoriesTitles);

        // Set the Adapter
        spinner.setAdapter(arrayAdapter);


        onClickListeners();
        fillArrayList();
    }

    private void onClickListeners() {
        save = findViewById(R.id.save);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                article = new Article();
                String newLabel = label.getText().toString();
                String newPrice = price.getText().toString();
                article.setLabel(newLabel);
                article.setPrice(Double.parseDouble(newPrice));
                System.out.println(article.toString());

                int pos = spinner.getSelectedItemPosition();
                int category_id = categoriesIds.get(pos);

                category = new Category();
                category.setId(category_id);
                System.out.println("printing selected category id");
                System.out.println(category_id);


                Call<Article> articleCall = MainActivity.api.addArticle(article);
                articleCall.enqueue(new Callback<Article>() {
                    @Override
                    public void onResponse(Call<Article> call, Response<Article> response) {
                        Toast.makeText(getApplicationContext(), "onResponse", Toast.LENGTH_SHORT);
                        System.out.println("printing response from add  article");
                        System.out.println(response.body());
                        article = response.body();
                        System.out.println("printing id of saved article");
                        System.out.println(article.getId());

                        System.out.println("printing id of saved article from other side");
                        System.out.println(article.getId());
                        Call<Article> categorieCall = MainActivity.api.addCategoryToArticle(article.getId(), category);
                        categorieCall.enqueue(new Callback<Article>() {
                            @Override
                            public void onResponse(Call<Article> call, Response<Article> response) {
                                Toast.makeText(getApplicationContext(), "onResponse", Toast.LENGTH_SHORT);
                                ArticleNewActivity.this.finish();
                            }

                            @Override
                            public void onFailure(Call<Article> call, Throwable t1) {
                                System.out.println(t1.getLocalizedMessage());
                            }
                        });


                    }

                    @Override
                    public void onFailure(Call<Article> call, Throwable t) {
                        System.out.println(t.getLocalizedMessage());
                    }
                });


            }
        });




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
                    System.out.println("Loading categorie named :"+c.getTitle());
                    categoriesTitles.add(c.getTitle());
                    categoriesIds.add(c.getId());
                    System.out.println(c.toString());
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