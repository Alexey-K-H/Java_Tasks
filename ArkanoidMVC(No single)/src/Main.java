import Controller.Controller;
import java.awt.*;

public class Main {
    public static void main(String[] args){
        try{
            Controller game = new Controller(new Dimension(800, 630));
            game.run();
        }
        catch (Exception ex){
            System.err.println(ex.getMessage());
        }
    }
}
