package com.example.conectamobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private EditText emailField, passwordField;
    private Button loginButton, registerButton;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailField = findViewById(R.id.email);
        passwordField = findViewById(R.id.password);
        loginButton = findViewById(R.id.btnLogin);
        registerButton = findViewById(R.id.registerLink);

        // Inicializar Firebase
        auth = FirebaseAuth.getInstance();

        registerButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, Register.class);
            startActivity(intent);
        });

        loginButton.setOnClickListener(v -> {
            String email = emailField.getText().toString().trim();
            String password = passwordField.getText().toString().trim();

            // Validación básica
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            // Iniciar sesión con Firebase Authentication
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();

                            // Obtener el nombre del usuario desde la base de datos
                            if (user != null) {
                                String userId = user.getUid();
                                DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("users");
                                databaseRef.child(userId).get().addOnCompleteListener(dbTask -> {
                                    if (dbTask.isSuccessful() && dbTask.getResult().exists()) {
                                        String username = dbTask.getResult().child("name").getValue(String.class);
                                        Intent intent = new Intent(MainActivity.this, HomeView.class);
                                        intent.putExtra("username", username);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(this, "Error al obtener datos del usuario", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                Toast.makeText(this, "Error al obtener usuario", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
}
