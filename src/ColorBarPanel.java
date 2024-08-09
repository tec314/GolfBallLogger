import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ColorBarPanel extends JPanel {

    private GifPanel gifPanel;
    
    private String colorPickText; 
    private String pos;

    // Constructor to initialize the class
    public ColorBarPanel(GifPanel gifPanel, String pos) {
        this.gifPanel = gifPanel;
        this.pos = pos;
        
        setLayout(new GridLayout());

        addColorPanel(Color.WHITE, "WHITE", "W", new Color(255, 255, 255));
        addColorPanel(Color.RED, "RED", "R", new Color(255, 0, 0));
        addColorPanel(new Color(255, 128, 0), "ORANGE", "O", new Color(255, 128, 0));
        addColorPanel(Color.YELLOW, "YELLOW", "Y", new Color(255, 255, 0));
        addColorPanel(new Color(198, 255, 0), "LIME", "L", new Color(198, 255, 0));
        addColorPanel(Color.GREEN, "GREEN", "G", new Color(0, 255, 0));
        addColorPanel(new Color(0, 128, 0), "FOREST", "F", new Color(0, 128, 0));
        addColorPanel(new Color(0, 206, 230), "CYAN", "C", new Color(0, 206, 230));
        addColorPanel(new Color(0, 128, 255), "BLUE", "B", new Color(0, 128, 255));
        addColorPanel(Color.PINK, "PINK", "P", new Color(255, 0, 255));
        addColorPanel(new Color(128, 0, 255), "VIOLET", "V", new Color(128, 0, 255));
    }

    // Helper method to add individual color panels
    private void addColorPanel(Color color, String colorName, String label, Color colorMask) {
        JPanel colorPanel = new JPanel();
        colorPanel.setBackground(color);
        colorPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	if(pos.equals("th")) {
            		colorPickText = colorName;
            	} else if(pos.equals("bh")) {
            		
            	}
                gifPanel.setColorMask(colorMask.getRed(), colorMask.getGreen(), colorMask.getBlue(), pos);
            }
        });
        this.add(colorPanel);
        JLabel colorLabel = new JLabel(label);
        colorPanel.add(colorLabel);
    }
    
    public String getColorPick() {
    	return colorPickText;
    }
}
 