package GameModel.ShapeFabric;

import GameModel.Cube;

import java.awt.*;
import java.util.ArrayList;

public final class T_shape extends ShapeBuilder{
    @Override
    public ArrayList<Cube> buildShape(Color color) {
        ArrayList<Cube> t_shape = new ArrayList<Cube>(4);
        t_shape.add(new Cube(0,0,color));
        t_shape.add(new Cube(0,1,color));
        t_shape.add(new Cube(0,2,color));
        t_shape.add(new Cube(1,1,color));
        return t_shape;
    }

    @Override
    public void rotateShape(ArrayList<Cube> shape, int angle) {
        switch (angle){
            case 0:
                rotate(shape, 0, 0, 0 ,1, 0, 2, 1, 1);
                break;
            case 90:
                rotate(shape, 0, 1, 1, 1, 2, 1, 1, 0);
                break;
            case 180:
                rotate(shape, 1, 2, 1, 1, 1, 0, 0, 1);
                break;
            case 270:
                rotate(shape, 2, 0, 1, 0, 0, 0, 1, 1);
                break;
        }
    }
}
