import javax.swing.*;
import java.awt.*;

public class Camera {
    private double heading;
    private double pitch;
    private double roll;
    private double fov;

    private final JSlider headingSlider;
    private final JSlider pitchSlider;
    private final JSlider rollSlider;
    private final JSlider fovSlider;

    public Camera(Container container){
        headingSlider = new JSlider(0, 360, 180);
        pitchSlider = new JSlider(SwingConstants.VERTICAL, -90, 90, 0);
        rollSlider = new JSlider(SwingConstants.VERTICAL, -90, 90, 0);
        fovSlider = new JSlider(1, 179, 60);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(headingSlider, BorderLayout.CENTER);

        JPanel westPanel = new JPanel(new BorderLayout());
        westPanel.add(rollSlider, BorderLayout.CENTER);

        JPanel eastPanel = new JPanel(new BorderLayout());
        eastPanel.add(pitchSlider, BorderLayout.CENTER);

        container.add(southPanel, BorderLayout.SOUTH);
        container.add(eastPanel, BorderLayout.EAST);
        container.add(westPanel, BorderLayout.WEST);
        container.add(fovSlider, BorderLayout.NORTH);

        headingSlider.addChangeListener(e -> updateHeading());
        pitchSlider.addChangeListener(e -> updatePitch());
        rollSlider.addChangeListener(e -> updateRoll());
        fovSlider.addChangeListener(e -> updateFOV());

        // Initialize values
        updateHeading();
        updatePitch();
        updateRoll();
        updateFOV();
    }

    private void updateHeading(){
        heading = Math.toRadians(headingSlider.getValue());
    }
    private void updatePitch() {
        pitch = Math.toRadians(pitchSlider.getValue());
    }
    private void updateRoll() {
        roll = Math.toRadians(rollSlider.getValue());
    }
    private void updateFOV() {
        fov = Math.toRadians(fovSlider.getValue());
    }

    public Matrix getHeadingTransform() {
        return new Matrix(new double[]{
                Math.cos(heading), 0, -Math.sin(heading), 0,
                0, 1, 0, 0,
                Math.sin(heading), 0, Math.cos(heading), 0,
                0, 0, 0, 1
        });
    }

    public Matrix getPitchTransform() {
        return new Matrix(new double[]{
                1, 0, 0, 0,
                0, Math.cos(pitch), Math.sin(pitch), 0,
                0, -Math.sin(pitch), Math.cos(pitch), 0,
                0, 0, 0, 1
        });
    }

    public Matrix getRollTransform() {
        return new Matrix(new double[]{
                Math.cos(roll), -Math.sin(roll), 0, 0,
                Math.sin(roll), Math.cos(roll), 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1
        });
    }

    public Matrix getPanOutTransform() {
        return new Matrix(new double[]{
                1, 0, 0, 0,
                0, 1, 0, 0,
                0, 0, 1, 0,
                0, 0, -400, 1
        });
    }

    public Matrix getTransformationMatrix() {
        return getHeadingTransform()
                .multiplyMatrices(getPitchTransform())
                .multiplyMatrices(getRollTransform())
                .multiplyMatrices(getPanOutTransform());
    }

    public JSlider getHeadingSlider() {
        return headingSlider;
    }

    public JSlider getPitchSlider() {
        return pitchSlider;
    }

    public JSlider getRollSlider() {
        return rollSlider;
    }

    public JSlider getFovSlider() {
        return fovSlider;
    }

    public double getFov() {
        return fov;
    }
}
