package com.example.newsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {
    private TextView detailTitle, detailDescription, detailAuthor, detailPublishedAt, detailContent,detailUrl ;
    private ImageView detailImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        detailTitle = findViewById(R.id.detailTitle);
        detailDescription = findViewById(R.id.detailDescription);
        detailAuthor = findViewById(R.id.detailAuthor);
        detailPublishedAt = findViewById(R.id.detailPublishedAt);
        detailContent = findViewById(R.id.detailContent);
        detailImage = findViewById(R.id.detailImage);
        detailUrl = findViewById(R.id.detailUrl);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String description = intent.getStringExtra("description");
        String author = intent.getStringExtra("author");
        String publishedAt = intent.getStringExtra("publishedAt");
        String content = intent.getStringExtra("content");
        String imageUrl = intent.getStringExtra("imageUrl");
        String url = intent.getStringExtra("url");

        detailTitle.setText(title);
        detailDescription.setText(description);
        detailAuthor.setText("Author: " + author);
        detailPublishedAt.setText("Published on: " + publishedAt);
        detailContent.setText(content);
        detailUrl.setText(url);

        Glide.with(this)
                .load(imageUrl)
                .into(detailImage);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_share) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, getIntent().getStringExtra("url"));
            startActivity(Intent.createChooser(shareIntent, "Partager via"));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
