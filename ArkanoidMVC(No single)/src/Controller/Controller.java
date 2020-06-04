package Controller;
import Model.Model;
import View.MainView;
import javax.swing.*;
import java.awt.*;

public class Controller{
    private MainView view;

    public Controller(Dimension d){
        JFrame frame = new JFrame("Arkanoid");
        frame.setSize(d);
        Model model = new Model();
        view = new MainView(frame.getSize(), frame, model);
    }

    public void run(){
        view.initMenu();
    }

}
