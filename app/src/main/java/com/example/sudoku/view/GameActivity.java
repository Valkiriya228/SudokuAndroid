package com.example.sudoku.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Chronometer;
import android.graphics.Canvas;
import android.widget.GridView;
import android.widget.Toast;

import com.example.sudoku.R;
import com.example.sudoku.view.custom.puzzleView;


public class GameActivity extends AppCompatActivity implements View.OnClickListener  {


    private boolean go;
    private long timePause;
    private ConstraintLayout layout;
    private Chronometer chronometer1;
    public Button b1, b2, b3, b4, b5, b6, b7, b8, b9, bC, bNext;

    private Paint colorDigit = new Paint();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        getItems();

    }

    String[] digits =  {"  1","  2","  3","  4","  5","  6","  7","  8","  9"};
    String[] array =  {"  5","  9","  3","  1","  0","  0","  0","  2","  0",
            "  0","  2","  0","  7","  0","  5","  3","  4","  9",
            "  8","  7","  0","  0","  3","  2","  0","  0","  0",
            "  1","  0","  6","  8","  0","  3","  0","  9","  0",
            "  9","  3","  0","  4","  0","  0","  8","  5","  7",
            "  4","  0","  0","  2","  5","  0","  1","  3","  0",
            "  0","  1","  9","  3","  4","  0","  6","  0","  5",
            "  3","  6","  8","  0","  9","  0","  2","  7","  4",
            "  7","  4","  5","  6","  2","  8","  9","  0","  0"};

    Canvas canvas;
    puzzleView puzzleView;
    private int selectedRow = 0;
    private int selectedColumn = 0;
    private final static float cellsSizePixels = 0F;


    @Override
    public void onClick(View v) {
        int fontsize = 60;
        colorDigit.setColor(getResources().getColor(R.color.colordarkgreen));
        colorDigit.setTextSize(fontsize);
        b1 = findViewById(R.id.button1);
        b2 = findViewById(R.id.button2);
        b3 = findViewById(R.id.button3);
        b4 = findViewById(R.id.button4);
        b5 = findViewById(R.id.button5);
        b6 = findViewById(R.id.button6);
        b7 = findViewById(R.id.button7);
        b8 = findViewById(R.id.button8);
        b9 = findViewById(R.id.button9);
        bC = findViewById(R.id.buttonC);
        bNext = findViewById(R.id.buttonNext);

        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
        b5.setOnClickListener(this);
        b6.setOnClickListener(this);
        b7.setOnClickListener(this);
        b8.setOnClickListener(this);
        b9.setOnClickListener(this);
        bC.setOnClickListener(this);
        bNext.setOnClickListener(this);

        if (v.getId() == R.id.button1) {




        }

    }




    private void getItems(){
        chronometer1 = findViewById(R.id.chronometer1);
    }


    public void startChronometer(View view) {
        if (!go) {
            chronometer1.setBase(SystemClock.elapsedRealtime() - timePause);
            chronometer1.start();
            go = true;
        }
    }
    public void stopChronometer(View view) {
        chronometer1.setBase(SystemClock.elapsedRealtime());
        timePause = 0;
    }
    public void pauseChronometer(View view) {
        if (go) {
            chronometer1.stop();
            timePause = SystemClock.elapsedRealtime() -chronometer1.getBase();
            go = false;
        }
    }



}