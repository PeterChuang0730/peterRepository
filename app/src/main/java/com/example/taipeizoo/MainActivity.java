package com.example.taipeizoo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.taipeizoo.fragment.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fragment minFragment = new MainFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.mainLayout, minFragment).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}