package com.example.myapplication;
import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Car;
import com.example.myapplication.R;

import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {

    private List<Car> favoriteCars;

    public FavoritesAdapter(List<Car> favoriteCars) {
        this.favoriteCars = favoriteCars;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite_car, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Car car = favoriteCars.get(position);

        // Bind data to the ViewHolder
        holder.textBrand.setText("Brand: " + car.getBrand());
        holder.textModel.setText("Model: " + car.getModel());
        holder.textPrice.setText("Price: $" + car.getPrice());

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle delete action here, e.g., remove the item from the list and notify the adapter
                favoriteCars.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return favoriteCars.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Declare the views in the item layout
        TextView textBrand;
        TextView textModel;
        TextView textPrice;
        Button btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // Initialize views
            textBrand = itemView.findViewById(R.id.textBrand);
            textModel = itemView.findViewById(R.id.textModel);
            textPrice = itemView.findViewById(R.id.textPrice);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
