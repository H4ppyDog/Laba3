package com.example.myapplication3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class activity_main  extends AppCompatActivity {
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        dbHelper = new DatabaseHelper(this);

        Button viewButton = findViewById(R.id.viewButton);
        Button addButton = findViewById(R.id.addButton);
        Button updateButton = findViewById(R.id.updateButton);

        viewButton.setOnClickListener(v -> {
            Intent intent = new Intent(activity_main .this, ViewActivity.class);
            startActivity(intent);
        });

        addButton.setOnClickListener(v -> dbHelper.addRecord("Новое ФИО"));

        updateButton.setOnClickListener(v -> dbHelper.updateLastRecord("Иванов Иван Иванович"));
    }
}

