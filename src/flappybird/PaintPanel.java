package flappybird;

import java.awt.Graphics;
import javax.swing.JPanel;

// JPanel set up, paints objects onto the panel
public class PaintPanel extends JPanel{
    @Override
    protected void paintComponent(Graphics g){
        FlappyBird.core.repaint(g);
    }
}
