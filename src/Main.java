
import javax.swing.*;
import java.awt.*;



public class Main {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        EventQueue.invokeLater(() -> {
            ShipTripFrame ex = new ShipTripFrame();
            ex.setVisible(true);
            ex.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        });
    }
}
