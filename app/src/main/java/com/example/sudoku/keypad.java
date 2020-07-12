package com.example.sudoku;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.example.sudoku.view.puzzleView;

public class keypad extends Dialog {

    private final View[] keys = new View[9]; //массив содержащий все кнопки от 1 до 9
    private View keypad;
    private final int[] useds; //массив использованных цифр
    private final puzzleView puzzleView;

    public keypad(Context context, int[] useds, com.example.sudoku.view.puzzleView puzzleView) {
        super(context);
        this.useds = useds;
        this.puzzleView = puzzleView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.keypad_title);
        setContentView(R.layout.keypad); //открытие окна с цифрами
        findViewsById(); //метод с присваиванием id
        for (int element : useds) {
            if (element != 0)
                keys[element - 1].setVisibility(View.INVISIBLE); //кнопка скрывается, и пользователь ее не видит
        }
        setListeners();//устанавливает обработчики для каждой кнопки
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) { //метод нажатий на кнопки
        int tile = 0;
        switch (keyCode) { //при нажатии на какую либо из кнопок ей присваиваются ее значение которое пользователь хочет ввести в ячейку
            case KeyEvent.KEYCODE_1:     tile = 1; break;
            case KeyEvent.KEYCODE_2:     tile = 2; break;
            case KeyEvent.KEYCODE_3:     tile = 3; break;
            case KeyEvent.KEYCODE_4:     tile = 4; break;
            case KeyEvent.KEYCODE_5:     tile = 5; break;
            case KeyEvent.KEYCODE_6:     tile = 6; break;
            case KeyEvent.KEYCODE_7:     tile = 7; break;
            case KeyEvent.KEYCODE_8:     tile = 8; break;
            case KeyEvent.KEYCODE_9:     tile = 9; break;
            default:
                return super.onKeyDown(keyCode, event);
        }
        if (isValid(tile)) returnResult(tile);
        return true;
    }

    private void returnResult(int tile) { //возврат выбранной цифры
        puzzleView.setSelectedTile(tile);
        dismiss();
    }
    private boolean isValid(int tile) { //метод проверяющий подходит ли данная цифра для этой ячейки
        for (int t : useds) {
            if (tile == t) return false;
        }
        return true;
    }

    private void findViewsById() { //присваиваение каждой кнопке и окну с цифрами его id
        keypad = findViewById(R.id.keypad);
        keys[0] = findViewById(R.id.keypad_1);
        keys[1] = findViewById(R.id.keypad_2);
        keys[2] = findViewById(R.id.keypad_3);
        keys[3] = findViewById(R.id.keypad_4);
        keys[4] = findViewById(R.id.keypad_5);
        keys[5] = findViewById(R.id.keypad_6);
        keys[6] = findViewById(R.id.keypad_7);
        keys[7] = findViewById(R.id.keypad_8);
        keys[8] = findViewById(R.id.keypad_9);
    }

    private void setListeners() { //установка обработчиков для каждой из кнопки
        for (int i = 0; i < keys.length; i++) {
            final int t = i + 1;
            keys[i].setOnClickListener(new View.OnClickListener(){
                public void onClick(View v) {
                    returnResult(t);
                }});
        }
        keypad.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                returnResult(0);
            }});
    }
}