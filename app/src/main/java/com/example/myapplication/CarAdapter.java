package com.example.myapplication;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.ViewHolder> {

    private static List<Car> cars;
    private static android.content.Context context; // Change the type of context

    public CarAdapter(android.content.Context context, List<Car> cars) {
        this.context = context;
        this.cars = cars;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Car car = cars.get(position);

        holder.textBrand.setText(car.getBrand());
        holder.textModel.setText(car.getModel());
        holder.textPrice.setText(String.valueOf(car.getPrice()));

        Glide.with(context)
                .load(car.getImageUrl())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return cars.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textBrand;
        TextView textModel;
        TextView textPrice;
        ImageView imageView;
        Button  viewCarButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textBrand = itemView.findViewById(R.id.textBrand);
            textModel = itemView.findViewById(R.id.textModel);
            textPrice = itemView.findViewById(R.id.textPrice);
            imageView = itemView.findViewById(R.id.imageView);
            viewCarButton=itemView.findViewById(R.id.viewCarButton);
            viewCarButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Car car = cars.get(getAdapterPosition());
                    Intent intent = new Intent(context, CarDetailsActivity.class);
                    intent.putExtra("brand", car.getBrand());
                    intent.putExtra("model", car.getModel());
                    intent.putExtra("price", car.getPrice());
                    intent.putExtra("imageUrl", car.getImageUrl());
                    context.startActivity(intent);
                }
            });


        }
    }
}















