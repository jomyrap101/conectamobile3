package com.example.conectamobile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditProfile extends AppCompatActivity {

    private EditText editTextName;
    private Button buttonSave;
    private FirebaseAuth auth;
    private DatabaseReference databaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        editTextName = findViewById(R.id.editTextName);
        buttonSave = findViewById(R.id.buttonSave);

        auth = FirebaseAuth.getInstance();
        databaseRef = FirebaseDatabase.getInstance().getReference("users");

        // Cargar el nombre actual del usuario
        String userId = auth.getCurrentUser().getUid();
        databaseRef.child(userId).child("name").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                String currentName = task.getResult().getValue(String.class);
                editTextName.setText(currentName);
            } else {
                Toast.makeText(this, "Error al cargar el nombre", Toast.LENGTH_SHORT).show();
            }
        });

        buttonSave.setOnClickListener(view -> {
            String newName = editTextName.getText().toString().trim();

            if (newName.isEmpty()) {
                Toast.makeText(this, "Por favor, ingresa un nombre", Toast.LENGTH_SHORT).show();
                return;
            }

            // Guardar el nuevo nombre en Firebase
            databaseRef.child(userId).child("name").setValue(newName).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(this, "Nombre actualizado exitosamente", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(EditProfile.this, HomeView.class));
                    finish();
                } else {
                    Toast.makeText(this, "Error al actualizar el nombre", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}