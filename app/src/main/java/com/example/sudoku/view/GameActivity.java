package com.example.sudoku.view;


import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.example.sudoku.R;
import com.example.sudoku.keypad;


public class GameActivity extends Activity {


    public static final String level = "";
    public static final int easy1 = 0; //значение инта для первого поля
    public static final int easy2 = 1; //значение инта для второго поля
    public static int row = 9; //количество строк
    public static int column = 9; // количество строк
    private int[] puzzle = new int[row * column]; //массив  с размером 9 на 9
    private puzzleView puzzleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int sudokuLevel = getIntent().getIntExtra(level,easy2);
        puzzle = gettingThePuzzle(sudokuLevel); //присваивание puzzle в виде массива уровень который был выбран в методе gettingThePuzzle
        usedCells();
        puzzleView = new puzzleView(this);
        setContentView(puzzleView);
        puzzleView.requestFocus();
    }


    private int[] gettingThePuzzle(int sudokuLevel) { //определение уровня сложности и самого судоку и преобразование его в вид массива
        String puzzle;
        String easyFirst = "180507209352061478900328506" + //первое судоку легкого уровня
                "030004760527609300641702905" +
                "413296057798410623205070000";
        String easySecond = "650000070000506000014000005" + //второе судоку легкого уровня
                "007009000002314700000700800" +
                "500000630000201000030000097";
        if (sudokuLevel == easy1) { puzzle = easyFirst; //при выборе первого уровня сложности, присваиваем puzzle первую строку
        } else { puzzle = easySecond;}//иначе присваиваем puzzle вторую строку
        return getAnMassivOfSudoku(puzzle); //преобразование в массив
    }


    static protected int[] getAnMassivOfSudoku(String string) { //преображение строку значений судоку в массив
        int[] puzzle = new int[string.length()]; //инициализация массива с размером равным размеру массивов easyFirst и easySecond
        for (int i = 0; i < puzzle.length; i++)
            puzzle[i] = string.charAt(i) - '0'; //если встречается "0", оно превращается в "", то бишь в пустую клетку
        return puzzle; //возвращение чисел в судоку в виде массива
    }

    protected String getTileString(int x, int y) { //по данным координатам  преобразовываем значение клетки в формат String
        int v = gettingCellValue(x, y); //получение по координатам значения в данной клетке
        if (v == 0)  return ""; //если значение равно 0, присваиваем пустое значение клетке
        else return String.valueOf(v); //иначе преобразовываем значение в формат String
    }


    protected boolean setTileIfValid(int x, int y, int value) { //введение значения если неправильное значение
        int[] tiles = getUsedTiles(x, y); //массив использованных клеток по координате
        if (value != 0) {
            for (int tile : tiles) {
                if (tile == value)
                    return false; //возврат false если введено значение которое уже есть в массиве использованных значений
            }
        }
        setCellValue(x, y, value); // иначе присваивание клетке значение value
        usedCells();//вычисление массива использованных клеток
        return true;
    }

    protected void showKeypadOrError(int x, int y) {
        int[] cells = getUsedTiles(x, y);
        if (cells.length == 9) { //если использованы все значения в клетках, то появляется окно, что вставлять ничего не нужно
            Toast toast = Toast.makeText(this,
                    R.string.no_moves_label, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0); //расположение окошка по середине
            toast.show(); //показ данного окна пользователю
        } else {
            Dialog v = new keypad(this, cells, puzzleView); //иначе открытие окна с возможностью ввести число в клетку
            v.show(); //показ данного окна пользователю
        }
    }

    protected int[] getUsedTiles(int x, int y) { //возврат используемых клеток судя по координатам
        return used[x][y];
    }

    private final int[][][] used = new int[9][9][]; //трехмерный массив с использованными клетками

    private void usedCells() { //вычисление массива использованных клеток
        for (int x = 0; x < row; x++) {
            int y = 0;
            while (y < column) {
                used[x][y] = usedCells(x, y);
                y++;
            }
        }
    }


    private int[] usedCells(int x, int y) {
        int[] massive = new int[row]; //создаем пустой массив в котором будут значения которые уже используются
        for (int i = 0; i < row; i++) { //проход по строкам судоку
            if (i == y) continue;
            int t = gettingCellValue(x, i); // присваивание значению t значение клетки по координатам x, i
            if (t != 0) massive[t - 1] = t; //если это значение не равно 0, то добавляем это значение в массив, с индексом "значение - 1"
        }
        for (int i = 0; i < column; i++) { //проход по колоннам судоку
            if (i == x) continue;
            int t = gettingCellValue(i, y); // присваивание значению t значение клетки по координатам i, y
            if (t != 0) massive[t - 1] = t; //если это значение не равно 0, то добавляем это значение в массив, с индексом "значение - 1"
        }
        int startx = (x / 3) * 3; // стартовая клетка в каждой тройке каждой строки
        int starty = (y / 3) * 3; //стартовая клетка в каждой тройке каждой колонны
        for (int i = startx; i < startx + 3; i++) { //проходим массивом в данном квадрате 3 на 3
            for (int j = starty; j < starty + 3; j++) {
                if (i == x && j == y) continue;
                int t = gettingCellValue(i, j); // присваивание значению t значение клетки по координатам i, j
                if (t != 0) massive[t - 1] = t; //если клетка не равна 0 то присваиваем массиву с индексом "значение - 1" значение t
            }
        }
        int array = 0;
        for (int t : massive) {
            if (t != 0) array++; //если элемент в массиве не равен 0, инкрементируем array
        }
        int[] massiv1 = new int[array]; //создание нового массивас размером, равным полученному array
        array = 0; //обнуляем данную переменную
        for (int t : massive) {
            if (t != 0) massiv1[array++] = t;
        }
        return massiv1;
    }

    private int gettingCellValue(int x, int y) { //метод возвращающий значение клетки по ее координатам
        return puzzle[y * 9 + x];
    }

    private void setCellValue(int x, int y, int digit) { //присваивание определенной клетке значения digit
        puzzle[y * 9 + x] = digit;
    }
}