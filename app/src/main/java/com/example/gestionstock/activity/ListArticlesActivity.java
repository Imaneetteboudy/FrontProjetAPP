package com.example.gestionstock.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.gestionstock.MainActivity;
import com.example.gestionstock.adapters.ArticleListAdapter;
import com.example.gestionstock.R;
import com.example.gestionstock.models.Article;
import com.example.gestionstock.services.ApiClient;
import com.example.gestionstock.services.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListArticlesActivity extends AppCompatActivity {


    private ArrayList<Article> articles;
    private ListView listView;
    public static ArticleListAdapter listViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_articles);

        initialize();
    }


    @Override
    protected void onResume() {
        super.onResume();
        fillArrayList();
    }

    private void initialize() {
        articles = new ArrayList<>();
        listView = findViewById(R.id.article_list_view);
        listViewAdapter = new ArticleListAdapter(ListArticlesActivity.this, articles);
        listView.setAdapter(listViewAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Article article = articles.get(position);
                Intent i = new Intent(ListArticlesActivity.this, ArticleEditActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("article", article);
                i.putExtras(bundle);
                startActivity(i);
            }
        });


    }

    private void fillArrayList() {
        listViewAdapter.clear();
        Call<List<Article>> listCall = MainActivity.api.getArticles();

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