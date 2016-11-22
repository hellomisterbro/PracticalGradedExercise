import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by kirichek on 11/20/16.
 */
public class ShipTripFrame extends JFrame implements ChangeListener, ParametersChangeListener {

    public static final String APPLICATION_TITLE = "warning.png";

    public static final int MIN_FRAME_WIDTH = 900;
    public static final int MIN_FRAME_HEIGHT = 580;
    public static final int MIN_CONTROL_PANE_WIDTH = 400;

    public static final String NAME_OF_WARNING = "warning.png";

    private JSlider speedSlider;
    private JSlider angleSlider;
    private JSpinner spinnerSpeed;
    private JSpinner spinnerAngle;
    private JProgressBar bar;
    private JLabel imageLabel;
    private JLabel labelForWarning = new JLabel("You are too fast!!");

    public ShipTripFrame() {
        initUI();
    }

    /*
    *
    * Creating UI components
    * */

    public void initUI() {

        setTitle(APPLICATION_TITLE);
        centreWindow();
        setSize(MIN_FRAME_WIDTH, MIN_FRAME_HEIGHT);
        setMinimumSize(new Dimension(MIN_FRAME_WIDTH, MIN_FRAME_HEIGHT));
        getContentPane().setLayout(new BorderLayout());
        createShipPanel();
        createControlElements();
    }


    public void createShipPanel() {
        ShipDrawerPane pane = new ShipDrawerPane();
        JScrollPane scroller = new JScrollPane(pane);
        add(scroller, BorderLayout.CENTER);
        scroller.setFocusable(true);
    }

    public void createControlElements() {
        JPanel controlPanel = new JPanel();
        controlPanel.setPreferredSize(new Dimension(MIN_CONTROL_PANE_WIDTH, MIN_FRAME_HEIGHT));
        controlPanel.setLayout(new GridBagLayout());

        SpinnerNumberModel spinnerModelSpeed = new SpinnerNumberModel(0, 0, 100, 1);
        SpinnerNumberModel spinnerModelAngle = new SpinnerNumberModel(0, 0, 100, 1);
        spinnerSpeed = new JSpinner(spinnerModelSpeed);
        spinnerSpeed.setPreferredSize(new Dimension(300, 30));
        spinnerAngle = new JSpinner(spinnerModelAngle);
        spinnerAngle.setPreferredSize(new Dimension(300, 30));

        JLabel speedLabelForSpinner = new JLabel("Speed:");
        speedLabelForSpinner.setPreferredSize(new Dimension(50, 30));
        JLabel angleLabelForSpinner = new JLabel("Angle:");
        angleLabelForSpinner.setPreferredSize(new Dimension(50, 30));
        JLabel speedLabelForSlider = new JLabel("Speed:");
        JLabel angleLabelForSlider = new JLabel("Angle:");

        speedSlider = new JSlider(JSlider.VERTICAL, 0, 100, 0);
        speedSlider.setMinorTickSpacing(5);
        speedSlider.setMajorTickSpacing(20);
        speedSlider.setPaintTicks(true);
        speedSlider.setPaintLabels(true);

        angleSlider = new JSlider(JSlider.HORIZONTAL, TripParameters.MIN_ANGLE, TripParameters.MAX_ANGLE, 0);
        angleSlider.setMinorTickSpacing(20);
        angleSlider.setMajorTickSpacing(45);
        angleSlider.setPaintTicks(true);
        angleSlider.setPaintLabels(true);


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.insets = new Insets(5, 5, 5, 5);

        TripParameters.getInstance().addListener(this);
        speedSlider.addChangeListener(this);
        angleSlider.addChangeListener(this);
        spinnerSpeed.addChangeListener(this);
        spinnerAngle.addChangeListener(this);

        bar = new JProgressBar(JProgressBar.VERTICAL, 0, 100);
        try {
            Image img = ImageIO.read(new File(NAME_OF_WARNING));
            img = img.getScaledInstance(150, 150, Image.SCALE_REPLICATE);
            ImageIcon icon = new ImageIcon(img);
            imageLabel = new JLabel(icon);
        } catch (IOException exc) {
            imageLabel = new JLabel("Warning!");
        }


        addComp(controlPanel, speedLabelForSpinner, 1, 1, 1, 1, GridBagConstraints.CENTER, 0.9, 0.1, gbc);
        addComp(controlPanel, spinnerSpeed, 2, 1, 4, 1, GridBagConstraints.CENTER, 0.9, 0.1, gbc);
        addComp(controlPanel, angleLabelForSpinner, 1, 2, 1, 1, GridBagConstraints.CENTER, 0.9, 0.1, gbc);
        addComp(controlPanel, spinnerAngle, 2, 2, 4, 1, GridBagConstraints.CENTER, 0.9, 0.1, gbc);
        addComp(controlPanel, speedLabelForSlider, 1, 3, 1, 1, GridBagConstraints.BOTH, 0.1, 0.1, gbc);
        addComp(controlPanel, labelForWarning, 3, 3, 1, 1, GridBagConstraints.CENTER, 0.1, 0.1, gbc);
        addComp(controlPanel, speedSlider, 1, 4, 1, 1, GridBagConstraints.BOTH, 0.1, 0.1, gbc);
        addComp(controlPanel, bar, 2, 4, 1, 1, GridBagConstraints.CENTER, 0.1, 0.1, gbc);
        addComp(controlPanel, imageLabel, 3, 4, 3, 1, GridBagConstraints.CENTER, 0.1, 0.1, gbc);
        addComp(controlPanel, angleLabelForSlider, 1, 5, 5, 1, GridBagConstraints.BOTH, 0.1, 0.1, gbc);
        addComp(controlPanel, angleSlider, 2, 5, 5, 1, GridBagConstraints.BOTH, 0.1, 0.1, gbc);

        imageLabel.setVisible(false);
        labelForWarning.setVisible(false);
        getContentPane().add(controlPanel, BorderLayout.EAST);

    }


