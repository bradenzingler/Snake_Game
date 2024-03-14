////////////////////////////////////////////////////////////////////////////////////////////////////
/*  SnakeGame.java
 *  Created by Braden Zingler on 3/13/2024.
 * 
 *  A snake object used in the snake game. It is instantiated by SnakeGame.java and creates an 
 *  initial snake object to start the game out with. Snake.java also controls the snake movement and
 *  the logic behind the snake eating food to gain length and increase speed.
 */
////////////////////////////////////////////////////////////////////////////////////////////////////
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.Thread;
import java.util.Random;


public class SnakeGame implements KeyListener
{


    // Variables
    private Cell[][] board;
    private JPanel panel;
    JLabel label;
    private Direction direction;
    private Snake snake;
    private int clock = 1;
    private boolean clockOn = true;
    JFrame frame;


    /*
     * Constructor for the game. Initializes a new game.
     */
    public SnakeGame(Cell[][] board, JPanel panel, JFrame frame) 
    {
        this.board = board;
        this.panel = panel;
        this.snake = new Snake(new Cell(15, 15, CellType.SNAKE), board, this);
        this.frame = frame;
        initializeBoard(board);
        draw(board);
    }


    /**
     * Fills board with empty cells, adds cells to grid
     * Randomly spawns food cells
     * @param board reference to the 2D array board
     */
    public void initializeBoard(Cell[][] board) {

        // generate random food cell indices
        Random rand = new Random();

        for (int i = 0; i < 30; i++){
            for (int j = 0; j < 30; j++) {
                if (rand.nextInt(140) == 3) {
                    board[i][j] = new Cell(i, j, CellType.FOOD);
                } else {
                    board[i][j] = new Cell(i, j, CellType.EMPTY);
                }
            }
        }
    }
    

    /**
     * Updates GUI based on board array. 
     * Should be called anytime the board is changed.
     */
    public void draw(Cell[][] board) 
    {
        // Remove snake from screen completely
        panel.removeAll();

        // Redraw board with new snake locations
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                panel.add(board[i][j]); 
            }
        }

        panel.revalidate();
        panel.repaint();
    }


    /**
     * This is where the game starts.
     * @param args
     */
    public static void main(String[] args) 
    {
        startGame();
    }


    /**
     * Starts a new snake game
     */
    public static void startGame() 
    {
        // Initial configuration
        JFrame frame = new JFrame("Snake Game");
        frame.setSize(new Dimension(700, 700));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create layered pane to manage components
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(frame.getSize());

        // Create panel to display board
        JPanel panel = new JPanel(new GridLayout(30, 30));
        panel.setSize(frame.getSize());
        panel.setLocation(0, 0);

        

        // Initialize all instances
        Cell[][] board = new Cell[30][30];
        SnakeGame game = new SnakeGame(board, panel, frame);

        // Create text to display the score
        game.label = new JLabel("Score: 0");
        game.label.setFont(new Font("Sans Serif", Font.PLAIN, 20));
        game.label.setBounds(0, 0, 100, 20 );
        game.label.setBackground(Color.RED);
        layeredPane.add(game.label, JLayeredPane.PALETTE_LAYER);

        // Add layered pane to frame
        layeredPane.add(panel, JLayeredPane.DEFAULT_LAYER);
        frame.add(layeredPane);
        
        // GUI config
        frame.addKeyListener(game); 
        frame.setVisible(true);
        frame.requestFocusInWindow();

        // Start game clock
        game.update();
    }



    /**
     * Game clock to update game continuously
     */
    public void update() 
    {

        try {
            while (clockOn) {
                int clock = getClock();
                int score = snake.getScore();

                // Create text to display the score
                label.setText("Score: " + score);

                // Move and redraw snake based on the board.
                snake.move(board);
                draw(board);

                // Clock frequency between while-loop iterations
                Thread.sleep(170 - clock);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * Gets the value of the clock
     * @param clock
     */
    public void setClock(int clock) {
        this.clock = clock;
    }


    /**
     * Sets the value of the clock
     * @param clock
     */
    public void stopClock(boolean clockOn) {
        this.clockOn = clockOn;
    }


    /**
     * Gets the value of the clock
     * @param clock
     */
    public int getClock() {
        return this.clock;
    }


    /**
     * Ends the game
     */
    public void endGame(int reason) {

        // Stop game clock
        this.stopClock(false);

        panel.removeAll();
        String msg = reason == 1 ? "You hit yourself!" : "You ran into a wall!";

        // Create new game over popup with button to reset
       int response = JOptionPane.showConfirmDialog(panel, msg + "\nPlay again?", "Game Over!", JOptionPane.YES_NO_OPTION);
       if (response == JOptionPane.YES_OPTION){    
            restartGame();
       } else {
            System.exit(1);
       }
    }


    /**
     * Restarts the snake game by closing the existing game and instantiating a new one.
     */
    public void restartGame() {
        frame.dispose();
        startGame();
    }


    /**
     * Handles logic when movement key is pressed.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        Direction curDir = snake.getDirection();

        /* Based on key pressed, move in direction of the key unless that key would cause the snake
        to go into itself.
        */
        switch (e.getKeyCode())
        {
            case (KeyEvent.VK_RIGHT):
                if (curDir != Direction.LEFT) {
                    direction = Direction.RIGHT;
                    snake.setDirection(Direction.RIGHT);
                }
                break;
            case (KeyEvent.VK_UP):
                if (curDir != Direction.DOWN) {
                    direction = Direction.UP;
                    snake.setDirection(Direction.UP);
                }
                break;
            case (KeyEvent.VK_DOWN):
                if (curDir != Direction.UP) {
                    direction = Direction.DOWN;
                    snake.setDirection(Direction.DOWN);
                }
                break;
            case (KeyEvent.VK_LEFT): 
                if (curDir != Direction.RIGHT) {
                    direction = Direction.LEFT;
                    snake.setDirection(Direction.LEFT);
                }
                break;
        }

    }
    // These two methods are not required, but must be included.
    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}
}