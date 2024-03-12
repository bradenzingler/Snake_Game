/**
 * Creates a snake component to represent each segment of the snakes body
 * By Braden Zingler
 */

import javax.swing.*;
import java.awt.*;
 

public class SnakeComponent extends JPanel {

    // Variables
    final private int SIZE = 35;
    public int snakeX, snakeY;


    /**
     * SnakeComponent constructor
     * @param snakeX components X position in pixels
     * @param snakeY components Y position in pixels
     */
    public SnakeComponent(int snakeX, int snakeY) 
    {
        this.snakeX = snakeX;
        this.snakeY = snakeY;
        setPreferredSize(new Dimension(SIZE, SIZE));
    }



    /**
     * Paints the actual component to the screen and adds a reference to it in an array
     * @param g graphics reference
     */
    @Override
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        g.setColor(Color.GREEN);
        g.fillRect(snakeX, snakeY, SIZE, SIZE);
    }
}
