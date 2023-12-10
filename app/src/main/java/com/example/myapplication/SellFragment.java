package com.example.myapplication;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SellFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SellFragment extends Fragment {

    private EditText editTextBrand, editTextModel, editTextPrice, editTextImageUrl;
    private Button btnAddCar;

    private FirebaseAuth mAuth;
    private DatabaseReference carsRef;

    public SellFragment() {
        // Required empty public constructor
    }

    public static SellFragment newInstance(String param1, String param2) {
        SellFragment fragment = new SellFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        carsRef = database.getReference("cars");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sell, container, false);

        editTextBrand = view.findViewById(R.id.editTextBrand);
        editTextModel = view.findViewById(R.id.editTextModel);
        editTextPrice = view.findViewById(R.id.editTextPrice);
        editTextImageUrl = view.findViewById(R.id.editTextImageUrl);
        btnAddCar = view.findViewById(R.id.btnAddCar);

        btnAddCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCarToDatabase();
            }
        });

        return view;
    }

    private void addCarToDatabase() {
        String brand = editTextBrand.getText().toString().trim();
        String model = editTextModel.getText().toString().trim();
        String priceStr = editTextPrice.getText().toString().trim();
        String imageUrl = editTextImageUrl.getText().toString().trim();

        if (brand.isEmpty() || model.isEmpty() || priceStr.isEmpty() || imageUrl.isEmpty()) {
            Toast.makeText(getActivity(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double price = Double.parseDouble(priceStr);

        // Create a new Car object
        Car car = new Car(brand, model, price, imageUrl);

        // Push the new car to the "cars" reference, which automatically generates a unique key
        carsRef.push().setValue(car);

        Toast.makeText(getActivity(), "Car added successfully", Toast.LENGTH_SHORT).show();
        clearFields();  // Clear input fields after adding the car
    }

    private void clearFields() {
        editTextBrand.setText("");
        editTextModel.setText("");
        editTextPrice.setText("");
        editTextImageUrl.setText("");
    }
}
