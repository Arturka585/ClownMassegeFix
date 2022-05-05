package com.example.clownmassegefix;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.clownmassegefix.databinding.FragmentSettingsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Settings extends Fragment {

    private FragmentSettingsBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        binding = FragmentSettingsBinding.inflate (inflater,container,false);
        return binding.getRoot ();

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        DatabaseReference rootReference = FirebaseDatabase.getInstance ().getReference ("Users");
        FirebaseAuth authentication = FirebaseAuth.getInstance ();
        String currentUserID = authentication.getCurrentUser ().getUid ();


        binding.profileImage.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                Intent gallery =new Intent ().setAction (Intent.ACTION_GET_CONTENT).setType ("image/*");
            getActivity ().startActivityForResult (gallery,0);
            }
        });


        binding.ChangeName.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {

                HashMap<String, Object> profile = new HashMap<> ();
                profile.put ("name",binding.userName.getText ().toString ().trim ());

                rootReference.child (currentUserID).setValue (profile)
                        .addOnCompleteListener (new OnCompleteListener<Void> () {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful ()){
                                    Toast.makeText (getActivity (), "Смена имени прошла успешно", Toast.LENGTH_SHORT).show ();
                                }else {
                                    Toast.makeText (getActivity (), "Смена имени не удалась, обратитесь в поддержку " + task.getException ().toString (), Toast.LENGTH_LONG).show ();
                                }
                            }
                        });
            }
        });

        rootReference.child (currentUserID)
                .addValueEventListener (new ValueEventListener () {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String userName = snapshot.child ("name").getValue ().toString ();
                        binding.userName.setText (userName);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

        binding.SingOut.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance ().signOut ();
                startActivity (new Intent (Settings.this.getActivity (),Login.class));
            }
        });
    }
}
