package com.example.sudoku.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.sudoku.R;

public class MainActivity extends Activity implements OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //открытие первого главного окна с кнопками Start About Exit
        findViewsById(); //инициализация всех кнопок и присваивание им их Id отдельным методом

    }

    public void findViewsById(){
        View buttonStart = findViewById(R.id.buttonStart); //соответствие кнопкам их Id
        buttonStart.setOnClickListener(this);
        View buttonAbout = findViewById(R.id.buttonAbout);
        buttonAbout.setOnClickListener(this);
        View buttonExit = findViewById(R.id.buttonExit);
        buttonExit.setOnClickListener(this);
    }

    public void onClick(View v) { // метод для нажатий на кнопки на главном окне
        if (v.getId() == R.id.buttonAbout) {//
            Intent about = new Intent(this, aboutActivity.class); //осуществляется переход в класс aboutActivity
            startActivity(about); //отображение окна about.xml
        } else if (v.getId() == R.id.buttonStart) {
            windowChoseLevel(); // открытие окошка с возможностью выбрать уровень сложности пользователю (смотри метод ниже)
        } else if (v.getId() == R.id.buttonExit) { //при нажатии на Exit осуществляется выход из приложения
            finish(); //закрытие приложения
        }
    }
    private void windowChoseLevel() { //открытие нового окна с возможностю выбора уровня сложности
        new AlertDialog.Builder(this) //создание нового диалогового окна
                .setItems(R.array.difficulty, new DialogInterface.OnClickListener() { //это диалоговое окно включает в себя 2 вида
                                                                                     // уровня сложности, с возможностью нажатия, то бишь выбора
                    public void onClick(DialogInterface dialoginterface, int i) {
                        startGame(i); //запуск метода, позволяющегооткрыть игровое поле
                    }
                }).show();//отображение после его запуска
    }

    private void startGame(int i) { //метод, который открывает игровое поле, с выбранным уровнем сложности
        Intent intent = new Intent(MainActivity.this, GameActivity.class); //создание окна с отсылкой к классу GameActivity,
                                                                                        // где прописывается  логика
        intent.putExtra(GameActivity.level, i);//инициализация сложночти поля
        startActivity(intent);//старт (открытие) окна содержимого
    }



}