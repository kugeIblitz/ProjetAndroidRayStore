package com.example.myapplication;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        // Handle incoming FCM messages here
        // You can extract data and show notifications or update UI
        Log.d("FCM Message", "Message data payload: " + remoteMessage.getData());
    }

    @Override
    public void onNewToken(@NonNull String token) {
        // Handle token refresh (if needed)
        Log.d("FCM Token", "Refreshed token: " + token);
    }
}
