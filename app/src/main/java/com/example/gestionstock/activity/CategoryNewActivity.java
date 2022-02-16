package com.example.gestionstock.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestionstock.MainActivity;
import com.example.gestionstock.R;
import com.example.gestionstock.models.Article;
import com.example.gestionstock.models.Category;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryNewActivity extends AppCompatActivity {
    Category category;
    EditText title;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_new);

        title = findViewById(R.id.category_title_new);
        onClickListeners();

    }

    private void onClickListeners() {
        save = findViewById(R.id.save);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                category = new Category();
                String newTitle = title.getText().toString();
                category.setTitle(newTitle);
                System.out.println("printing before adding");
                System.out.println(category.toString());
                Call<Category> categoryCall = MainActivity.api.addCategory(category);
                categoryCall.enqueue(new Callback<Category>() {
                    @Override
                    public void onResponse(Call<Category> call, Response<Category> response) {
                        Toast.makeText(getApplicationContext(), "onResponse", Toast.LENGTH_SHORT);
                        System.out.println("printing add category response body");
                        System.out.println(response.body().toString());
                        CategoryNewActivity.this.finish();

                    }

                    @Override
                    public void onFailure(Call<Category> call, Throwable t) {
                        System.out.println(t.getLocalizedMessage());
                    }
                });
            }
        });




    }





}
