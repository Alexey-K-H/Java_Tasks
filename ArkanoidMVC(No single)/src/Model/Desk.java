package Model;

import java.awt.geom.Rectangle2D;

//Desk
public class Desk{
    private double width;
    private double height;

    private double x;
    private double y;
    private double velocity;
    private String deskDirection = "stay";

    public void setX(double x) {
        this.x = x;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getVelocity() {
        return velocity;
    }

    public String getDeskDirection() {
        return deskDirection;
    }

    public void setDeskDirection(String _deskDirection) {
        deskDirection = _deskDirection;
    }

    public Rectangle2D.Double getShape(){
        return new Rectangle2D.Double(x, y, width, height);
    }

    public Desk(double _x, double _y){
        x = _x;
        y = _y;
        width = _x/4;
        height = _y/40;
        velocity = _x/70;
    }

}