    public void centreWindow() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - getHeight()) / 2);
        setLocation(x, y);
    }

    /*
    *
    * Listeners
    *
    * */

    @Override
    public void stateChanged(ChangeEvent e) {
        Object source = e.getSource();
        if (source instanceof JSlider) {
            JSlider slider = (JSlider) source;
            if (slider == angleSlider) {
                TripParameters.getInstance().setAngle(slider.getValue());
            } else if (slider == speedSlider) {
                TripParameters.getInstance().setSpeed(slider.getValue());
            }
        } else if (source instanceof JSpinner) {
            JSpinner spinner = (JSpinner) source;
            if (spinner == spinnerAngle) {
                TripParameters.getInstance().setAngle((int) spinner.getValue());
            } else if (spinner == spinnerSpeed) {
                TripParameters.getInstance().setSpeed((int) spinner.getValue());
            }
        }

    }

    @Override
    public void parametersChanged(int speed, int angle) {
        boolean speedIsOK = speed > 0.9 * TripParameters.MAX_SPEED;
        imageLabel.setVisible(speedIsOK);
        labelForWarning.setVisible(speedIsOK);
        bar.setValue(speed);
        spinnerSpeed.setValue(speed);
        spinnerAngle.setValue(angle);
        speedSlider.setValue(speed);
        angleSlider.setValue(angle);
    }


    /*
    *
    * Helpers
    *
    * */
    private void addComp(JPanel panel, JComponent comp,
                         int x, int y,
                         int gWidth, int gHeight,
                         int fill,
                         double weightx, double weighty,
                         GridBagConstraints gbc) {
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = gWidth;
        gbc.gridheight = gHeight;
        gbc.fill = fill;
        gbc.weightx = weightx;
        gbc.weighty = weighty;

        panel.add(comp, gbc);
    }

}
