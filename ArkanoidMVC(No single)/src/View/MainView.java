package View;

import Model.Model;
import Observe.ArkanoidObservable;
import Observe.ArkanoidObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.*;

public class MainView implements ArkanoidObserver, KeyListener {
    private JFrame frame;
    private Model model;
    private MenuView menu;
    private GameView gameView;

    public MainView(Dimension d, JFrame frame_of_view, Model curr_model){
        frame = frame_of_view;
        model = curr_model;
        menu = new MenuView(d, frame, this);
        gameView = new GameView(model);
        gameView.addKeyListener(this);
        gameView.setFocusable(true);
    }

    public void initMenu(){
        frame.setResizable(false);
        frame.setVisible(true);
        frame.getContentPane().add(menu);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void play() throws IOException{
        initGame();
    }

    private void initGame() throws IOException{
        gameView.setSize(frame.getSize());
        frame.getContentPane().add(gameView);
        model.addObserver(this);
        model.setDimension(gameView.getSize());
        model.init();

        gameView.setTexture();
        gameView.requestFocus();
    }

    @Override
    public void update(ArkanoidObservable o, boolean isOver) {
        if(isOver){
            try{
                gameOver();
            }
            catch (IOException ex){
                throw new UncheckedIOException(ex);
            }
        }
        else {
            gameView.repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_A:
                model.getDesk().setDeskDirection("left");
                break;
            case KeyEvent.VK_D:
                model.getDesk().setDeskDirection("right");
                break;
            case KeyEvent.VK_ESCAPE:
                model.pause();
                model.deleteObserver(this);
                Object[] options = new Object[]{"Resume", "Restart", "Exit"};
                int select = JOptionPane.showOptionDialog(null, "", "Pause", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
                switch (select){
                    case 0:
                        model.addObserver(this);
                        model.resume();
                        break;
                    case 1:
                        try{
                            model.resetTime();
                            frame.getContentPane().remove(gameView);
                            frame.getContentPane().repaint();
                            play();
                            break;
                        }
                        catch (IOException ex){
                            throw new UncheckedIOException(ex);
                        }
                    case 2:
                        model.resetTime();
                        frame.getContentPane().remove(gameView);
                        frame.getContentPane().repaint();
                        frame.getContentPane().add(menu);
                        break;
                }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        model.getDesk().setDeskDirection("stay");
    }

    private void gameOver() throws IOException {
        model.pause();
        model.resetTime();
        model.deleteObserver(this);
        int select;
        Object[] options = new Object[]{"Exit", "Restart"};
        int points = model.getBricks().getAmountBricks();
        if(model.getBricks().existBricks()){
            select = JOptionPane.showOptionDialog(null, "Game over\nYour score: " + points, "Try again", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        }
        else{
            select = JOptionPane.showOptionDialog(null, "You are won!\nYour score: " + points, "Congratulations", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        }
        frame.getContentPane().remove(gameView);
        printScore(points);

        switch (select){
            case 0:
                frame.getContentPane().repaint();
                frame.getContentPane().add(menu);
                break;
            case 1:
                play();
                break;
        }
    }

    private void printScore(int points) throws IOException {
        TreeMap<Integer, String> scores = new TreeMap<>(Collections.reverseOrder());
        File file = new File("src/Resources/Score");
        FileReader fr = new FileReader(file);
        Scanner sc = new Scanner(fr);
        scores.put(points, "Score.................:");

        if (file.length() > 1) {
            int countRecords = 10;
            while (sc.hasNextLine() && countRecords>0) {
                String[] arrOfStr = sc.nextLine().split(":", 0);
                int curr_points = Integer.parseInt(arrOfStr[1]);
                scores.put(curr_points, arrOfStr[0] + ":");
                countRecords--;
            }
        }

        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write("");

        for (Map.Entry<Integer, String> entry : scores.entrySet()) {
            fileWriter.write(entry.getValue() + entry.getKey() + "\n");
        }
        fileWriter.flush();
        fileWriter.close();
        sc.close();
        fr.close();
    }
}
