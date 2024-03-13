////////////////////////////////////////////////////////////////////////////////////////////////////
/*  Snake.java
 *  Created by Braden Zingler on 3/13/2024.
 * 
 *  A snake object used in the snake game. It is instantiated by SnakeGame.java and creates an 
 *  initial snake object to start the game out with. Snake.java also controls the snake movement and
 *  the logic behind the snake eating food to gain length and increase speed.
 */
////////////////////////////////////////////////////////////////////////////////////////////////////
import java.util.LinkedList;
import java.util.Random;


/**
 * The Snake class is used to model the snake in the snake game.
 */
public class Snake
{


////////////////////////////////////////////////////////////////////////////////////////////////////
// Variables
////////////////////////////////////////////////////////////////////////////////////////////////////
    public LinkedList<Cell> snakeComponents = new LinkedList<>();      // List of snake components
    private final int MIN_SNAKE_LENGTH = 3;                            // Starting length of snake
    private Direction direction;                                       // Current direction of snake
    private SnakeGame game;                                            // Reference to SnakeGame



    
    /**
     * Constructor to create the snake
     */
    public Snake(Cell start, Cell[][] board, SnakeGame game) 
    {
        // Create first snake component (the head)
        snakeComponents.addFirst(new Cell(start.row, start.col, CellType.SNAKE));

        // Set initial snake direction and save instance of SnakeGame
        this.direction = Direction.DOWN;
        this.game = game;

        // Update board to reflect first snake components
        updateBoard(board);
    }




////////////////////////////////////////////////////////////////////////////////////////////////////
// SETTER METHODS
////////////////////////////////////////////////////////////////////////////////////////////////////


    /**
     * Updates the snakes direction of movement.
     */
    public void setDirection(Direction dir) {this.direction = dir;}


////////////////////////////////////////////////////////////////////////////////////////////////////
// GETTER METHODS
////////////////////////////////////////////////////////////////////////////////////////////////////


    /**
     * A getter method for the snakes components
     */
    public LinkedList<Cell> getSnakeComponents() {return this.snakeComponents;}


////////////////////////////////////////////////////////////////////////////////////////////////////
// SNAKE LOGIC
////////////////////////////////////////////////////////////////////////////////////////////////////


    /**
     * Creates more food in random locations. A large bound is selected and if the random integer
     * within the bound is some arbitrary integer 3, then set that cell to be food.
     * 
     * @param board: reference to the game board
     */
    public void createFood(Cell[][] board) 
    {
        Random rand = new Random();
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                if (rand.nextInt(600) == 3 && board[i][j].getCellType() == CellType.EMPTY) {
                    board[i][j] = new Cell(i, j, CellType.FOOD);
                }
            }
        }
    }


    /**
     * Increases the length of the snake by one, generates more food in random locations, and 
     * slightly increases the speed of the game clock to make the game harder.
     * 
     * @param board: reference to the game board.
     */
    public void eat(Cell[][] board) 
    {
        // Add new segment to the snake
        Cell segment = snakeComponents.getFirst();
        int newRow = segment.row;
        int newCol = segment.col;
        snakeComponents.push(new Cell(newRow, newCol, CellType.SNAKE));

        // Generate more food
        createFood(board);

        // Increase speed of the snake
        game.setClock(game.getClock() + 5);
    }


    /**
     * If snake hits itself or a wall, the game ends and the snake dies.
     */
    public void die() {game.endGame();}

    
    /**
     * Updates the board with new snake positions.
     * 
     * @param board: Reference to the game board.
     */
    private void updateBoard(Cell[][] board) 
    {
        for (Cell cell : snakeComponents) 
        {
            board[cell.row][cell.col] = cell;
        }
    }

    
    /**
     * Moves the snake to the next cell based on the direction of movement.
     * 
     * @param nextCell - the next cell to move the snake to.
     */
    public void move(Cell[][] board) 
    {
        // Current row and column that the head of the snake is at
        int curRow = snakeComponents.getFirst().row;
        int curCol = snakeComponents.getFirst().col;

        // Calculate the next cell based on the current cell and selected direction
        Cell nextCell;
        switch (direction) 
        {
            case RIGHT:
                nextCell = new Cell(curRow, curCol+1, CellType.SNAKE);
                break;
            case LEFT:
                nextCell = new Cell(curRow, curCol-1, CellType.SNAKE);
                break;
            case DOWN:
                nextCell = new Cell(curRow+1, curCol, CellType.SNAKE);
                break;
            case UP:
                nextCell = new Cell(curRow-1, curCol, CellType.SNAKE);
                break;
            default:
                nextCell = new Cell(curRow-1, curCol, CellType.SNAKE);
                break;
        }

        // Current position
        Cell oldPos = board[curRow][curCol];

        // Check if snake hit food
        if (board[nextCell.row][nextCell.col].getCellType() == CellType.FOOD) {
            eat(board);
        }

        // Check if snake is going to hit the edge
        if (nextCell.row == board[nextCell.row-1].length || nextCell.col == board[nextCell.col-1].length){
            die();
        }
        
        // Set new snake position
        if (oldPos.getCellType() != CellType.FOOD && snakeComponents.size() > MIN_SNAKE_LENGTH)
        {
            Cell tail = snakeComponents.removeLast();
            board[tail.row][tail.col] = new Cell(tail.row, tail.col, CellType.EMPTY);
            oldPos = board[snakeComponents.getFirst().row][snakeComponents.getFirst().col];
        }

        // Update head position
        board[oldPos.row][oldPos.col] = new Cell(oldPos.row, oldPos.col, CellType.EMPTY);
        snakeComponents.push(nextCell);
        board[nextCell.row][nextCell.col] = nextCell;

        // Redraw board with new board positions
        updateBoard(board);
    }
    
}
