package GameModel.ShapeFabric;

import GameModel.Cube;

import java.awt.*;
import java.util.ArrayList;

public final class I_shape extends ShapeBuilder{
    @Override
    public ArrayList<Cube> buildShape(Color color) {
        ArrayList<Cube> i_shape = new ArrayList<Cube>(4);
        i_shape.add(new Cube(0,0,color));
        i_shape.add(new Cube(0,1,color));
        i_shape.add(new Cube(0,2,color));
        i_shape.add(new Cube(0,3,color));
        return i_shape;
    }

    @Override
    public void rotateShape(ArrayList<Cube> shape, int angle) {
        switch (angle){
            case 0:
                rotate(shape, 0,0,0,1,0,2,0,3);
                break;
            case 90:
                rotate(shape, 0,0,1,0,2,0,3,0);
                break;
            case 180:
                rotate(shape, 0,3,0,2,0,1,0,0);
                break;
            case 270:
                rotate(shape, 3,0,2,0,1,0,0,0);
                break;
        }
    }
}
