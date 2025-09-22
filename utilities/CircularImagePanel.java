package utilities;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class CircularImagePanel extends JPanel {
    private BufferedImage image;

    public CircularImagePanel(String resourcePath, int size) {
        setPreferredSize(new Dimension(size, size));
        setOpaque(false);

        try {
            // Load from resources (inside src)
            image = ImageIO.read(getClass().getResource(resourcePath));
            System.out.println(image);
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Could not load image: " + resourcePath + "\n" + e);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int size = Math.min(getWidth(), getHeight());
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Shape circle = new Ellipse2D.Double(0, 0, size, size);
        g2.setClip(circle);

        if (image != null) {
            g2.drawImage(image, 0, 0, size, size, this);
        } else {
            g2.setColor(Color.WHITE);  // fallback placeholder
            g2.fill(circle);
        }

        g2.dispose();
    }
}
