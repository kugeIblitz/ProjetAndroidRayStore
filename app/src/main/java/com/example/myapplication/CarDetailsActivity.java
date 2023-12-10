package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.myapplication.Car;
import com.example.myapplication.FavoritesFragment;
import com.example.myapplication.R;
import com.example.myapplication.chatScreen;

public class CarDetailsActivity extends AppCompatActivity {

    private boolean isFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.car_details);

        // Retrieve data from the intent
        Intent intent = getIntent();
        String brand = intent.getStringExtra("brand");
        String model = intent.getStringExtra("model");
        double price = intent.getDoubleExtra("price", 0.0);
        String imageUrl = intent.getStringExtra("imageUrl");

        // Initialize views
        ImageView detailImageView = findViewById(R.id.detailImageView);
        TextView detailBrandTextView = findViewById(R.id.detailBrandTextView);
        TextView detailModelTextView = findViewById(R.id.detailModelTextView);
        TextView detailPriceTextView = findViewById(R.id.detailPriceTextView);
        Button contactOwner = findViewById(R.id.contactOwnerButton);
        ImageView favoritesIcon = findViewById(R.id.favoritesIcon);

        // Set data to views
        Glide.with(this)
                .load(imageUrl)
                .into(detailImageView);

        detailBrandTextView.setText("Brand: " + brand);
        detailModelTextView.setText("Model: " + model);
        detailPriceTextView.setText("Price: $" + String.valueOf(price));

        contactOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CarDetailsActivity.this, chatScreen.class);
                startActivity(intent);
            }
        });

        favoritesIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFavorite) {
                    favoritesIcon.setImageResource(R.drawable.empty_fav);
                } else {
                    favoritesIcon.setImageResource(R.drawable.filledheat);
                    Car favoriteCar = new Car(brand, model, price, imageUrl);
                    DatabaseHelper helper=new DatabaseHelper(CarDetailsActivity.this);
                    helper.addFavoriteCar(favoriteCar);


                }


                isFavorite = !isFavorite;
            }
        });
    }
}
