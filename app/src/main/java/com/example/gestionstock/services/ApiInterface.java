package com.example.gestionstock.services;

import com.example.gestionstock.models.Article;
import com.example.gestionstock.models.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiInterface {

    @GET("articles")
    Call<List<Article>> getArticles();

    @PUT("articles/{id}/")
    Call<Article> updateArticle(@Path("id") int id, @Body Article article);

    @DELETE("articles/{id}/")
    Call<Article> deleteArticle(@Path("id") int id);

    @POST("articles/")
    Call<Article> addArticle(@Body Article article);




    @GET("categories")
    Call<List<Category>> getCategories();

    @PUT("categories/{id}/")
    Call<Category> updateCategory(@Path("id") int id, @Body Category category);

    @DELETE("categories/{id}/")
    Call<Category> deleteCategory(@Path("id") int id);

    @POST("categories/")
    Call<Category> addCategory(@Body Category category);




    @PUT("articles/{id}/category")
    Call<Article> addCategoryToArticle(@Path("id") int id, @Body Category category);

    @GET("categories/{id}/articles")
    Call<List<Article>> getCategoryArticles(@Path("id") int id);


}
