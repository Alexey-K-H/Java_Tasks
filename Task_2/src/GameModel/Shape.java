package GameModel;

import GameModel.ShapeFabric.Builder_Selector;

import java.awt.*;
import java.util.ArrayList;

public class Shape {
    private final static int rotate_angle = 90;

    private final ArrayList<Cube> shape;
    private final Builder_Selector builderSelector;
    private final Shape_Kind shapeKind;
    private int angle;
    private final Color color;
    private int xCoord;
    private int yCoord;

    public Shape(Builder_Selector builder_selector, Shape_Kind shape_kind, Color curr_color, int angle)
    {
        this.shape = builder_selector.getBuilder(shape_kind).buildShape(curr_color);
        this.builderSelector = builder_selector;
        this.shapeKind = shape_kind;
        this.color = curr_color;
        this.angle = angle;
        this.xCoord = 0;
        this.yCoord = 0;
        builderSelector.getBuilder(shapeKind).rotateShape(shape, angle);
    }

    public int getxCoord()
    {
        return xCoord;
    }

    public int getyCoord()
    {
        return yCoord;
    }

    public void setxCoord(int x_Coord) {
        this.xCoord = x_Coord;
    }

    public void setyCoord(int y_Coord){
        this.yCoord = y_Coord;
    }

    public Color getColor()
    {
        return color;
    }

    public Cube getCube(int index){
        return shape.get(index);
    }

    public void rotate()
    {
        this.angle = (this.angle + rotate_angle)%360;
        builderSelector.getBuilder(shapeKind).rotateShape(shape, this.angle);
    }
}
