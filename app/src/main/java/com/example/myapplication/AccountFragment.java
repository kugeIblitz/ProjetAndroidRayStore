package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AccountFragment extends Fragment {

    private static final String TAG = "AccountFragment";
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button saveButton;
    private FirebaseAuth auth;

    public AccountFragment() {
        // Required empty public constructor
    }

    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        // Find views by ID
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        saveButton = view.findViewById(R.id.saveButton);

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();

        // Set the existing email
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            editTextEmail.setText(currentUser.getEmail());
        }

        // Handle save button click
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
            }
        });

        return view;
    }

    private void updateProfile() {
        // Retrieve the entered email and password
        String newEmail = editTextEmail.getText().toString();
        String newPassword = editTextPassword.getText().toString();

        // Update email
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            currentUser.updateEmail(newEmail)
                    .addOnCompleteListener(requireActivity(), new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "User email address updated.");
                            } else {
                                Log.w(TAG, "Failed to update user email address.", task.getException());
                                Toast.makeText(requireContext(), "Failed to update email address.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

        // Update password
        if (!newPassword.isEmpty()) {
            currentUser.updatePassword(newPassword)
                    .addOnCompleteListener(requireActivity(), new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "User password updated.");
                            } else {
                                Log.w(TAG, "Failed to update user password.", task.getException());
                                Toast.makeText(requireContext(), "Failed to update password.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

        // You can add UI update logic or show a success message here
    }
}
