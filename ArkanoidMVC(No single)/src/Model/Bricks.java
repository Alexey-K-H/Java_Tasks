package Model;

import java.awt.geom.Rectangle2D;

//Bricks
public class Bricks{
    private Rectangle2D.Double []bricks = new Rectangle2D.Double[100];
    private double intersectMaxY;
    private double intersectMinY;

    public double getIntersectMaxY() {
        return intersectMaxY;
    }

    public double getIntersectMinY() {
        return intersectMinY;
    }

    public Bricks(double _width, double _height){
        double shiftX = 0;
        double shiftY = 0;

        double eps = _width/100;
        double width = _width / 10;
        double height = _height / 8;

        for(int i = 0; i < 100; i++){
            bricks[i] = new Rectangle2D.Double(shiftX, shiftY, width, height);
            if(shiftX+eps+ width >= _width){
                shiftX = 0;
                shiftY += height;
            }
            else{
                shiftX += width;
            }
        }
    }

    public Rectangle2D.Double getShape(int index){
        if(bricks[index]!=null){
            return new Rectangle2D.Double(bricks[index].x, bricks[index].y, bricks[index].width, bricks[index].height);
        }
        return null;
    }

    public void removeBrick(int index){
        intersectMaxY = bricks[index].getMaxY();
        intersectMinY = bricks[index].getMinY();
        bricks[index] = null;
    }

    public boolean existBricks(){
        for(int i = 0; i < 100; i++){
            if(bricks[i] != null)
                return true;
        }
        return false;
    }

    public int getAmountBricks(){
        int counter = 0;
        for(int i = 0; i < 100; i ++){
            if(bricks[i] == null)
                counter++;
        }
        return counter;
    }

}
