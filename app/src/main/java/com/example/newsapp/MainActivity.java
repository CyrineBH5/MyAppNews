package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;
import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NewsAdapter adapter;
    private List<News> newsList = new ArrayList<>();
    private NewsApiService retrofitApi;
    private String apiKey = "4b44a861effb4b1babed24e5cb327c86";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        retrofitApi = RetrofitActivity.getRetrofitInstance().create(NewsApiService.class);
        adapter = new NewsAdapter(MainActivity.this, newsList);
        recyclerView.setAdapter(adapter);

        Spinner categorySpinner = findViewById(R.id.categorySpinner);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.categories, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(spinnerAdapter);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedCat = parentView.getItemAtPosition(position).toString();
                fetchNews(selectedCat.toLowerCase());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                Toast.makeText(MainActivity.this, "Please select a category to filter news", Toast.LENGTH_SHORT).show();

            }
        });

        fetchNews("general");
    }

    private void fetchNews(String category) {
        Call<NewsResponse> call = retrofitApi.getTopHeadlines( category, apiKey);
        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<News> news = response.body().getArticles();
                    Log.d("MainActivity", "Number of news selected : " + news.size());
                    if (news.isEmpty()) {
                        Toast.makeText(MainActivity.this, "No news found for the selected category", Toast.LENGTH_SHORT).show();
                    }
                    newsList.clear();
                    newsList.addAll(news);
                    adapter.notifyDataSetChanged();
                } else {
                    Log.e("MainActivity", "Error in response : " + response.code());
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error fetching news", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
