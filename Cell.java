////////////////////////////////////////////////////////////////////////////////////////////////////
/*  Cell.java
 *  Created by Braden Zingler on 3/13/2024.
 * 
 *  Represents a square in the game board which can have certain attributes which will
 *  determine its color.
 */
////////////////////////////////////////////////////////////////////////////////////////////////////
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;



/**
 * The Cell class extends JPanel and represents each object in a grid as a 2D array.
 */
public class Cell extends JPanel 
{


////////////////////////////////////////////////////////////////////////////////////////////////////
// Variables
////////////////////////////////////////////////////////////////////////////////////////////////////
    private CellType cellType; 
    public int row;
    public int col;
    



    /**
     * Cell constructor to initialize a cell.
     * 
     * @param row: the row index of this cell.
     * @param col: the column index of this cell.
     * @param type: the type of cell, specified by CellType.java.
     */
    public Cell(int row, int col, CellType type) 
    {
        // Initialize this cells attributes
        this.cellType = type;
        this.row = row;
        this.col = col;

        // Set cell colors based on the cells type
        if (cellType.equals(CellType.SNAKE)) setBackground(Color.GREEN);
        if (cellType.equals(CellType.FOOD)) setBackground(Color.RED);
        if (cellType.equals(CellType.EMPTY)) setBackground(Color.WHITE);
    }




////////////////////////////////////////////////////////////////////////////////////////////////////
// GETTER AND SETTER METHODS
////////////////////////////////////////////////////////////////////////////////////////////////////


    /**
     * Setter method to set this cells cell type.
     * 
     * @param type: the type of cell to set the cell to
     */
    public void setCellType(CellType type) { this.cellType = type;}


    /**
     * Getter method to get this cells cell type.
     */
    public CellType getCellType() { return this.cellType;}


}
