package com.example.clownmassegefix;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.clownmassegefix.databinding.ActivityContactsBinding;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Contacts extends AppCompatActivity {
    private ActivityContactsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        binding = ActivityContactsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());




        binding.floatingActionButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Contacts.this,MainActivity.class));
                Contacts.this.finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("Users");
        String user = FirebaseAuth.getInstance().getCurrentUser().getUid();

        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<AllUsers>().setQuery(
                database,
                AllUsers.class).build();

        FirebaseRecyclerAdapter<AllUsers,AllUsersViewHolder> allUsersView = new FirebaseRecyclerAdapter<AllUsers, AllUsersViewHolder>(
                options) {
            @Override
            protected void onBindViewHolder(@NonNull AllUsersViewHolder holder, int position, @NonNull AllUsers model) {


                database.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        String name =  snapshot.child("name").getValue().toString();
                        holder.name.setText(name);

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String profileUID = getRef(holder.getBindingAdapterPosition()).getKey();
                                Intent profile = new Intent(Contacts.this,Profile.class);
                                profile.putExtra("profileUID",profileUID);
                                startActivity(profile);}
                        });

                        }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

            @NonNull
            @Override
            public AllUsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contacts,parent,false);
                AllUsersViewHolder holder = new AllUsersViewHolder(view);
                return holder;
            }
        };
        binding.userView.setAdapter(allUsersView);
        allUsersView.startListening();
    }
    public static class AllUsersViewHolder extends RecyclerView.ViewHolder{

        public TextView name;


        public AllUsersViewHolder(@NonNull View itemView) {
            super(itemView);
             name = itemView.findViewById(R.id.contactName);
            TextView status = itemView.findViewById(R.id.status);
        }
    }

}