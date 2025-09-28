package utilities;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class CircularImagePanel extends JPanel {
    private BufferedImage image;

    public CircularImagePanel(BufferedImage image) {
        this.image = image;
        setPreferredSize(new Dimension(250, 250));
        setOpaque(false);
    }

    public CircularImagePanel(BufferedImage image, int width, int height) {
        this.image = image;
        setPreferredSize(new Dimension(width, height));
        // this.setAlignmentY(Component.CENTER_ALIGNMENT);
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int diameter = Math.min(getWidth(), getHeight());
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (image != null) {
            Shape circle = new Ellipse2D.Float(0, 0, diameter, diameter);
            g2.setClip(circle);
            g2.drawImage(image, 0, 0, diameter, diameter, this);
        } else {
            g2.setColor(Color.WHITE);
            g2.fillOval(0, 0, diameter, diameter);
        }

        g2.dispose();
    }
}
