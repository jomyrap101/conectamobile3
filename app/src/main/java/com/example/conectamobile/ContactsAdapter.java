package com.example.conectamobile;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.conectamobile.UserProfile;

import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactViewHolder> {

    private final Context context;
    private final List<UserProfile> userList;

    public ContactsAdapter(Context context, List<UserProfile> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_contact_item, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        UserProfile user = userList.get(position);
        holder.contactName.setText(user.getName());

        holder.chatButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, ChatActivity.class);
            intent.putExtra("recipientId", user.getId());
            intent.putExtra("recipientName", user.getName());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView contactName;
        Button chatButton;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            contactName = itemView.findViewById(R.id.contactName);
            chatButton = itemView.findViewById(R.id.chatButton);
        }
    }
}
