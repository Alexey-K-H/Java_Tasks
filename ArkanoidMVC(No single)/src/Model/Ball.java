package Model;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;

//Ball
public class Ball {
    private double x;
    private double y;
    private double diameter;

    private double []vector = new double[2];

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getDiameter() {
        return diameter;
    }

    public double getVector(int index) {
        return vector[index];
    }

    public void multipleVectorByNum(int index, int val){
        this.vector[index] *= val;
    }

    public void setVector(int index, double val) {
        this.vector[index] = val;
    }

    public Ball(double initX, double initY, double speed, double initDiameter){
        x = initX;
        y = initY;
        Random r = new Random();
        vector[0] = (-speed*Math.sqrt(2))*r.nextDouble();
        vector[1] = -Math.sqrt(speed*2*speed - vector[0]*vector[0]);
        diameter = initDiameter;
    }

    public double getSpeed(){
        return Math.sqrt(vector[0]*vector[0] + vector[1]*vector[1]);
    }

    public Ellipse2D.Double getShape(){
        return new Ellipse2D.Double(x, y, diameter, diameter);
    }

    public Rectangle2D.Double getAreaBall() {return new Rectangle2D.Double(x, y, diameter, diameter);}
}
