/**
 * Snake object for snake game
 * By Braden Zingler
 */
import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class Snake extends JPanel 
{


    // VARIABLES
    public LinkedList<SnakeComponent> snakeComponents = new LinkedList<>();
    private int length;
    private JPanel panel;
    private SnakeComponent snakeHead;
   



    /*
     * Constructor to create a snake
     */
    public Snake(int length, JPanel panel) {
        this.length = length;
        this.panel = panel;
        drawSnake();
    }

    /*
     * Moves the snake in specified direction
     */
    public void moveSnake(String mode) 
    {

        // The snake segments should always be aligned opposite the direction of movement
        for (int i = snakeComponents.size() - 1; i > 0; i--) 
        {
            SnakeComponent currentSegment = snakeComponents.get(i);
            SnakeComponent next = snakeComponents.get(i + 1);
            currentSegment.snakeX = next.snakeX;
            currentSegment.snakeY = next.snakeY;
        }

        switch(mode)
        {
            case "right":
                panel.setLocation(panel.getX() + 10, panel.getY());
                break;
            case "left":
                panel.setLocation(panel.getX() - 10, panel.getY());
                break;
            case "down":
                panel.setLocation(panel.getX(), panel.getY()+10);
                break;
            case "up":
                panel.setLocation(panel.getX(), panel.getY()-10);
                break;

        }
        panel.repaint();
    }

    


    /*
    * Draws the the entire snake to the screen
    */
    public LinkedList<SnakeComponent> drawSnake() 
    {
        panel.setLayout(new FlowLayout());

        // Create each snake component
        for (int i = 0; i < length; i++) 
        {
            SnakeComponent s = new SnakeComponent(WIDTH/2, HEIGHT/2);
            snakeComponents.add(s);
            panel.add(s);
        }

        panel.revalidate();
        panel.repaint();
        this.snakeHead = snakeComponents.get(0);

        return snakeComponents;
    }




////////////////// GETTER METHODS /////////////////////////////////////////////


    /*
     * A getter method for the snakes length
     */
    public int getLength() 
    {
        return this.length;
    }


    /*
     * A getter method for the head of the snake
     */
    public SnakeComponent getHead() 
    {
        return this.snakeHead;
    }



}
