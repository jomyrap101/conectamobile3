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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    private EditText emailRegister, passwordRegister, passwordRegisterX2, nameRegister;
    private Button createAccount;
    private FirebaseAuth auth;
    private DatabaseReference databaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailRegister = findViewById(R.id.emailRegister);
        passwordRegister = findViewById(R.id.passwordRegister);
        passwordRegisterX2 = findViewById(R.id.passwordRegisterX2);
        createAccount = findViewById(R.id.btnCreate);
        nameRegister = findViewById(R.id.nameRegister);

        // Inicializar Firebase
        auth = FirebaseAuth.getInstance();
        databaseRef = FirebaseDatabase.getInstance().getReference("users");


        createAccount.setOnClickListener(view -> {
            String email = emailRegister.getText().toString().trim();
            String password = passwordRegister.getText().toString().trim();
            String passwordConfirm = passwordRegisterX2.getText().toString().trim();
            String name = nameRegister.getText().toString().trim();

            // Validar entradas
            if (email.isEmpty() || password.isEmpty() || passwordConfirm.isEmpty() || name.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(passwordConfirm)) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                return;
            }

            if (password.length() < 6) {
                Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
                return;
            }

            // Registrar al usuario con Firebase
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    FirebaseUser user = auth.getCurrentUser();
                    if (user != null) {
                        String userId = user.getUid();

                        // Guardar el nombre del usuario en Firebase Database
                        databaseRef.child(userId).setValue(new UserProfile(name, email))
                                .addOnCompleteListener(dbTask -> {
                                    if (dbTask.isSuccessful()) {
                                        Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(Register.this, HomeView.class);
                                        intent.putExtra("username", name);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(this, "Error al guardar los datos", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                } else {
                    Toast.makeText(this, "Error al registrar: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}