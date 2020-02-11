package GameModel;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public final class Field {
    //Набор цветов используемый для фигурок
    private final static Color[] colors = {Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE};
    //Различные значения задержек, которые задают скорость движения на конкретном уровне(пусть уровней 10)
    private final static int[] timer_delay = {1000, 900, 800, 700, 600, 500, 400, 300, 200, 100};
    //Таблица уровней (номер уровня - порог для его достижения)
    private final static HashMap<Integer, Integer> levelTable = new HashMap<Integer, Integer>();

    public final static int SHAPE_SIZE = 4;//Максимум 4 кубика на фигурку
    public final static int COLOR_COUNT = 5;//Количество цветов
    public final static int MAX_LEVEL = 10;
    public final static int ZERO = 0;

    //Набор кубиков для построения фигурок
    private final ArrayList<Cube> field;
    //Фигура, которая падает в данный момент
    private Shape currentShape;
    //Фигура, которая собирается упасть следующей
    private Shape nextShape;

    private int score;//Текущий рекорд игрока
    private int level;//Текущий уровень
    private int minY;//Минимальная высота строения из упавших фигур

    //Длинна и высота стакана
    private int width;
    private int height;

    public Field(int XSize, int YSize){
        assert (ZERO < XSize && ZERO < YSize);
        this.width = XSize;
        this.height = YSize;
        this.field = new ArrayList<Cube>();
        this.minY = 21;
        this.level = 1;
        this.score = 0;

        //Пороги уровней
        levelTable.put(1, 0);
        levelTable.put(2, 1000);
        levelTable.put(3, 3000);
        levelTable.put(4, 6000);
        levelTable.put(5, 10000);
        levelTable.put(6, 15000);
        levelTable.put(7, 21000);
        levelTable.put(8, 28000);
        levelTable.put(9, 35000);
        levelTable.put(10, 44000);
    }

    public int getLevelScore(int level){
        return levelTable.get(level);
    }

    public int getScore(){
        return score;
    }

    public void setScore(int value){
        score = value;
    }

    public int getTimerDelay_of_level(int curr_level)
    {
        assert (ZERO < level && MAX_LEVEL >= level);
        return timer_delay[level - 1];
    }

    public void setLevel(int curr_level)
    {
        assert (ZERO < curr_level && MAX_LEVEL >= curr_level);
        this.level = curr_level;
    }

    public int getMinY()
    {
        return minY;
    }

    public void setMinY(int curr_minY){
        this.minY = curr_minY;
    }


}
