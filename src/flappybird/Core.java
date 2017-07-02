package flappybird;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.Timer;

public class Core implements ActionListener, MouseListener, KeyListener {
    public Timer timer;
    public Random rand = new Random();
    public JFrame jframe;
    public PaintPanel GamePanel;
    // Default JFrame height and width
    public static int WIDTH = 600, HEIGHT = 600;
    // Gap between top and bottom rectangles
    public int gap = 250;
    // width of each rectanle
    public int rWidth = 100;
    // bottom rectangle height
    public int rHeight;
    // space between each column
    public  int space = 300;

    //public Bird b = new Bird();
    public Rectangle bird;// = b.bird;
    
    public int ticks, yMotion, xMotion = 10, score = 0;
    public int ground = 80;
    public ArrayList<Integer> scores;
    public ArrayList<Rectangle> columns;
    public boolean gameOver = false, started;
    
    public Core(){
        // initialise instance of GamePanel class
        GamePanel = new PaintPanel();
        // JFrame set up
        jframe = new JFrame();
        jframe.add(GamePanel);
        jframe.addMouseListener(this);
        jframe.addKeyListener(this);
        jframe.setTitle("Flappy Bird");
        jframe.setSize(WIDTH + 16, HEIGHT + 39);
        jframe.setVisible(true);
        jframe.setResizable(true);
        jframe.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        // Set bird position to center screen and diminsions to 20px
        bird = new Rectangle(WIDTH / 2 - 10, HEIGHT / 2 - 10, 20, 20);
        // ArrayList containing columns to be drawn
        columns = new ArrayList<>();
        addColumn(true);
        addColumn(true);
        addColumn(true);
        addColumn(true);

        // timer for ActionListener, listen every 20 miliseconds
        timer = new Timer(20, this);
        timer.start();
    }
    
    public void addColumn(boolean start) {
        // This method is executed in batches of 4 (every 4 columns)
        // After each execution of this method, 2 rectangles are added to the columns array
        // 2 rectangles constitute to 1 column (bottom & top) divided by a gap
        // (WIDTH + width) = push rectangle off screen to the right
        // (HEIGHT - height - gap) = start drawing top rectangle upwards
        rHeight = 50 + rand.nextInt(250);
        
        if (start) { // initial 4 columns
            // Bottom rectangle
            columns.add(new Rectangle(WIDTH + rWidth + (columns.size() * space), HEIGHT - ground - rHeight, rWidth, rHeight));
            // Top rectangle
            columns.add(new Rectangle(WIDTH + rWidth + ((columns.size() - 1) * space), 0, rWidth, HEIGHT - rHeight - gap));
        } else { // add 1 column at a time
            // Bottom rectangle
            columns.add(new Rectangle(columns.get(columns.size() - 1).x + space * 2, HEIGHT - ground - rHeight, rWidth, rHeight));
            // Top rectangle
            columns.add(new Rectangle(columns.get(columns.size() - 1).x, 0, rWidth, HEIGHT - rHeight - gap));
        }
    }

    public void repaint(Graphics g) {
        // Paint sky
        g.setColor(Color.cyan);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // Paint ground
        g.setColor(Color.yellow);
        g.fillRect(0, HEIGHT - ground, WIDTH, ground);

        // Paint grass
        g.setColor(Color.green);
        g.fillRect(0, HEIGHT - ground, WIDTH, 10);

        // Paint columns
        for (Rectangle column : columns) {
            g.setColor(Color.green.darker());
            g.fillRect(column.x, column.y, column.width, column.height);
        }

        // Paint bird
        g.setColor(Color.red);
        g.fillRect(bird.x, bird.y, bird.width, bird.height);

        // Paint text
        g.setColor(Color.white);
        g.setFont(new Font("Arial", 1, 75));

        if (!started) {
            g.drawString("Click to Start", 70, HEIGHT / 2 - 50);
        }
        if (gameOver) {
            g.drawString("Game Over!", 100, HEIGHT / 2 - 50);
            g.setFont(new Font("Arial", 1, 55));
            g.drawString("Score: " + String.valueOf(score), 205, HEIGHT - 230);
        }
        if (!gameOver && started) {
            g.drawString(String.valueOf(score), WIDTH / 2 - 25, 100);
        }
    }
    
    private void jump() {
        // Reset game
        if (gameOver) {
            bird = new Rectangle(WIDTH / 2 - 10, HEIGHT / 2 - 10, 20, 20);
            yMotion = 0;
            score = 0;

            columns.clear();
            addColumn(true);
            addColumn(true);
            addColumn(true);
            addColumn(true);

            gameOver = false;
        }
        // Start game if new game
        // Otherwise decrease bird coordinates vertically when game is running
        if (!started) {
            started = true;
        } else if (!gameOver) {
            // Resets yMotion to zero so gravity can take effect
            if (yMotion > 0) {
                yMotion = 0;
            }
            // Gravity strength
            yMotion -= 10;
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        ticks++;
        // If game has been started
        if (started) {
            // Move columns left horizontally
            for (int i = 0; i < columns.size(); i++) {
                Rectangle col = columns.get(i);
                col.x -= xMotion;
            }
            // height to move bird up by
            if (ticks % 2 == 0 && yMotion < 15) {
                yMotion += 2;
            }
            // move bird vertically
            bird.y += yMotion;

            for (int i = 0; i < columns.size(); i++) {
                Rectangle col = columns.get(i);
                // If column goes off screen to the left remove it from the ArrayList
                // Generate a new column and add it to the ArrayList
                if (col.x + col.width < 0) {
                    columns.remove(col);
                    addColumn(false);
                }
            }

            for (Rectangle col : columns) {
                if (col.y == 0 && bird.x + bird.width / 2 > col.x + col.width / 2 - 10 && bird.x + bird.width / 2 < col.x + col.width / 2 + 10) {
                    if(!gameOver) score++;
                }
                if (col.intersects(bird)) {
                    gameOver = true;

                    if (bird.x <= col.x) {
                        bird.x = col.x - bird.width;
                    } else if (col.y != 0) {
                        bird.y = col.y - bird.height;
                    } else if (bird.y < col.height) {
                        bird.y = col.height;
                    }
                }
            }
            // Game over if bird touches ground or goes above screen
            if (bird.y > HEIGHT - ground - bird.height || bird.y < 0) {
                gameOver = true;
            }
            // Make bird land on the ground when game over
            if (bird.y + yMotion >= HEIGHT - ground) {
                bird.y = HEIGHT - ground - bird.height;
            }
        }
        GamePanel.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        jump();
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            jump();
        }
    }
}
