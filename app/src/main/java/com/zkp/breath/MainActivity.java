package com.zkp.breath;

import android.content.Intent;
import android.os.Bundle;

import com.zkp.breath.views.EventActivity;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, EventActivity.class);
        startActivity(intent);
    }
}
