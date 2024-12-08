package com.example.conectamobile;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class SearchContactsActivity extends AppCompatActivity {

    private EditText searchBar;
    private RecyclerView recyclerView;
    private SearchAdapter adapter;
    private List<String> userList;
    private DatabaseReference usersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_contacts);

        searchBar = findViewById(R.id.searchBar);
        recyclerView = findViewById(R.id.recyclerView);

        // Inicializar Firebase
        usersRef = FirebaseDatabase.getInstance().getReference("users");
        userList = new ArrayList<>();
        adapter = new SearchAdapter(this, userList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Cargar usuarios desde Firebase
        usersRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                for (DataSnapshot snapshot : task.getResult().getChildren()) {
                    String userName = snapshot.child("name").getValue(String.class);
                    if (userName != null) {
                        userList.add(userName);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });

        // Filtrar contactos al escribir en la barra de búsqueda
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterContacts(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void filterContacts(String query) {
        List<String> filteredList = new ArrayList<>();
        for (String user : userList) {
            if (user.toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(user);
            }
        }
        adapter = new SearchAdapter(this, filteredList);
        recyclerView.setAdapter(adapter);
    }
}
