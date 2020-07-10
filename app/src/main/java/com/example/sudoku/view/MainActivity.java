package com.example.sudoku.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import com.example.sudoku.R;
import com.example.sudoku.view.GameActivity;
import com.example.sudoku.view.aboutActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        findViewById(R.id.buttonStart).setOnClickListener(this);
        findViewById(R.id.buttonAbout).setOnClickListener(this);
        findViewById(R.id.buttonExit).setOnClickListener(this);
    }
    @Override
    public void onClick(View v) { //Обработка переходов между окнами при нажатии на кнопки Start, About, Exit.
        if (v.getId() == R.id.buttonAbout) { //Переход к правилам игры, для тех, кто их не знает.
            Intent intent = new Intent(this, aboutActivity.class);
            startActivity(intent);
        }
        if (v.getId() == R.id.buttonStart) { //Переход к окну с первым полем судоку
            Intent intent = new Intent(this, GameActivity.class);
            startActivity(intent);
        }
        if (v.getId() == R.id.buttonExit) finish(); // выход из приложения
    }
}