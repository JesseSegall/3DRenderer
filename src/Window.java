import javax.swing.*;
import java.awt.*;

public class Window {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Window::new);
    }

    private Window() {
        JFrame frame = new JFrame("3D Renderer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);

        Container pane = frame.getContentPane();
        pane.setLayout(new BorderLayout());

        Scene scene = new Scene();
        Renderer renderer = new Renderer();


        Camera camera = new Camera(pane);


        JPanel renderPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setBackground(Color.BLACK);
                renderer.render(g, scene, camera, getWidth(), getHeight());
            }
        };

        renderPanel.setPreferredSize(new Dimension(800, 600));
        pane.add(renderPanel, BorderLayout.CENTER);

        camera.getHeadingSlider().addChangeListener(e -> renderPanel.repaint());
        camera.getPitchSlider().addChangeListener(e -> renderPanel.repaint());
        camera.getRollSlider().addChangeListener(e -> renderPanel.repaint());
        camera.getFovSlider().addChangeListener(e -> renderPanel.repaint());

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
