package snake;
    
import java.awt.Graphics;
import javax.swing.JPanel;

public class PaintPanel extends JPanel{
    @Override
    protected void paintComponent(Graphics g){
        Game.snake.paint(g);
    }
}