package com.example.conectamobile;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        String username = getIntent().getStringExtra("username");

        TextView chatHeader = findViewById(R.id.chatHeader);
        if (username != null && !username.isEmpty()) {
            chatHeader.setText("Chat con " + username);
        } else {
            chatHeader.setText("Chat");
        }
    }
}
