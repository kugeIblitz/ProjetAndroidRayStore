package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Car;
import com.example.myapplication.DatabaseHelper;
import com.example.myapplication.FavoritesAdapter;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class FavoritesFragment extends Fragment {

    private List<Car> favoriteCars;
    private RecyclerView recyclerView;
    private FavoritesAdapter favoritesAdapter;
    private TextView textViewEmptyState;

    // Declare a DatabaseHelper
    private DatabaseHelper databaseHelper;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public FavoritesFragment() {
        // Required empty public constructor
    }

    public static FavoritesFragment newInstance(String param1, String param2) {
        FavoritesFragment fragment = new FavoritesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize the DatabaseHelper
        databaseHelper = new DatabaseHelper(getContext());
        // Initialize the list of favorite cars
        favoriteCars = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewFavorites);
        textViewEmptyState = view.findViewById(R.id.textViewEmptyState);

        // Initialize RecyclerView and set layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        // Initialize and set up the adapter
        favoritesAdapter = new FavoritesAdapter(favoriteCars);
        recyclerView.setAdapter(favoritesAdapter);

        // Load favorite cars from the database
        loadFavoriteCars();

        // Check if there are favorite cars to display
        updateEmptyState();

        return view;
    }

    // Method to add a favorite car to the list and the database
    public void addFavoriteCar(Car car) {
        // Add to the database
        databaseHelper.addFavoriteCar(car);

        // Refresh the favorite car list from the database
        loadFavoriteCars();

        // Notify the adapter
        favoritesAdapter.notifyDataSetChanged();

        // Update the empty state TextView visibility
        updateEmptyState();
    }

    // Method to load favorite cars from the database
    private void loadFavoriteCars() {
        favoriteCars.clear();
        favoriteCars.addAll(databaseHelper.getAllFavoriteCars());
    }

    // Helper method to update the empty state TextView visibility
    private void updateEmptyState() {
        if (favoriteCars.isEmpty()) {
            textViewEmptyState.setVisibility(View.VISIBLE);
        } else {
            textViewEmptyState.setVisibility(View.GONE);
        }
    }
}
