package Model;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public class Model {
    private static Model model;
    private Dimension dimension;

    private Ball ball;
    private Desk desk;
    private Bricks bricks;

    public static Model getInstance(){
        if(model == null){
            model = new Model();
        }
        return model;
    }

    public void setDimension(Dimension d){
        dimension = d;
    }

    public void init(){
        ball = new Ball((double) 2 * dimension.width/3, (double) 2*dimension.height/5, 1, (double)dimension.height/50);
        desk = new Desk((double)dimension.width/2, (double)(9*dimension.height)/10);
        bricks = new Bricks(dimension.width, (double)dimension.height/4);
    }

    public Dimension getDimension(){
        return new Dimension(dimension);
    }

    //Ball
    public static class Ball{
        private static double x;
        private static double y;
        private static double diameter;


        private static double []vector = new double[2];

        private Ball(double initX, double initY, double speed, double initDiameter){
            x = initX;
            y = initY;
            Random r = new Random();
            vector[0] = (-speed*Math.sqrt(2))*r.nextDouble();
            vector[1] = -Math.sqrt(speed*2*speed - vector[0]*vector[0]);
            Ball.diameter = initDiameter;
        }

        public static void moveBall(int keyPressed){
            if(y >= Model.getInstance().getDimension().height - diameter || !Bricks.existBricks()){
                vector[0] = 0;
                vector[1] = 0;
            }
            else{
                double eps = getSpeed();

                if(x >= Model.getInstance().getDimension().width - diameter || x <= diameter){
                    vector[0] *= (-1);
                }

                if(y <= diameter){
                    vector[1] *= (-1);
                }

                if(intersectDesk()){
                    if(y + diameter - eps <= Desk.y){
                        if(keyPressed == KeyEvent.VK_LEFT){
                            if(vector[0] >= 0){
                                vector[0] -= 0.5;
                            }
                            else{
                                vector[0] += 0.5;
                            }
                        }
                        else if(keyPressed == KeyEvent.VK_RIGHT){
                            if(vector[0] < 0){
                                vector[0] -= 0.5;
                            }
                            else
                                vector[0] += 0.5;
                        }
                        vector[1] = Math.sqrt(eps * eps - vector[0] * vector[0]);
                        vector[1] *= (-1);
                        if((x - Desk.x + diameter/2 <= Desk.width / 2 && vector[0] > 0) ||
                                (x - Desk.x + diameter/2 > Desk.width / 2 && vector[0] < 0))
                            vector[0] *= (-1);
                    }
                    else if(y - diameter/2 - eps < Desk.y + Desk.height){
                        double shift = 20;
                        if(x - Desk.x <= Desk.width / 2 && x + diameter - Desk.x >= 0)
                            x -= shift;
                        else if(x - Desk.x < Desk.width)
                            x += shift;
                        vector[0] *= (-1);
                    }
                }

                if(y - eps <= (double) Model.getInstance().getDimension().height / 3){
                    int removeBrick;
                    if((removeBrick = intersectBricks()) != -1){
                        Bricks.removeBrick(removeBrick);
                        if(y + eps >= Bricks.intersectMaxY || y - eps <= Bricks.intersectMinY - diameter)
                            vector[1] *= (-1);
                        else
                            vector[0] *= (-1);
                    }
                }
                x += vector[0];
                y += vector[1];
            }
        }

        private static boolean intersectDesk(){
            return getShape().intersects(Desk.getShape());
        }

        private static int intersectBricks(){
            Rectangle2D.Double brick;
            for(int i = 0; i < 80; i++){
                brick = Bricks.getShape(i);
                if(brick!= null && getShape().intersects(brick)){
                    return i;
                }
            }
            return -1;
        }

        public static double getSpeed(){
            return Math.sqrt(vector[0]*vector[0] + vector[1]*vector[1]);
        }

        public static Ellipse2D.Double getShape(){
            return new Ellipse2D.Double(x, y, diameter, diameter);
        }
    }

    //Desk
    public static class Desk{
        private static double width;
        private static double height;

        private static double x;
        private static double y;
        private static double velocity;

        public static Rectangle2D.Double getShape(){
            return new Rectangle2D.Double(x, y, width, height);
        }

        private Desk(double _x, double _y){
            Desk.x = _x;
            Desk.y = _y;
            width = _x/4;
            height = _y/40;
            velocity = _x/60;
        }

        public static void moveLeft(){
            if(!( x-velocity < 0)){
                x -= velocity;
            }
            else{
                x = 0;
            }
        }

        public static void moveRight(){
            if(!(x+velocity+width > Model.getInstance().getDimension().width))
                x+=velocity;
            else
                x = Model.getInstance().getDimension().width - width;
        }
    }

    //Bricks
    public static class Bricks{
        private static Rectangle2D.Double []bricks = new Rectangle2D.Double[80];
        private static double width;
        private static double height;
        private static double intersectMaxY;
        private static double intersectMinY;

        private Bricks(double _width, double _height){
            double shiftX = 0;
            double shiftY = 0;

            double eps = _width/100;
            Bricks.width = _width/10;
            Bricks.height = _height/8;

            for(int i = 0; i < 80; i++){
                bricks[i] = new Rectangle2D.Double(shiftX, shiftY, Bricks.width, Bricks.height);
                if(shiftX+eps+Bricks.width >= _width){
                    shiftX = 0;
                    shiftY += Bricks.height;
                }
                else
                    shiftX += Bricks.width;
            }
        }

        public static Rectangle2D.Double getShape(int index){
            if(bricks[index]!=null){
                return new Rectangle2D.Double(bricks[index].x, bricks[index].y, bricks[index].width, bricks[index].height);
            }
            return null;
        }

        private static void removeBrick(int index){
            intersectMaxY = bricks[index].getMaxY();
            intersectMinY = bricks[index].getMinY();
            bricks[index] = null;
        }

        public static boolean existBricks(){
            for(int i = 0; i < 80; i++){
                if(bricks[i] != null)
                    return true;
            }
            return false;
        }

        public static int getAmountBricks(){
            int counter = 0;
            for(int i = 0; i < 80; i ++){
                if(bricks[i] == null)
                    counter++;
            }
            return counter;
        }

    }
}
