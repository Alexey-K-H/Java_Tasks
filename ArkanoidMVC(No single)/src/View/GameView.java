package View;

import Model.Model;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class GameView extends JPanel {
    private Model model;
    private TexturePaint bricksTexture;
    private BufferedImage spaceTexture;
    private TexturePaint deskTexture;

    public GameView(Model _model){
        model = _model;
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;
        Ellipse2D.Double ball = model.getBall().getShape();
        Rectangle2D.Double desk = model.getDesk().getShape();
        Rectangle2D.Double brick;

        g2.drawImage(spaceTexture, 0, 0, model.getDimension().width, model.getDimension().height, null);
        Font font = new Font(null, Font.BOLD, 15);
        g2.setFont(font);
        g2.setColor(Color.WHITE);
        int points = model.getBricks().getAmountBricks();
        g2.drawString("Score: " + points, 30, 570);


        g2.drawString(String.format("Time: %02d:%02d:%02d", model.getTime()/3600, model.getTime()/60, model.getTime()%60), 600, 570);

        g2.setColor(Color.WHITE);
        g2.draw(ball);
        g2.fill(ball);
        g2.setStroke(new BasicStroke(2));
        g2.setColor(Color.YELLOW);
        g2.draw(desk);
        g2.setPaint(deskTexture);
        g2.fill(desk);
        for(int i = 0; i < 100; i ++){
            brick = model.getBricks().getShape(i);
            if(brick != null){
                g2.setColor(Color.BLACK);
                g2.draw(brick);
                g2.setPaint(bricksTexture);
                g2.fill(brick);
            }
        }



        Toolkit.getDefaultToolkit().sync();
    }

    public void setTexture() throws IOException {
        BufferedImage image = ImageIO.read(GameView.class.getResourceAsStream("/Resources/brick8.jpg"));
        bricksTexture = new TexturePaint(image, Objects.requireNonNull(model.getBricks().getShape(0)).getBounds2D());
        spaceTexture = ImageIO.read(GameView.class.getResourceAsStream("/Resources/space2.jpg"));

        image = ImageIO.read(GameView.class.getResourceAsStream("/Resources/desk.jpg"));
        deskTexture = new TexturePaint(image, model.getDesk().getShape().getBounds2D());
    }
}
