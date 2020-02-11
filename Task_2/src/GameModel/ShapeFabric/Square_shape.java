package GameModel.ShapeFabric;

import GameModel.Cube;

import java.awt.*;
import java.util.ArrayList;

public final class Square_shape extends ShapeBuilder{
    @Override
    public ArrayList<Cube> buildShape(Color color) {
        ArrayList<Cube> sqr_shape = new ArrayList<Cube>(4);
        sqr_shape.add(new Cube(0, 0, color));
        sqr_shape.add(new Cube(0, 1, color));
        sqr_shape.add(new Cube(1, 0, color));
        sqr_shape.add(new Cube(1, 1, color));
        return sqr_shape;
    }

    @Override
    public void rotateShape(ArrayList<Cube> shape, int angle) {
        switch (angle){
            case 0:
                rotate(shape, 0, 0, 0, 1, 1, 0, 1, 1);
                break;
            case 90:
                rotate(shape, 0, 1,1,1,0,0,1,0);
                break;
            case 180:
                rotate(shape, 1,1,1,0,0,1,0,0);
                break;
            case 270:
                rotate(shape, 1, 0, 0,0,1,1,0,1);
                break;
        }
    }
}
