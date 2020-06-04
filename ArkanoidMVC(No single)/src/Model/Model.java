package Model;

import Observe.ArkanoidObservable;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Timer;
import java.util.TimerTask;

public class Model extends ArkanoidObservable{
    private Dimension dimension;
    private Timer timer;
    private TimerTask timerTask;
    private TimerTask cycleTask;

    private Ball ball;
    private Desk desk;
    private Bricks bricks;

    private int time;

    public void increaseTime(){
        time++;
    }

    public int getTime(){
        return time;
    }

    public void resetTime(){
        time = 0;
    }

    public Ball getBall() {
        return ball;
    }

    public Desk getDesk() {
        return desk;
    }

    public Bricks getBricks() {
        return bricks;
    }

    public void setDimension(Dimension d){
        dimension = d;
    }

    public void init(){
        ball = new Ball((double) 2 * dimension.width/3, (double) 2*dimension.height/5, 2, (double)dimension.height/50);
        desk = new Desk((double)dimension.width/2, (double)(9*(dimension.height - 30))/10);
        bricks = new Bricks(dimension.width, (double)dimension.height/4);
        timer = new Timer();
        timerTask = new ModelTimerTask(this);
        cycleTask = new CycleTimerTask(this);
        timer.schedule(timerTask, 0, 1000 / 60);
        timer.schedule(cycleTask, 0, 1000);
    }

    public void pause(){
        this.timer.cancel();
    }

    public void resume(){
        this.timer = new Timer();
        timerTask = new ModelTimerTask(this);
        cycleTask = new CycleTimerTask(this);
        this.timer.schedule(timerTask, 0, 1000 / 60);
        this.timer.schedule(cycleTask, 0, 1000);
    }

    public void moveBall(){
        boolean isZeroSpeed = false;

        if(ball.getY() >= dimension.height - ball.getDiameter() || !bricks.existBricks()){
            ball.setVector(0, 0);
            ball.setVector(1, 0);
            isZeroSpeed = true;
        }
        else{
            double eps = ball.getSpeed();

            if(ball.getX() >= dimension.width - ball.getDiameter() || ball.getX() <= ball.getDiameter()){
                ball.multipleVectorByNum(0, -1);
            }

            if(ball.getY() <= ball.getDiameter()){
                ball.multipleVectorByNum(1, -1);
            }

            if(intersectDesk()){
                if(ball.getY() + ball.getDiameter() - eps <= desk.getY()){

                    if(desk.getDeskDirection().equals("left")){
                        if(ball.getVector(0) >= 0){
                            ball.setVector(0, ball.getVector(0) - 0.5);
                        }
                        else{
                            ball.setVector(0, ball.getVector(0) + 0.5);
                        }
                    }
                    else if(desk.getDeskDirection().equals("right")){
                        if(ball.getVector(0) <= 0){
                            ball.setVector(0, ball.getVector(0) - 0.5);
                        }
                        else
                            ball.setVector(0, ball.getVector(0) + 0.5);
                    }

                    if(eps*eps - ball.getVector(0)*ball.getVector(0) >= 0)
                        ball.setVector(1, Math.sqrt(eps*eps - ball.getVector(0)*ball.getVector(0)));
                    else if(desk.getDeskDirection().equals("left")){
                        ball.setVector(1, 0.5);
                    }
                    else if(desk.getDeskDirection().equals("right")){
                        ball.setVector(1, -0.5);
                    }
                    ball.multipleVectorByNum(1, -1);
                    if((ball.getX() - desk.getX() + ball.getDiameter()/2 <= desk.getWidth() / 2 && ball.getVector(0) > 0) ||
                            (ball.getX() - desk.getX() + ball.getDiameter()/2 > desk.getWidth() / 2 && ball.getVector(0) < 0))
                        ball.multipleVectorByNum(0, -1);
                }
                else if(ball.getY() - ball.getDiameter()/2 - eps < desk.getY() + desk.getHeight()){
                    double shift = 20;
                    if(ball.getX() - desk.getX() <= desk.getWidth() / 2 && ball.getX() + ball.getDiameter() - desk.getX() >= 0)
                        ball.setX(ball.getX() - shift);
                    else if(ball.getX() - desk.getX() < desk.getWidth())
                        ball.setX(ball.getX() + shift);
                    ball.multipleVectorByNum(0, -1);
                }
            }

            if(ball.getY() - eps <= (double) dimension.height / 3){
                int removeBrick;
                if((removeBrick = intersectBricks()) != -1){
                    bricks.removeBrick(removeBrick);
                    if(ball.getY() + eps >= bricks.getIntersectMaxY() || ball.getY() - eps <= bricks.getIntersectMinY() - ball.getDiameter())
                        ball.multipleVectorByNum(1, -1);
                    else
                        ball.multipleVectorByNum(0, -1);
                }
            }
            ball.setX(ball.getX() + ball.getVector(0));
            ball.setY(ball.getY() + ball.getVector(1));
        }

        setChanged();
        notifyObserver(isZeroSpeed);
    }

    public void moveDeskLeft(){
        if(!( desk.getX()-desk.getVelocity() < 0)){
            desk.setX(desk.getX() - desk.getVelocity());
        }
        else{
            desk.setX(0);
        }
    }

    public void moveDeskRight(){
        if(!(desk.getX()+desk.getVelocity()+desk.getWidth() > dimension.width))
            desk.setX(desk.getX() + desk.getVelocity());
        else
            desk.setX(dimension.width - desk.getWidth());
    }

    public boolean intersectDesk(){
        return (ball.getShape().intersects(desk.getShape()) || (desk.getShape().intersects(ball.getAreaBall())));
    }

    public int intersectBricks(){
        Rectangle2D.Double brick;
        for(int i = 0; i < 100; i++){
            brick = bricks.getShape(i);
            if(brick!= null && ball.getShape().intersects(brick)){
                return i;
            }
        }
        return -1;
    }

    public Dimension getDimension(){
        return new Dimension(dimension);
    }
}
