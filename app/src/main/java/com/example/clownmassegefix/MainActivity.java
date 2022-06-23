package com.example.clownmassegefix;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.clownmassegefix.databinding.ActivityMainBinding;
import com.example.clownmassegefix.ui.main.SectionsPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Animation rotateOpen = AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim);
        Animation rotateClose = AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim);
        Animation fromBottom = AnimationUtils.loadAnimation(this, R.anim.from_buttom_anim);
        Animation toBottom = AnimationUtils.loadAnimation(this, R.anim.to_buttom_anim);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.Tabs);
        tabs.setupWithViewPager(viewPager);

        binding.floatingActionButton2.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Contacts.class));
            }
        });

        binding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOpen == false) {
                    binding.floatingActionButton.startAnimation(rotateOpen);
                    binding.floatingActionButton2.startAnimation(fromBottom);
                    binding.floatingActionButton2.setVisibility(View.VISIBLE);
                    binding.floatingActionButton3.startAnimation(fromBottom);
                    binding.floatingActionButton3.setVisibility(View.VISIBLE);
                    isOpen = true;
                } else {
                    binding.floatingActionButton.startAnimation(rotateClose);
                    binding.floatingActionButton2.startAnimation(toBottom);
                    binding.floatingActionButton2.setVisibility(View.INVISIBLE);
                    binding.floatingActionButton3.startAnimation(toBottom);
                    binding.floatingActionButton3.setVisibility(View.INVISIBLE);
                    isOpen = false;
                }
            }
        });
    }

}





