package com.example.clownmassegefix;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

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
        binding = FragmentChatsBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        List<String> users = new ArrayList<String>();
        ArrayAdapter<String> list = new ArrayAdapter<String>(getContext(),R.layout.users,R.id.userNames
                ,users);
        binding.chatList.setAdapter(list);
        FirebaseAuth authentication = FirebaseAuth.getInstance ();
        String currentUserID = authentication.getCurrentUser ().getUid ();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot :snapshot.getChildren()){
                    String userList = dataSnapshot.child("name").getValue().toString();
                    assert userList !=null;
                    list.add(userList);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        list.notifyDataSetChanged();

        binding.chatList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(Chats.this.getActivity(),Messages.class));
            }
        });

    }
}
