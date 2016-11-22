import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Created by kirichek on 11/21/16.
 */

public class ShipDrawerPane extends JLayeredPane {

    public static final Color SHIP_PANEL_COLOR = new Color(255, 255, 255);

    private static final Color LINE_COLOR = new Color(25, 25, 255);
    private static final int LINE_THICKNESS = 1;
    private static final int NUMBER_OF_LINES = 6;

    public final static int GRAPHICS_UPDATING_TIME = 1;

    public final static String FILE_NAME = "ship-big.jpg";

    public final static double SPEED_COEFFICIENT = 2.0;

    private BufferedImage img;

    private double stepForHorizontalLines = 0;
    private double stepForVericallLines = 0;


    ShipDrawerPane() {

        try {
            img = ImageIO.read(new File(FILE_NAME));
        } catch (IOException exc) {
            img = null;
        }

        setBackground(SHIP_PANEL_COLOR);
        setFocusable(true);
        Timer timer = new Timer(GRAPHICS_UPDATING_TIME, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                repaint();
            }
        });

        setVisible(true);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int speed = TripParameters.getInstance().getSpeed();
        int angle = TripParameters.getInstance().getAngle();


        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setStroke(new BasicStroke(LINE_THICKNESS));
        g2d.setColor(LINE_COLOR);


        double deltaHorizontal = (double) getHeight() / (double) NUMBER_OF_LINES;
        double deltaVertical = (double) getWidth() / (double) NUMBER_OF_LINES;

        for (int i = 0; i < getHeight(); i += deltaHorizontal) {
            Line2D line = new Line2D.Double(0, stepForHorizontalLines + i, getWidth(), stepForHorizontalLines + i);
            g2d.draw(line);
        }

        for (int i = 0; i < getWidth(); i += deltaVertical) {
            Line2D line = new Line2D.Double(stepForVericallLines + i, 0, stepForVericallLines + i, getHeight());
            g2d.draw(line);
        }

        //Adding image
        int x = getWidth()/2;
        int y = getHeight()/2;

        AffineTransform trans = new AffineTransform();
        trans.rotate( Math.toRadians(angle), x, y);
        trans.translate(x - img.getWidth()/2, y - img.getHeight()/2);
        g2d.drawImage(img, trans, this);


        double angleImpactHorizontal = Math.abs((1.0 - Math.abs((double) angle / ((double) TripParameters.MAX_ANGLE/2.0))));
        double angleImpactVertical = 1 - angleImpactHorizontal;
        double speedImpact = SPEED_COEFFICIENT *((double) speed /((double) TripParameters.MAX_SPEED));

        int quoter = (int) (TripParameters.MAX_ANGLE/2.0);
        int max = TripParameters.MAX_ANGLE;

        if(angle >= -max && angle <= -quoter){
            stepForVericallLines += angleImpactVertical * speedImpact;
            stepForHorizontalLines -= angleImpactHorizontal * speedImpact;
        } else if (angle >= -quoter && angle <= 0){
            stepForVericallLines += angleImpactVertical * speedImpact;
            stepForHorizontalLines += angleImpactHorizontal * speedImpact;
        } else if (angle >= 0 && angle <= quoter){
            stepForVericallLines -= angleImpactVertical * speedImpact;
            stepForHorizontalLines += angleImpactHorizontal * speedImpact;
        } else if (angle >= quoter && angle <= max){
            stepForVericallLines -= angleImpactVertical * speedImpact;
            stepForHorizontalLines -= angleImpactHorizontal * speedImpact;
        }

        if (stepForHorizontalLines > deltaHorizontal || - stepForHorizontalLines > deltaHorizontal ) {
            stepForHorizontalLines = 0.0;
        }
        if(stepForVericallLines > deltaVertical || - stepForVericallLines > deltaHorizontal){
            stepForVericallLines = 0.0;
        }

        g2d.dispose();
    }



}
