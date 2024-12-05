package com.example.myapplication3;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ViewActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        // Инициализация DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        // Элемент TextView для отображения записей
        TextView recordsTextView = findViewById(R.id.recordsTextView);

        // Получение всех записей из базы данных
        Cursor cursor = dbHelper.getAllRecords();
        StringBuilder records = new StringBuilder();

        // Форматирование данных для отображения
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String fio = cursor.getString(1);
            String timestamp = cursor.getString(2);

            records.append("ID: ").append(id)
                    .append(", ФИО: ").append(fio)
                    .append(", Время: ").append(timestamp)
                    .append("\n");
        }

        // Закрытие курсора
        cursor.close();

        // Установка текста в TextView
        recordsTextView.setText(records.toString());
    }
}
