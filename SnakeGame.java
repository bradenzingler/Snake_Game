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
    public Cell[][] board;
    private JPanel panel;
    private Direction direction;
    public Snake snake;
    public int clock = 1;




    /*
     * Constructor for the game. Initializes a new game.
     */
    public SnakeGame(Cell[][] board, JPanel panel) 
    {
        this.board = board;
        this.panel = panel;
        this.snake = new Snake(new Cell(15, 15, CellType.SNAKE), board, this);
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
     * Updates GUI based on updated board array. 
     * Should be called anytime the board is changed.
     */
    public void draw(Cell[][] board) 
    {
        panel.removeAll();

        // redraw board
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                panel.add(board[i][j]); 
            }
        }

        panel.revalidate();
        panel.repaint();
    }




    /**
     * Game engine of snake. This is where the game starts.
     * @param args
     */
    public static void main(String[] args) 
    {
        // Initial configuration
        JFrame frame = new JFrame("Snake Game");
        frame.setSize(new Dimension(590, 560));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create panel to display board
        JPanel panel = new JPanel(new GridLayout(30, 30));
        panel.setSize(frame.getSize());
        panel.setLocation(0, 0);

        // Initialize all instances
        Cell[][] board = new Cell[30][30];
        SnakeGame game = new SnakeGame(board, panel);
        
        // GUI config
        frame.add(panel);
        frame.addKeyListener(game); 
        frame.setVisible(true);
        frame.requestFocusInWindow();

        // Start game clock
        game.update();
    }



    /*
     * Game clock to update game continuously
     */
    public void update() {
        try {
            while(true) 
            {
                int clock = getClock();
                Direction d = direction;
                snake.move(board);
                draw(board);
                Thread.sleep(200 - clock);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }




    /**
     * Sets the value of the clock
     * @param clock
     */
    public void setClock(int clock) {
        this.clock = clock;
    }




     /**
     * Gets the value of the clock
     * @param clock
     */
    public int getClock() {
        return this.clock;
    }





    /*
     * Ends the game
     */
    public void endGame() {
        System.out.println("Game over");
    }




    /**
     * Handles logic when movement key is pressed.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode())
        {
            case (KeyEvent.VK_RIGHT):
                direction = Direction.RIGHT;
                snake.setDirection(Direction.RIGHT);
                break;
            case (KeyEvent.VK_UP):
                direction = Direction.UP;
                snake.setDirection(Direction.UP);
                break;
            case (KeyEvent.VK_DOWN):
                direction = Direction.DOWN;
                snake.setDirection(Direction.DOWN);
                break;
            case (KeyEvent.VK_LEFT): 
                direction = Direction.LEFT;
                snake.setDirection(Direction.LEFT);
                break;
        }

    }




    // These two methods are not required.
    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}
}