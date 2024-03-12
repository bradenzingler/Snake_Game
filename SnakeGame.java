/**
 * Snake game in Java
 * By Braden Zingler
 */
 
// Imports
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class SnakeGame implements KeyListener
{

    private final int BOARDSIZE = 20;
    public SnakeComponent snakeHead;
    public static Snake snake;
    public static Cell[][] board;


    public static void main(String[] args) 
    {
        // Initial configuration
        JFrame frame = new JFrame("Snake Game");
        frame.setSize(new Dimension(900, 700));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
        // Create new components
        JPanel panel = new JPanel(new GridLayout(20, 20));
        board = new Cell[20][20];
        initializeBoard();
        
        

        // Set size and position of panel
        panel.setSize(frame.getSize());
        panel.setLocation(0, 0);
        

        // Add components to frame
        frame.add(panel);
        frame.addKeyListener(new SnakeGame());

        frame.setVisible(true);

    }


    public void initializeBoard(Cell[][] board)
    {
        for (int i = 0; i < BOARDSIZE; i++)
        {
            for (int j = 0; j < BOARDSIZE; j++) 
            {
                board[i][j] = new Cell(i, j, CellType.EMPTY);
            }
        }
    }
    



////////////////////////////// MOVEMENT LOGIC //////////////////////////////////////////////////////


    @Override
    public void keyPressed(KeyEvent e) 
    {
        switch (e.getKeyCode())
        {
            case (KeyEvent.VK_RIGHT):
                snake.moveSnake("right");
                break;
            case (KeyEvent.VK_UP):
                snake.moveSnake("up");
                break;
            case (KeyEvent.VK_DOWN):
                snake.moveSnake("down");
                break;
            case (KeyEvent.VK_LEFT): 
                snake.moveSnake("left");
                break;
        }
    }





    // These two methods are not required.
    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}
}