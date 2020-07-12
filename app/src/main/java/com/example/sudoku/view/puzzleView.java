package com.example.sudoku.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.sudoku.R;

public class puzzleView extends View {
    private static final String TAG = "Sudoku";
    private final GameActivity game;
    private float width; // ширина одной клетки судоку
    private float height; // высота одной клетки судоку
    private int selX; // координата Х выделенной клетки
    private int selY; // координата У выделенной клетки
    private final Rect selRect = new Rect();

    public puzzleView(Context context) {
        super(context);
        this.game = (GameActivity) context;
        setFocusableInTouchMode(true); // функция используется для включения просмотра фокуса в режиме сенсорного режима.
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) { //инициализация рамеров клетки и рисование пустого прямоугольника через getRect
        width = w / 9f; //деление всего экрана на 9 столбиков с шириной width
        height = h / 9f; //деление экрана на 9 строк с высотой height
        getRect(selX, selY, selRect); //переход к методу getRect для отрисовки прямоугольника
        super.onSizeChanged(w, h, oldw, oldh);
    }

    private void getRect(int x, int y, Rect rect) { //создаем пустой прямоугольник и задаем координаты точек
        rect.set((int) (x * width), (int) (y * height), (int) (x * width + width), (int) (y * height + height));
    }
    //инициализация цветов для метода onDraw (отрисовка)
    private  Paint lines = new Paint(); //отрисовка обычных линий
    private Paint linesDiv3 = new Paint(); //отрисовка линий, разделяющих поле на 9 квадратов 3 на 3
    private Paint background = new Paint(); //инициализация цвета фона поля
    private Paint foreground = new Paint(Paint.ANTI_ALIAS_FLAG);//отрисовка цифр, вводимых в ячейку
   private Paint selected = new Paint();//инициализация цвета выделенной ячейки
    @Override
    protected void onDraw(Canvas canvas) { //метод с отрисовкой границ, столбцов, строк, выделения ячейки
        background.setColor(getResources().getColor(R.color.colorwhite)); //присваивание белого цвета фону поля
        canvas.drawRect(0, 0, getWidth(), getHeight(), background); //закрашивание белым цветом всего поля по координатам

        lines.setColor(getResources().getColor(R.color.colorlightblack)); //присваивание черного цвета обычным линиям
        lines.setStrokeWidth(1F);

        linesDiv3.setColor(getResources().getColor(R.color.colorblack));//присваивание черного цвета линиямДив3
        linesDiv3.setStrokeWidth(5F);//присваивание данным линиям толщины, для различия с отсальными линиями
        //отрисовка всех строк и столбцов обычным черным
        for (int i = 0; i < 10; i++) {
            canvas.drawLine(0, i * height, getWidth(), i * height, lines);
            canvas.drawLine(0, i * height + 1, getWidth(), i * height + 1, lines);
            canvas.drawLine(i * width, 0, i * width, getHeight(), lines);
            canvas.drawLine(i * width + 1, 0, i * width + 1, getHeight(), lines);
        }
        //проход по полю и отрисовка жирным черным цветом только каждую третью строку и столбец
        for (int i = 0; i < 10; i++) {
            if (i % 3 != 0) //проверка условия, третья ли это линия
                continue;
            canvas.drawLine(0, i * height, getWidth(), i * height, linesDiv3);
            canvas.drawLine(0, i * height + 1, getWidth(), i * height + 1, linesDiv3);
            canvas.drawLine(i * width, 0, i * width, getHeight(), linesDiv3);
            canvas.drawLine(i * width + 1, 0, i * width + 1, getHeight(), linesDiv3);
        }
        //отрисовка цифр и присваивание вводимо

        foreground.setColor(getResources().getColor(R.color.colorblack));//присваивание черного цвета цвету текста (цифр)
        foreground.setStyle(Style.FILL);//присваивания стиля шрифта
        foreground.setTextSize(height * 0.75f); //размер цифры в ячейке
        foreground.setTextScaleX(width / height);//позволяет растянуть/сжать текст
        foreground.setTextAlign(Paint.Align.CENTER);//расположение текста по центру ячейки
        // отрисовка цифры в центре ячейки
        FontMetrics fm = foreground.getFontMetrics();
        // центровка по координате Х
        float x = width / 2;
        // центровка по У
        float y = height / 2 - (fm.ascent + fm.descent) / 2;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                canvas.drawText(this.game.getTileString(i, j), i * width + x, j * height + y, foreground);
            }
        }
        selected.setColor(getResources().getColor(R.color.colorlightgrey)); //инициализация цвета для выделенной пользователем ячейки
        selected.setAlpha(127);//присваивание цвету выделения прозрачности, чтобы видеть цифру, находящуюся в выделенной ячейке
        canvas.drawRect(selRect, selected);//отрисовка прямоугольника(квадрата) размером с ячейку и залитие цветом, выбранным выше
    }

    private void select(int x, int y) {//новые координаты + вычисление новой области выделения
        invalidate(selRect);
        selX = Math.min(Math.max(x, 0), 8);//вычисляем новые координаты х и у выделения
        selY = Math.min(Math.max(y, 0), 8);
        getRect(selX, selY, selRect);//вычисление нового прямоугольника выделения
        invalidate(selRect);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() != MotionEvent.ACTION_DOWN)
            return super.onTouchEvent(event);
        select((int) (event.getX() / width), (int) (event.getY() / height));
        game.showKeypadOrError(selX, selY);
        return true;
    }

    public void setSelectedTile(int tile) { //изменение числа в ячейке
        if (game.setTileIfValid(selX, selY, tile)) {
            invalidate();// можно изменить подсказки
        } else {
            //число не подходит для данной ячейки
            Log.d(TAG, "setSelectedTile: invalid: " + tile);

        }
    }
}

