package com.example.clownmassegefix;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.clownmassegefix.databinding.FragmentChatsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Chats extends Fragment {

    private FragmentChatsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentChatsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("Users");
        String user = FirebaseAuth.getInstance().getCurrentUser().getUid();


        List<String> users = new ArrayList<String>();
        ArrayAdapter<String> list = new ArrayAdapter<String>(getContext(), R.layout.users, R.id.userNames, users);
        binding.chatList.setAdapter(list);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String userList = dataSnapshot.child("name").getValue().toString();
                    assert userList != null;
                    list.add(userList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        list.notifyDataSetChanged();
        loadFriends();

        binding.chatList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String profileUID =users.get(position);
//                Toast.makeText(Chats.this.getActivity(), profileUID, Toast.LENGTH_SHORT).show();
//                Intent profile = new Intent(Chats.this.getActivity(),Profile.class);
//                profile.putExtra("profileUID",profileUID);
//                startActivity(profile);
                startActivity(new Intent(Chats.this.getActivity(), Messages.class));
            }
        });


    }

    private void loadFriends() {
    }
}



