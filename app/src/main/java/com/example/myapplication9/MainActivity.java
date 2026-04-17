package com.example.myapplication9;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnLogin; // change ID to match your activity_main.xml

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin = findViewById(R.id.btnLogin); // Make sure this ID exists in activity_main.xml

        btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, katspage.class);
            startActivity(intent);
            finish(); // closes login so back button doesn't return to it
        });
    }
}
