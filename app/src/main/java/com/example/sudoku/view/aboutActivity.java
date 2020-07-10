package com.example.sudoku.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.example.sudoku.R;

public class aboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) { //открытие окна с правилами игры, при нажатии на кнопку About.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
}