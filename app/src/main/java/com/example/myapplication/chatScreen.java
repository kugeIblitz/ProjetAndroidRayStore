package com.example.myapplication;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

public class chatScreen extends AppCompatActivity {

    private List<Message> messageList;
    private ArrayAdapter<Message> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_screen);

        messageList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, messageList);

        ListView listViewChat = findViewById(R.id.listViewChat);
        listViewChat.setAdapter(adapter);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            // Handle token retrieval failure
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                        System.out.println(token);
                    }
                });

        Button buttonSend = findViewById(R.id.buttonSend);
        EditText editTextMessage = findViewById(R.id.editTextMessage);

        buttonSend.setOnClickListener(v -> {
            String messageText = editTextMessage.getText().toString().trim();
            if (!messageText.isEmpty()) {
                // Assuming "user" is the sender (you can replace it with the actual sender)
                Message message = new Message("user", messageText);
                messageList.add(message);

                // Notify the adapter that the data set has changed
                runOnUiThread(() -> adapter.notifyDataSetChanged());

                editTextMessage.setText("");
            }
        });
    }
}
