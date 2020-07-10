package com.example.sudoku.view.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.example.sudoku.R;
import com.example.sudoku.view.GameActivity;


import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class puzzleView extends View{

    private static float size = 9;
public GameActivity gameActivity;

    private puzzleView.OnTouchListener listener = null;
    private static float cellsSizePixels = 0F;
    private Paint mod3lines = new Paint();
    private Paint lines = new Paint();
    private Paint background = new Paint();
    private int selectedRow = 0;
    private int selectedColumn = 0;
    private Paint colorSelectedCell = new Paint();
    private Paint colorDigit = new Paint();

    public Button b1, b2, b3, b4, b5, b6, b7, b8, b9, bC, bNext;


    public puzzleView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);


    }


    public void onMeasure(int width, int height){
        super.onMeasure(width, height);
        int sizePixels = Math.min(width, height);
        setMeasuredDimension(sizePixels, sizePixels);

    }

    @Override
    public void onDraw(Canvas canvas){
        cellsSizePixels = (Float) (getWidth()/ size);

        drawLines(canvas);
        selectOnlyEmptyCells(canvas);
        digitCells(canvas);
        if (5 > 1) digit(canvas, "  1");



    }


    public void digit(Canvas canvas, String text){
        int k = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals("  0")) {
                if ((selectedRow == i / 9) && (selectedColumn == i % 9) ) {
                    dig(text, canvas, selectedRow, selectedColumn, colorSelectedCell);
                    k++;

                }
            }

        }

    }
    public void dig(String text, Canvas canvas, int row, int column, Paint paint){
        canvas.drawText(text, (column  )* cellsSizePixels, (row +1 )* cellsSizePixels, colorDigit);

    }


    String[] array =  {"  5","  9","  3","  1","  0","  0","  0","  2","  0",
            "  0","  2","  0","  7","  0","  5","  3","  4","  9",
            "  8","  7","  0","  0","  3","  2","  0","  0","  0",
            "  1","  0","  6","  8","  0","  3","  0","  9","  0",
            "  9","  3","  0","  4","  0","  0","  8","  5","  7",
            "  4","  0","  0","  2","  5","  0","  1","  3","  0",
            "  0","  1","  9","  3","  4","  0","  6","  0","  5",
            "  3","  6","  8","  0","  9","  0","  2","  7","  4",
            "  7","  4","  5","  6","  2","  8","  9","  0","  0"};





    public void drawLines(Canvas canvas) { // TUT VSE OK!!!!!!!!!!!
        background.setColor(getResources().getColor(R.color.colorwhite));
        canvas.drawRect(0F, 0F, getWidth(),getHeight(), background);
        lines.setColor(getResources().getColor(R.color.colorgrey));
        lines.setStrokeWidth(2F);
        mod3lines.setColor(getResources().getColor(R.color.colorblack));
        mod3lines.setStrokeWidth(5F);
        for (int i = 0; i < size + 1; i++) {
            Paint paintToUse;
            if (i % 3 == 0) {
                paintToUse = mod3lines;
            } else {
                paintToUse = lines;
            }
            canvas.drawLine(i*cellsSizePixels, 0F, i*cellsSizePixels, getHeight(), paintToUse);
            canvas.drawLine(0F, i*cellsSizePixels, getWidth(), i*cellsSizePixels, paintToUse);
        }



    }
    ArrayList<Integer> listRows = new ArrayList<>();
    ArrayList<Integer> listCols = new ArrayList<>();




    public void selectOnlyEmptyCells(Canvas canvas){

        if (selectedColumn == -1 && selectedRow == -1) return;

        colorSelectedCell.setColor(getResources().getColor(R.color.colorlightgrey));
        colorSelectedCell.setStyle(Paint.Style.FILL_AND_STROKE);
        colorSelectedCell.setAlpha(133);
        int k = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals("  0")) {
                if ((selectedRow == i / 9) && (selectedColumn == i % 9) ) {
                    coloringCell(canvas, selectedRow, selectedColumn, colorSelectedCell);
                    k++;

                }
            }

        }

    }
    public void coloringCell(Canvas canvas, int row, int column, Paint paint){
        canvas.drawRect(column * cellsSizePixels, row * cellsSizePixels,
                (column + 1) * cellsSizePixels, (row + 1) * cellsSizePixels, paint);

    }



    public void digitCells(Canvas canvas) {
        int k = 0;
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size ; column++) {
                digitingCell(canvas, row, column, colorDigit, k);
                k++;

            }
        }
    }
    public void digitingCell(Canvas canvas, int row, int column, Paint paint, int k){
        int fontsize = 60;
        colorDigit.setColor(getResources().getColor(R.color.colordarkgreen));
        colorDigit.setTextSize(fontsize);
        if (array[k].equals("  0")) canvas.drawText("", (column  )* cellsSizePixels, (row +1 )* cellsSizePixels, colorDigit);

        else {
            canvas.drawText(array[k], (column) * cellsSizePixels, (row + 1) * cellsSizePixels, colorDigit);

        }
        canvas.translate(0F, -0.35F); // смещение по вертикали, чтобы каждая циферка была по серединке
    }


    public void handleTouchEvent(float x, float y){
        selectedRow = (int) (y / cellsSizePixels);
        selectedColumn = (int) (x / cellsSizePixels);
        invalidate();

    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event){
        if (MotionEvent.ACTION_DOWN == event.getAction()) {
            handleTouchEvent(event.getX(), event.getY());
            return true;
        }
        return false;
    }






}
