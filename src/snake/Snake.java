package snake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.Timer;
import static javax.swing.WindowConstants.HIDE_ON_CLOSE;

public class Snake implements ActionListener, KeyListener {
    
    JFrame frame;
    PaintPanel panel;
    Label lbl_score, lbl_highscore, lbl_gameover, lbl_restart;
    Timer timer;
    ArrayList<Rectangle> snake = new ArrayList<>(), border = new ArrayList<>();
    Rectangle food, head, tail;
    boolean started = false;
    final int width = 20, height = 20, x = 200, y = 200;
    int score;
    
    enum Direction{
        Left, Right, Up, Down, Stop
    }
    
    Direction direction;

    public Snake() {
        panel = new PaintPanel();
        frame = new JFrame();
        lbl_gameover = new Label("", Label.CENTER);
        lbl_gameover.setBackground(Color.white);
        panel.add(lbl_gameover);
        frame.add(panel);
        lbl_gameover.setLocation(50, 200);
        frame.addKeyListener(this);
        frame.setSize(new Dimension(800, 459));
        frame.setVisible(true);
        frame.setDefaultCloseOperation(HIDE_ON_CLOSE);
        
        for (int i = 0; i < 4; i++) {
            border.add(new Rectangle(0, 0, width, 420));
            border.add(new Rectangle(625, 0, width, 420));
            border.add(new Rectangle(0, 0, 645, height));
            border.add(new Rectangle(0, 400, 645, height));
        }

        InitialiseGame();
        
        timer = new Timer(200, this);
        timer.start();
    }
    
    private void InitialiseGame(){
        
        for(int x = 0; x < 125; x += 25){
            snake.add(new Rectangle(195 - x + 5, 200, width, height));
            snake.add(new Rectangle(195 - x, 200, width, height));
        }
        
        score = 0;
        
        GenerateFood();
        
    }
    
    private void ResetGame(){
        snake.clear();
        InitialiseGame();
    }

    public void paint(Graphics g){
        g.setColor(Color.white);
        g.fillRect(0, 0, 645, 420);
        g.fillRect(645, 0, 139, 420);
        
        g.setColor(Color.black);
        
        border.stream().forEach(rec -> {
            g.fillRect(rec.x, rec.y, rec.width, rec.height);
        });

        snake.stream().forEach(rec -> {
            g.fillRect(rec.x, rec.y, rec.width, rec.height);
        });
        
        g.fillRect(food.x, food.y, food.width, food.height);
    }

    private void GenerateFood(){
        int a = new Random().nextInt(500);
        int b = new Random().nextInt(500);
        
        if(a % 25 == 0 && b % 25 == 0){
            food = new Rectangle(a + 5, b + 5, 10, 10);
            snake.stream().forEach(rec -> {
                if (Intersection(food, rec)) GenerateFood();
            });
            border.stream().forEach(rec -> {
                if (Intersection(food, rec)) GenerateFood();
            });
            if (food.x > 620 || food.y > 395) {
                GenerateFood();
            }
        }else{
            GenerateFood();
        }
    }
    
    boolean Intersection(Rectangle a, Rectangle b){
        return a.intersects(b);
    }
    
    boolean Intersection(){
        for (int i = 2; i < snake.size(); i++) {
            if(Intersection(snake.get(0), snake.get(i))){
                return true;
            }
        }
        
        for(int i = 0; i < border.size() - 1; i++) {
            if(Intersection(snake.get(0), border.get(i))){
                return true;
            }
        };
        
        return false;
    }
    
    void move(int x, int y, int w, int h){
        head = snake.get(0);
        snake.add(0, new Rectangle(head.x + x, head.y + y, w, h));
        snake.remove(snake.size() - 1);
        
        head = snake.get(0);
        snake.add(0, new Rectangle(head.x + (x/4), head.y + (y/4), w, h));
        snake.remove(snake.size() - 1);
    }
    
    boolean FoodEaten(){
        return snake.get(0).intersects(food);
    }
    
    void grow(){
        tail = snake.get(snake.size() - 1);
        snake.add(new Rectangle(tail.x, tail.y, width, height));
        snake.add(new Rectangle(tail.x, tail.y, width, height));
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (FoodEaten()) {
            score += 10;
            GenerateFood();
            grow();
        }
        if (Intersection()) {
            lbl_gameover.setText("GAME OVER");
            lbl_gameover.setSize(100, 100);
            lbl_gameover.setLocation(50, 200);
        } else if (started) {
            switch(direction){
                case Left:
                    move(-20, 0, width, height);
                    break;
                case Right:
                    move(20, 0, width, height);
                    break;
                case Up:
                    move(0, -20, height, width);
                    break;
                case Down:
                    move(0, 20, height, width);
                    break;
                case Stop:
                    break;
            }
        }
        panel.repaint();
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        started = true;
        switch(e.getKeyCode()){
            case KeyEvent.VK_LEFT:
                if (direction != Direction.Right) direction = Direction.Left;
                break;
            case KeyEvent.VK_RIGHT:
                if (direction != Direction.Left) direction = Direction.Right;
                break;
            case KeyEvent.VK_UP:
                if (direction != Direction.Down) direction = Direction.Up;
                break;
            case KeyEvent.VK_DOWN:
                if (direction != Direction.Up) direction = Direction.Down;
                break;
            case KeyEvent.VK_SPACE:
                GenerateFood();
            default:
                direction = Direction.Stop;
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}

