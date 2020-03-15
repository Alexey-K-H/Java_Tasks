package Controller;

import Model.Model;
import View.MainView;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.*;
import java.awt.event.*;

public class Controller implements ActionListener, KeyListener {
    private Timer timer;
    private MainView view;
    private JFrame frame;
    private final HashSet<Integer> pressed = new HashSet<>();

    public Controller(Dimension d){
        frame = new JFrame("Arkanoid");
        timer = new Timer(1000 / 60, this);
        view = new MainView(frame.getSize());
        view.addKeyListener(this);
        view.setFocusable(true);
        frame.setSize(d);
    }

    private void play() throws IOException{
        initGame();
        timer.start();
    }

    private void initGame() throws IOException{
        view.setSize(frame.getSize());
        tieComp(frame, view);
        Model.getInstance().setDimension(view.getSize());
        Model.getInstance().init();
        view.setTexture();
        view.requestFocus();
    }

    private void tieComp(Container main, JComponent... components){
        for (JComponent component : components) {
            main.add(component);
        }
    }

    private boolean isEnd(){
        return (Model.Ball.getSpeed() == 0);
    }

    public void initMenu(){
        JPanel menu = new JPanel();
        String[] nameButton = {"New game", "Exit", "About", "High scores"};
        JButton[] menuButtons = new JButton[4];
        GridLayout layout = new GridLayout(4, 1, 0, 20);

        for(int i = 0; i < menuButtons.length; i++){
            menuButtons[i] = new JButton(nameButton[i]);
            menuButtons[i].setFont(new Font("Dialog", Font.BOLD, 25));
            menuButtons[i].setBackground(Color.GRAY);
            menuButtons[i].setForeground(Color.white);
        }

        menu.setBorder(BorderFactory.createEmptyBorder(60, 60, 60, 60));
        Dimension d = frame.getSize();
        menu.setSize(d);
        menu.setBackground(Color.BLACK);
        menu.setLayout(layout);
        tieComp(menu, menuButtons);

        frame.add(menu);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        menuButtons[0].addActionListener((ActionEvent e) -> {
            try {
                frame.remove(menu);
                play();
            }
            catch (IOException ex){
                throw new UncheckedIOException(ex);
            }
        });

        menuButtons[1].addActionListener((ActionEvent e) -> System.exit(0));

        menuButtons[2].addActionListener((ActionEvent e) -> {
            try {
                frame.remove(menu);
                showInfo("/home/ninetail/Desktop/Java Labs/Arkanoid/src/About");
            }
            catch (Exception ex){
                System.err.println(ex.getMessage());
            }
        });

        menuButtons[3].addActionListener((ActionEvent e) -> {
            try {
                frame.remove(menu);
                showInfo("/home/ninetail/Desktop/Java Labs/Arkanoid/src/Score");
            }
            catch (Exception ex){
                System.err.println(ex.getMessage());
            }
        });
    }

    private void showInfo(String path) throws IOException, BadLocationException {
        JPanel info = new JPanel();
        JTextPane text = new JTextPane();
        JButton back = new JButton("Back");

        textFromFile(text, path);
        text.setFont(new Font("Dialog", Font.BOLD, 15));
        text.setForeground(Color.WHITE);
        text.setBackground(Color.GRAY);
        back.setFont(new Font("Dialog", Font.BOLD, 25));
        back.setForeground(Color.white);
        back.setBackground(Color.BLACK);
        info.setSize(frame.getSize());
        back.setPreferredSize(new Dimension(frame.getSize().width, (frame.getSize().height) / 4));
        text.setPreferredSize(new Dimension(frame.getSize().width, (3 * frame.getSize().height) / 4));
        tieComp(info, text, back);
        frame.add(info);

        back.addActionListener((ActionEvent e) ->{
            frame.remove(info);
            initMenu();
        });
    }

    private void textFromFile(JTextPane field, String path) throws IOException, BadLocationException {
        File file = new File(path);
        FileReader fr = new FileReader(file);
        Scanner sc = new Scanner(fr);
        StyledDocument doc = field.getStyledDocument();
        SimpleAttributeSet attributes = new SimpleAttributeSet();

        StyleConstants.setAlignment(attributes, StyleConstants.ALIGN_CENTER);
        while (sc.hasNextLine()){
            doc.insertString(doc.getLength(), sc.nextLine() + "\n", attributes);
        }
        doc.setParagraphAttributes(0, doc.getLength(), attributes, false);
        sc.close();
        fr.close();
    }

    private void printScore(String pathFile, int points) throws IOException {
        //TreeSet<Integer> numbers = new TreeSet<>(Integer::compare);
        TreeMap<Integer, String> scores = new TreeMap<>(Collections.reverseOrder());
        File file = new File(pathFile);
        FileReader fr = new FileReader(file);
        Scanner sc = new Scanner(fr);
        //numbers.add(points);
        scores.put(points, "Score.................:");

        while(sc.hasNextLine()){
            //numbers.add(Integer.parseInt(sc.nextLine()));
            String[] arrOfStr = sc.nextLine().split(":", 0);
            int curr_points = Integer.parseInt(arrOfStr[1]);
            scores.put(curr_points, arrOfStr[0] + ":");
        }
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write("");

        /*for(Integer number : numbers){
            fileWriter.write(number + "\n");
        }*/

        for(Map.Entry<Integer, String> entry : scores.entrySet()){
            fileWriter.write(entry.getValue() + entry.getKey() + "\n");
        }

        sc.close();
        fr.close();
        fileWriter.flush();
        fileWriter.close();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        SwingUtilities.invokeLater(() -> {
            Model.getInstance().setDimension(view.getSize());
            Model.Ball.moveBall(pressed.hashCode());
            switch (pressed.hashCode()){
                case KeyEvent.VK_LEFT:
                    Model.Desk.moveLeft();
                    break;
                case KeyEvent.VK_RIGHT:
                    Model.Desk.moveRight();
                    break;
                case KeyEvent.VK_ESCAPE:
                    pressed.clear();
                    timer.stop();
                    Object[] options = new Object[]{"Resume", "Restart", "Exit"};
                    int select = JOptionPane.showOptionDialog(null, "", "Pause", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
                    switch (select){
                        case 0:
                            timer.start();
                            break;
                        case 1:
                            try{
                                frame.remove(view);
                                play();
                                break;
                            }
                            catch (IOException ex){
                                throw new UncheckedIOException(ex);
                            }
                        case 2:
                            frame.remove(view);
                            initMenu();
                            break;
                    }
            }
        });
        view.draw();

        if(isEnd()){
            try{
                gameOver();
            }
            catch (IOException ex){
                throw new UncheckedIOException(ex);
            }
        }
    }

    private void gameOver() throws IOException {
        timer.stop();
        int select;
        Object[] options = new Object[]{"Exit", "Restart"};
        int points = Model.Bricks.getAmountBricks();
        if(Model.Bricks.existBricks()){
            select = JOptionPane.showOptionDialog(null, "Game over\nYour score: " + points, "Try again", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        }
        else{
            select = JOptionPane.showOptionDialog(null, "You are won!\nYour score: " + points, "Congratulations", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        }
        pressed.clear();
        frame.remove(view);
        printScore("/home/ninetail/Desktop/Java Labs/Arkanoid/src/Score", points);

        switch (select){
            case 0:
                initMenu();
                break;
            case 1:
                play();
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        pressed.add(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressed.remove(e.getKeyCode());
    }
}
