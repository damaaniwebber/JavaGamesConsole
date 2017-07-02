package tictactoe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

@SuppressWarnings("serial")
public class TicTacToe extends JFrame implements ActionListener {

    JPanel panel;
    JButton[] button;
    int count = 0, sign = 0, players = 0, corner = 0, side = 0, corners[] = new int[4], sides[] = new int[4], pos = 0, strategy = 0, nextStrategy = 0;
    boolean win = false;
    String startingPlayer = null;
    Random rand = new Random();

    @SuppressWarnings({"OverridableMethodCallInConstructor", "LeakingThisInConstructor"})
    public TicTacToe() {
        if(getFirstPlayer()){
            panel = new JPanel();
            panel.setLayout(new GridLayout(3, 3));
            panel.setPreferredSize(new Dimension(500, 500));
            this.add(panel);
            button = new JButton[9];
            for (int i = 0; i <= 8; i++) {
                button[i] = new JButton();
                button[i].setEnabled(true);
                button[i].addActionListener(this);
                panel.add(button[i]);
            }
            this.pack();
            this.setDefaultCloseOperation(HIDE_ON_CLOSE);
            this.setVisible(true);
            try {
                if (startingPlayer.equals("C")) {
                    count++;
                    computersTurn("X");
                    sign++;
                }
            } catch (Exception e) {
                // do nothing
                // this means it is a 2 player game
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        count++;
        for (int i = 0; i <= 8; i++) {
            if (button[i] == e.getSource()) {
                if (sign % 2 == 0) {
                    if (startingPlayer != null) {
                        button[i].setText("X");
                        button[i].setEnabled(false);
                        checkWinner();
                        if (win != true && !(count >= 9)) {
                            count++;
                            computersTurn("O");
                            sign++;
                            checkWinner();
                        }
                    } else {
                        button[i].setText("X");
                        button[i].setEnabled(false);
                        checkWinner();
                    }
                } else if (startingPlayer != null) {
                    button[i].setText("O");
                    button[i].setEnabled(false);
                    checkWinner();
                    if (win != true && !(count >= 9)) {
                        count++;
                        computersTurn("X");
                        sign++;
                        checkWinner();
                    }
                } else {
                    button[i].setText("O");
                    button[i].setEnabled(false);
                    checkWinner();
                }
            }
        }

        if (win == true) //System.exit(0);

        if (count >= 9) {
            JOptionPane.showMessageDialog(null, "The game is a draw!");
            //System.exit(0);
        }
        sign++;
    }

    public boolean getFirstPlayer() {
        for (int b = 0; b < 1; b++) {
            String input = JOptionPane.showInputDialog("                                    Tic Tac Toe!" + "\n"
                    + "\n" + "Would you like to play with the computer or another player?"
                    + "\n" + "       (enter 1 to play the computer and 2 for two players)");

            if (input == null) {
                return false;
            } else {
                try {
                    players = Integer.parseInt(input);
                    if (!(players == 1 || players == 2)) {
                        JOptionPane.showMessageDialog(null, "Please enter either values 1 or 2");
                        b = -1;
                    }
                    if (players == 1) {
                        for (int a = 0; a < 1; a++) {
                            startingPlayer = JOptionPane.showInputDialog("Who goes first?" + "\n" + "Enter P for Player and C for computer");
                            if (startingPlayer == null) {
                                return false;
                            } else if (!(startingPlayer.equals("P") || startingPlayer.equals("C"))) {
                                JOptionPane.showMessageDialog(null, "Please enter correct values. Letters must be capitals");
                                a = -1;
                            }
                        }
                    }
                } catch (NumberFormatException | HeadlessException e) {
                    JOptionPane.showMessageDialog(null, "Please enter correct values.");
                    b = -1;
                }
            }
        }
        return true;
    }

    public void checkWinner() {
        // CROSSES
        if (button[0].getText().equals("X") && button[1].getText().equals("X") && button[2].getText().equals("X")) {
            JOptionPane.showMessageDialog(null, "X WINS!");
            win = true;
        } else if (button[3].getText().equals("X") && button[4].getText().equals("X") && button[5].getText().equals("X")) {
            JOptionPane.showMessageDialog(null, "X WINS!");
            win = true;
        } else if (button[6].getText().equals("X") && button[7].getText().equals("X") && button[8].getText().equals("X")) {
            JOptionPane.showMessageDialog(null, "X WINS!");
            win = true;
        } else if (button[0].getText().equals("X") && button[3].getText().equals("X") && button[6].getText().equals("X")) {
            JOptionPane.showMessageDialog(null, "X WINS!");
            win = true;
        } else if (button[1].getText().equals("X") && button[4].getText().equals("X") && button[7].getText().equals("X")) {
            JOptionPane.showMessageDialog(null, "X WINS!");
            win = true;
        } else if (button[2].getText().equals("X") && button[5].getText().equals("X") && button[8].getText().equals("X")) {
            JOptionPane.showMessageDialog(null, "X WINS!");
            win = true;
        } else if (button[0].getText().equals("X") && button[4].getText().equals("X") && button[8].getText().equals("X")) {
            JOptionPane.showMessageDialog(null, "X WINS!");
            win = true;
        } else if (button[2].getText().equals("X") && button[4].getText().equals("X") && button[6].getText().equals("X")) {
            JOptionPane.showMessageDialog(null, "X WINS!");
            win = true;
        }
        // NOUGHTS
        if (button[0].getText().equals("O") && button[1].getText().equals("O") && button[2].getText().equals("O")) {
            JOptionPane.showMessageDialog(null, "O WINS!");
            win = true;
        } else if (button[3].getText().equals("O") && button[4].getText().equals("O") && button[5].getText().equals("O")) {
            JOptionPane.showMessageDialog(null, "O WINS!");
            win = true;
        } else if (button[6].getText().equals("O") && button[7].getText().equals("O") && button[8].getText().equals("O")) {
            JOptionPane.showMessageDialog(null, "O WINS!");
            win = true;
        } else if (button[0].getText().equals("O") && button[3].getText().equals("O") && button[6].getText().equals("O")) {
            JOptionPane.showMessageDialog(null, "O WINS!");
            win = true;
        } else if (button[1].getText().equals("O") && button[4].getText().equals("O") && button[7].getText().equals("O")) {
            JOptionPane.showMessageDialog(null, "O WINS!");
            win = true;
        } else if (button[2].getText().equals("O") && button[5].getText().equals("O") && button[8].getText().equals("O")) {
            JOptionPane.showMessageDialog(null, "O WINS!");
            win = true;
        } else if (button[0].getText().equals("O") && button[4].getText().equals("O") && button[8].getText().equals("O")) {
            JOptionPane.showMessageDialog(null, "O WINS!");
            win = true;
        } else if (button[2].getText().equals("O") && button[4].getText().equals("O") && button[6].getText().equals("O")) {
            JOptionPane.showMessageDialog(null, "O WINS!");
            win = true;
        }
    }

    public void computersTurn(String move) {
        // array stores position of each corner on the Grid/Board
        corners[0] = 0;
        corners[1] = 2;
        corners[2] = 6;
        corners[3] = 8;
        // array stores position of each side on the Grid/Board
        sides[0] = 1;
        sides[1] = 3;
        sides[2] = 5;
        sides[3] = 7;
        switch (count) {
            case 1:
                strategy = 1 + rand.nextInt(2);
                switch (strategy) {
                    case 1:
                        strategySides(move);
                        break;
                    case 2:
                        strategyCorners(move);
                        break;
                    default:
                        System.out.println("unexpected case error of some sort");
                        break;
                }   nextStrategy = (strategy == 1) ? 2 : 1;
                break;
            case 3:
                switch (strategy) {
                    case 1:
                        strategySides(move);
                        break;
                    case 2:
                        strategyCorners(move);
                        break;
                    default:
                        System.out.println("unexpected case error of some sort");
                        break;
                }   break;
            case 5:
                switch (nextStrategy) {
                    case 1:
                        strategySides(move);
                        break;
                    case 2:
                        strategyCorners(move);
                        break;
                    default:
                        System.out.println("unexpected case error of some sort");
                        break;
                }   break;
            default:
                for (int i = 0; i < 1; i++) {
                    int a = rand.nextInt(9);
                    if (button[a].isEnabled() == true) {
                        button[a].setText(move);
                        button[a].setEnabled(false);
                    } else {
                        i = -1;
                    }
                }   break;
        }
    }

    public void strategySides(String move) {
        for (int i = 0; i < 1; i++) {
            side = rand.nextInt(4);
            pos = sides[side];
            if (button[pos].isEnabled() == true) {
                button[pos].setText(move);
                button[pos].setEnabled(false);
            } else {
                i = -1;
            }
        }
    }

    public void strategyCorners(String move) {
        for (int i = 0; i < 1; i++) {
            corner = rand.nextInt(4);
            pos = corners[corner];
            if (button[pos].isEnabled() == true) {
                button[pos].setText(move);
                button[pos].setEnabled(false);
            } else {
                i = -1;
            }
        }
    }
}
