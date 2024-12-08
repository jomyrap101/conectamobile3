package com.example.conectamobile;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.conectamobile.UserProfile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Contacts extends AppCompatActivity {

    private RecyclerView contactsRecyclerView;
    private ContactsAdapter contactsAdapter;
    private List<UserProfile> contactList;
    private DatabaseReference usersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        contactsRecyclerView = findViewById(R.id.contactsRecyclerView);
        contactsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        contactList = new ArrayList<>();
        contactsAdapter = new ContactsAdapter(this, contactList);
        contactsRecyclerView.setAdapter(contactsAdapter);

        // Cargar usuarios desde Firebase Database
        usersRef = FirebaseDatabase.getInstance().getReference("users");
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                contactList.clear();
                for (DataSnapshot data : snapshot.getChildren()) {
                    UserProfile user = data.getValue(UserProfile.class);
                    if (user != null) {
                        user.setId(data.getKey()); // Asignar ID de Firebase
                        contactList.add(user);
                    }
                }
                contactsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Contacts.this, "Error al cargar contactos", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
