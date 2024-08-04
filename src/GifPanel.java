import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

public class GifPanel extends JPanel {
    private ImageIcon gifIcon;
    private Timer timer;
    private int currentFrame = 0;
    private double scaleFactor = 1.0; // Default scale factor is 1 (no scaling)
    private Color maskColor = new Color(255, 255, 255, 150);;

    public GifPanel(String gifPath, double scaleFactor) {
        gifIcon = new ImageIcon(gifPath);
        this.scaleFactor = scaleFactor;

        // Timer to handle animation
        timer = new Timer(100, e -> {
            currentFrame++;
            int frameCount = getFrameCount();
            if (currentFrame >= frameCount) {
                currentFrame = 0;
            }
            repaint();
        });
        timer.start();
        setDoubleBuffered(true); // Ensure double buffering is enabled
    }

    public void setColorMask(int r, int g, int b) {
    	maskColor = new Color(r, g, b, 150);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (gifIcon.getImage() != null) {
            int panelWidth = getWidth();
            int panelHeight = getHeight();
            int gifWidth = gifIcon.getIconWidth();
            int gifHeight = gifIcon.getIconHeight();
            
            // Calculate frame size based on the scale factor
            int frameWidth = (int) (gifWidth / scaleFactor);
            int frameHeight = (int) (gifHeight / scaleFactor);

            // Calculate the X and Y positions to center the GIF
            int x = (panelWidth - frameWidth) / 2;
            int y = (panelHeight - frameHeight) / 2;

            // Create a BufferedImage to apply the mask
            BufferedImage bufferedImage = new BufferedImage(frameWidth, frameHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = bufferedImage.createGraphics();

            // Draw the scaled GIF image onto the BufferedImage
            g2d.drawImage(gifIcon.getImage(), 0, 0, frameWidth, frameHeight, this);

            // Create a circular mask
            Shape circle = new Ellipse2D.Double(54, 10, frameWidth-108, frameHeight-20);
            g2d.setClip(circle);

            // Set up the mask color
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, maskColor.getAlpha() / 255f));
            g2d.setColor(maskColor);
            g2d.fill(circle);

            // Reset the clip to draw the circular mask correctly
            g2d.setClip(null);
            
            // Draw the BufferedImage with the circular mask applied
            g.drawImage(bufferedImage, x, y, this);

            // Dispose of the Graphics2D object
            g2d.dispose();
        }
    }

    @Override
    public Dimension getPreferredSize() {
        int width = (int) (gifIcon.getIconWidth() / scaleFactor);
        int height = (int) (gifIcon.getIconHeight() / scaleFactor);
        return new Dimension(width, height);
    }
    
    private int getFrameCount() {
        return (int) Math.floor(gifIcon.getIconWidth() / (getWidth() / scaleFactor));
    }
}
