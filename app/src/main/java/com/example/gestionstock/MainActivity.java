package com.example.gestionstock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.gestionstock.activity.ArticleNewActivity;
import com.example.gestionstock.activity.CategoryNewActivity;
import com.example.gestionstock.activity.ListArticlesActivity;
import com.example.gestionstock.activity.ListCategoriesActivity;
import com.example.gestionstock.services.ApiClient;
import com.example.gestionstock.services.ApiInterface;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public static ApiInterface api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        api = ApiClient.getClient().create(ApiInterface.class);

    }

    public void getArticles(View view) {
        Intent i = new Intent(MainActivity.this, ListArticlesActivity.class);
        startActivity(i);
    }

    public void getCategories(View view) {
        Intent i = new Intent(MainActivity.this, ListCategoriesActivity.class);
        startActivity(i);
    }



    public void postArticle(View view) {
        Intent i = new Intent(MainActivity.this, ArticleNewActivity.class);
        startActivity(i);
    }


    public void postCategory(View view) {
        Intent i = new Intent(MainActivity.this, CategoryNewActivity.class);
        startActivity(i);
    }




}