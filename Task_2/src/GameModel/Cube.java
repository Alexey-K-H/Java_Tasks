package GameModel;

import java.awt.*;

//Класс описывающий фрагмент (кубик) который используется для сохдания основных 7 фигур
public class Cube {
    //Цвет кубика
    private final Color color;

    //Его координаты на поле
    private int x;
    private int y;

    public Cube(int val_x, int val_y, Color curr_color)
    {
        assert (Field.ZERO <= val_x && Field.ZERO <= val_y && null != curr_color);
        this.x = val_x;
        this.y = val_y;
        this.color = curr_color;
    }

    public Color getColor() {
        return color;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        assert (Field.ZERO <= x);
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        assert (Field.ZERO <= y);
        this.y = y;
    }
}
