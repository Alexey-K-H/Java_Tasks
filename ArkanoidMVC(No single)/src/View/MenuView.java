package View;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Scanner;

public class MenuView extends JPanel {
    private JFrame frame;
    private MainView mainView;

    public MenuView(Dimension dimension, JFrame _frame, MainView _mainView){
        frame = _frame;
        mainView = _mainView;
        String[] nameButton = {"New game", "Exit", "About", "High scores"};
        JButton[] menuButtons = new JButton[4];
        GridLayout layout = new GridLayout(4, 1, 0, 20);

        for(int i = 0; i < menuButtons.length; i++){
            menuButtons[i] = new JButton(nameButton[i]);
            menuButtons[i].setFont(new Font("Dialog", Font.BOLD, 25));
            menuButtons[i].setBackground(Color.GRAY);
            menuButtons[i].setForeground(Color.white);
        }

        this.setBorder(BorderFactory.createEmptyBorder(60, 60, 60, 60));
        this.setSize(dimension);
        this.setBackground(Color.BLACK);
        this.setLayout(layout);

        for (JButton menuButton : menuButtons) {
            this.add(menuButton);
        }

        menuButtons[0].addActionListener((ActionEvent e) -> {
            try {
                frame.getContentPane().remove(this);
                mainView.play();
            }
            catch (IOException ex){
                throw new UncheckedIOException(ex);
            }
        });

        menuButtons[1].addActionListener((ActionEvent e) -> System.exit(0));

        menuButtons[2].addActionListener((ActionEvent e) -> {
            try {
                frame.getContentPane().remove(this);
                showInfo("src/Resources/About");
            }
            catch (Exception ex){
                System.err.println(ex.getMessage());
            }
        });

        menuButtons[3].addActionListener((ActionEvent e) -> {
            try {
                frame.getContentPane().remove(this);
                showInfo("src/Resources/Score");
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

        info.add(text);
        info.add(back);
        frame.getContentPane().add(info);

        back.addActionListener((ActionEvent e) -> {
            frame.getContentPane().remove(info);
            frame.getContentPane().repaint();
            frame.getContentPane().add(this);
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
}
