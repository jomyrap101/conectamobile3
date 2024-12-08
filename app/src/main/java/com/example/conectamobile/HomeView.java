package com.example.conectamobile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HomeView extends AppCompatActivity {
    private Button btnContacts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_view);

        TextView welcomeText = findViewById(R.id.welcomeText);
        Button editProfileButton = findViewById(R.id.btnEditProfile);
        Button searchContacts = findViewById(R.id.btnSearchContacts);
        btnContacts = findViewById(R.id.btnContacts);


        String username = getIntent().getStringExtra("username");

        if (username != null && !username.isEmpty()) {
            welcomeText.setText("Bienvenido " + username);
        } else {
            welcomeText.setText("Bienvenido");
        }

        editProfileButton.setOnClickListener(view -> {
            Intent intent = new Intent(HomeView.this, EditProfile.class);
            startActivity(intent);
        });

        searchContacts.setOnClickListener(view -> {
            Intent intent = new Intent(HomeView.this, SearchContactsActivity.class);
            startActivity(intent);
        });

        btnContacts.setOnClickListener(view -> {
            Intent intent = new Intent(HomeView.this, Contacts.class);
            startActivity(intent);
        });


    }
}