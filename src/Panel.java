import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;


public class Panel extends JPanel {


    private static final long serialVersionUID = 1L;

    private int rows;                            // The number of rows of squares in the grid.
    private int columns;                         // The number of columns of squares in the grid.
    private Color cellColour = Color.BLACK;                    // Colour of squares, either black or white, depending on condition
    private boolean autopaint = true;            // If true, then when a square's colour is set, repaint is called automatically.
    private Color[][] grid;                      // An array that contains the rectangles' colours.
    private BufferedImage off_screenImg;         // The grid is actually drawn here, then the image is copied to the screen
    private Graphics off_screenGrph;             // Graphics context for drawing to off_screenImg
    private boolean needsRedraw;                 // This is set to true when a change has occurred that changes the appearance of the grid


    /*
     *  Construct a Panel with the specified number of rows and columns of rectangles, and with a specified preferred size for the squares.
     *  Background colour is black
     *  @parameter rows the grid will have this many rows of squares
     *  @parameter columns the grid will have this many columns of squares
     */

    public Panel(int rows, int columns, int cellWidth, int cellHeight) {
        this.rows = rows;                  //Rows variable in this class takes the value from this parameter
        this.columns = columns;            //Columns variable in this class takes the value from this parameter
        grid = new Color[rows][columns];   //Create a colour grid with specified rows and columns
        setBackground(Color.BLACK);        //Set the colour of the background
        setOpaque(true);                   //Paint every pixel within the components bounds

        setPreferredSize(new Dimension((cellWidth * columns), (cellHeight * rows))); //Set the preferred size of the grid

    }


    // Return the number of rows in the panel
    public int getRowCount() {
        return rows;
    }


    // Return the number of columns in the panel
    public int getColumnCount() {
        return columns;
    }

    /*
     *  Set the Colour of the square in the specified row and column.
     *  If the square lies outside the grid, this is simply ignored.
     *  THIS IS FOR INDIVIDUAL SQUARES.
     */

    public void setColour(int row, int col, Color c) {
        if (row >= 0 && row < rows && col >= 0 && col < columns) {
            grid[row][col] = c; // Square in this position has colour c
            if (off_screenImg == null) //If image is empty
                repaint(); //Repaint the component
            else {
                drawSquare(row, col, true); //Draw the individual square as 'true'
            }
        }
    }

    /*
     *  Set all rectangles of the grid to have the specified Colour.
     *  THIS IS TO FILL THE WHOLE GRID ( AT THE START OR WHEN EVERYTHING IS RESET)
     */
    public void fill(Color c) {
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++)
                grid[i][j] = c; //Give every square the same colour, which is parameter c
        forceRedraw();   //Draws the rectangles even if autopaint is false.
    }


    /*
     *   Clear the grid by setting all the colours to null.
     *   THIS IS CALLED WHEN RESET IS PRESSED
     */
    public void clear() {
        fill(null);
    }


    /*
    When this property is true, then every call to the setColour methods automatically results in repainting that square on the screen.
    When the value is false, colour changes are recorded in the data for the grid but are not made on the screen.
    When the autopaint property is reset to true, the changes are applied and the entire grid is repainted.
    The default value of this property is true.
    Note that clearing or filling the grid will cause an immediate screen update, even if autopaint is false.
  */
    public void setAutopaint(boolean autopaint) {
        if (this.autopaint == autopaint)
            return; //If the autopaint variable in the class is equal to the parameter then do nothing( if true )
        this.autopaint = autopaint; //Make the current autopaint variable equal the parameter
        if (autopaint)  //If this is true then force to redraw
            forceRedraw();
    }

    public boolean getAutopaint() {
        return autopaint;
    }

    /*
     * This method can be called to force redrawing of the entire grid while the autopaint variable does not need to be change.
     */
    final public void forceRedraw() {
        needsRedraw = true;
        repaint();
    }


    /*
     * Given an x-coordinate of a pixel in the panel, this method returns the row number of the grid square that contains that pixel.
     */
    public int xCoordToColumnNumber(int x) {

        //Get the whole width and divide by the number of columns to get each column width
        double colWidth = (double) (getWidth()) / columns;

        //calculate whether the col is within the number of columns, if yes then return the col number else return how many column there are
        int col = (int) ((x) / colWidth);
        if (col >= columns)
            return columns;
        else
            return col;
    }

    /*
     * Given a y-coordinate of a pixel in the grid, this method returns the column number of the grid square that contains that pixel.
     *
     */
    public int yCoordToRowNumber(int y) {

        //Get the full height and divide by the number of rows to get each row height.
        double rowHeight = (double) (getHeight()) / rows;

        //Divide the y position by the rowheight to get the row variable which is the row number
        int row = (int) ((y) / rowHeight);
        if (row >= rows) //if row is less than the number of rows
            return rows; //return the total rows variable
        else
            return row; //return the row
    }

    /*
     *  Returns the BufferedImage that contains the actual image of the grid.
     *
     */
    public BufferedImage getImage() {
        return off_screenImg;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g); //invoke the overridden method through the use of the keyword super

        if ((off_screenImg == null) || off_screenImg.getWidth() != getWidth() || off_screenImg.getHeight() != getHeight()) {
            //Create new image with the width and height of the component with 8-bit RGB imageType
            off_screenImg = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
            off_screenGrph = off_screenImg.getGraphics(); //Get graphics component of the image
            needsRedraw = true;
        }

        if (needsRedraw) {
            for (int r = 0; r < rows; r++)
                for (int c = 0; c < columns; c++)
                    drawSquare(r, c, false); //All cells become dead, and the colour is null(black)
            needsRedraw = false;
        }
        g.drawImage(off_screenImg, 0, 0, null); //Image to be drawn start from x,y
    }

    synchronized private void drawSquare(int row, int col, boolean callRepaint) {
        // Draw one of the squares in a specified graphics context.

        // G must be non-null and (row,col) must be in the grid.
        if (callRepaint && !autopaint)
            return; //Do nothing if callRepaint is true AND autopaint is true

        //Get each rowHeight by dividing the total height by the number of rows
        double rowHeight = (double) (getHeight()) / rows;

        //Get each colWid by dividing the total wid by the number of columns
        double colWidth = (double) (getWidth()) / columns;

        //Y represents the number of rows per rowHeight
        int y = (int) Math.round(rowHeight * row);


        //X represents the number of columns per colWidth
        int x = (int) Math.round(colWidth * col);


        Color c = grid[row][col]; //Create colour grid equal to the grid[r][c] to hold the colour


        off_screenGrph.setColor((c == null) ? cellColour : c); //If c is null then default colour, else c

        off_screenGrph.fillRect(x, y, 8, 8); //Colour the square with position x,y and size 8

        if (callRepaint) //If repaint is true
            repaint(x, y, 8, 8); //Repaint with x,y and cellSize 8


    }


}