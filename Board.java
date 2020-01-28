import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Board extends JPanel implements ActionListener, MouseListener, MouseMotionListener {

    private static final long serialVersionUID = 1L;

    private final int n = 100;  // Number of squares along each side of the board ( row & column have same size therefore its n by n )

    private int generationCount = 0; //Holds the generation number(Reseted to 0 if resetButton is clicked and is 0 at the start)

    private boolean[][] current_board;   // Represents the board.  current_board[r][c] is true if the cell in row r, column c is alive.

    private Color cellColour = Color.RED; //What colour the alive cells will be

    // Displays the grid to the user. Highlighted squares (The colour can change if the source code is altered) are alive, rest are dead.
    private Panel gui;

    private Timer play;  // Drives the simulation when the user presses the "Start" button.

    private JButton StartPauseButton;        // Button for starting and Pausing the simulation
    private JButton nextGenerationButton;    // Button for computing just the next generation.
    private JButton randomiseButton;         // Button for filling the board randomly with each cell having a 10% chance of  being current_board.
    private JButton resetButton;             // Button for clearing the board, that is setting all the cells to "dead".
    private JButton quitButton;              // Button for ending the program.
    private JLabel generationNumber;        // JLabel will display the generation count

    public static void main(String[] args) {
        //Create the frame here
        JFrame frame = new JFrame("Conway's Game Of Life"); //Frame with title
        JPanel panel = new Board(); //Create JPanel in class Game, easier to add everything in the constructor to the panel
        frame.setContentPane(panel); //Container object, can hold AWT Components
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Click the 'X' to close the game
        frame.pack(); //Size the window to fit all components
        frame.setLocation(500, 50); //Location of the frame
        frame.setVisible(true); //Make it visible
    }


    /*
     * Create the game board, initially empty.
     * The number of cells on each side of the grid is n( Size of the grid is n by n).
     */
    private Board() {

        current_board = new boolean[n][n];                          //  Create the current_board with width and height n( n by n).
        setLayout(new BorderLayout(5, 5));                           //  Container has BorderLayout with 5X5 gap between components.
        setBackground(Color.BLACK);                                 //  Background of the component(Panel)
        setBorder(BorderFactory.createLineBorder(Color.YELLOW, 5));  //  Border colour of the component(Border refers to the outer edges of the grid
        int cellSize = 800 / n;                                       //  Size of the cells, 800/n is 8 pixels per Cell, Ideal size
        gui = new Panel(n, n, cellSize, cellSize);                    //  Declare gui as new Panel object with parameters of (n,n,cellSize,cellSize,null,0)
        add(gui, BorderLayout.CENTER);                                //  Add gui to Center using BorderLayout
        JPanel button = new JPanel();                                //  Create a JPanel called buttons, which will contain all the buttons/Labels
        add(button, BorderLayout.SOUTH);                                //  Add buttons Panel to SOUTH

        // Buttons/Labels created here
        resetButton = new JButton("Reset");
        StartPauseButton = new JButton("Start");
        quitButton = new JButton("Quit");
        nextGenerationButton = new JButton("One Step");
        randomiseButton = new JButton("Randomise");
        generationNumber = new JLabel("Generation Count: " + generationCount);


        //Add all buttons/labels to the container (JPanel called button).
        button.add(StartPauseButton);
        button.add(nextGenerationButton);
        button.add(randomiseButton);
        button.add(resetButton);
        button.add(quitButton);
        button.add(generationNumber);


        //Add an action listener to the buttons
        StartPauseButton.addActionListener(this);
        resetButton.addActionListener(this);
        quitButton.addActionListener(this);
        randomiseButton.addActionListener(this);
        nextGenerationButton.addActionListener(this);
        gui.addMouseListener(this);
        gui.addMouseMotionListener(this);

        // play variable has 500millisecond delay(Half a second),
        play = new Timer(500, this);
    }


    //Compute the next generation
    private void nextGeneration() {
        boolean[][] newboard = new boolean[n][n];      // Create a newBoard boolean array that has the same size as "current_board"
        for (int r = 0; r < n; r++) {                // Iterate through the rows
            int above, below;                          // Rows considered above and below row number r
            int left, right;                           // Columns considered left and right of column c
            // The condition, (r > 0), is tested. If it is true the first value, r - 1, is returned. If it is false, the second value, n - 1, is returned.
            above = r > 0 ? r - 1 : n - 1;        // IF NOT first row, THEN check row above ELSE check last row ( n -1 because the grid wrap's around and there is no "last" row)
            below = r < n - 1 ? r + 1 : 0;         // IF NOT last row, THEN check row below, ELSE check first row

            for (int c = 0; c < n; c++) { //Iterate through the columns
                left = c > 0 ? c - 1 : n - 1; // IF NOT first column, THEN check column to the left, ELSE check last column
                right = c < n - 1 ? c + 1 : 0; // IF NOT last column, THEN check column to the right, ELSE check first column

                int counter = 0; // Hold the number of alive cells surrounding the [r][c]
                if (current_board[above][left]) //Top left
                    counter++;
                if (current_board[above][c]) //Directly above
                    counter++;
                if (current_board[above][right]) // Top right
                    counter++;
                if (current_board[r][left]) //Adjacent left
                    counter++;
                if (current_board[r][right]) //Adjacent right
                    counter++;
                if (current_board[below][left]) //Bottom left
                    counter++;
                if (current_board[below][c]) //Directly below
                    counter++;
                if (current_board[below][right]) //Bottom right
                    counter++;

                //ALIVE TO DEAD
                newboard[r][c] = counter == 3; //DEAD TO ALIVE IF NEIGHBOUR COUNT IS 3
            }
        }

        generationCount += 1; //Update generationCount by 1

        generationNumber.setText("Generation Count: " + generationCount); //Show this new generation count


        current_board = newboard; //Current board becomes new board

    }


    /*
     *  Sets the colour of every square in the gui to show whether the corresponding
     *  cell on the Game board is alive or dead.
     */
    private void showBoard() { //Automation of the board
        gui.setAutopaint(false);  // For efficiency, prevent redrawing of individual squares.
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                if (current_board[r][c])
                    gui.setColour(r, c, cellColour); //COLOUR OF THE RECTANGLES IF ALIVE
                else
                    gui.setColour(r, c, Color.BLACK);  // COLOUR OF THE RECTANGLES IF DEAD, defaultColor in the Panel class ( rectangles have same colour as background ).
            }
        }
        gui.setAutopaint(true);  // Redraw the whole board, and turn on drawing of individual squares.

    }


    // Respond to ActionEvent from the buttons
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();  // The object that caused the event.
        if (src == quitButton) { // End the program.
            System.exit(0); //Exit program
        } else if (src == resetButton) {  // Clear the board.
            current_board = new boolean[n][n]; //Create new board, n by n
            generationCount = 0; //Reset generationCount to 0
            generationNumber.setText("Generation Count: " + generationCount); //Show this generation count

            gui.clear(); //Clear the gui object
        } else if (src == nextGenerationButton) {  // Compute and show the next generation.
            nextGeneration(); //Compute new generation
            showBoard(); //Show this new generation
        } else if (src == StartPauseButton) {  // Start or stop the simulation, depending on whether or not it is currently running.
            if (play.isRunning()) {  // If the game is currently running.
                play.stop();  // This stops the simulation by turning off the play that drives the game.
                resetButton.setEnabled(true);  // Some buttons are enabled while simulation stops.
                randomiseButton.setEnabled(true);
                nextGenerationButton.setEnabled(true);
                StartPauseButton.setText("Start");  // Change text of button to "Start", since it can be used to start again.
            } else {  // If the  is not currently running, start it.
                play.start();  // Start the timer that will drive the game.
                resetButton.setEnabled(false);  // Buttons that modify the board are disabled
                randomiseButton.setEnabled(false);
                nextGenerationButton.setEnabled(false);
                StartPauseButton.setText("Pause"); // Change text of button to "Stop", since it can be used to stops.
            }
        } else if (src == randomiseButton) { // Fill the board randomly.
            for (int r = 0; r < n; r++) {
                for (int c = 0; c < n; c++)
                    current_board[r][c] = (Math.random() < 0.10);  // 10% probability that the cell is alive.
            }
            showBoard();
        } else if (src == play) {  // Each time the play fires, a new frame is computed and displayed.

            nextGeneration();
            showBoard();
        }
    }


    /*
     * The square containing the mouse comes to life or, if the right-mouse button is down, dies.
     */
    public void mousePressed(MouseEvent e) {
        if (play.isRunning()) //If game already running then do nothing
            return;
        int row = gui.yCoordToRowNumber(e.getY());      //Get Y position, which row contains the mouse being clicked?,  Y is same on single row
        int col = gui.xCoordToColumnNumber(e.getX()); //Get X position, which column contains the mouse being clicked?, X is same on single column

        // Check if the row variable is within the rowcount and whether col is within the column count
        if (row >= 0 && row < gui.getRowCount() && col >= 0 && col < gui.getColumnCount()) { // Within the boundaries
            if (e.isControlDown()) { //If control is pressed then cell is dead
                gui.setColour(row, col, null); // Colour remains the same
                current_board[row][col] = false; //Cell is set to false/dead
            } else {
                gui.setColour(row, col, cellColour); //Colour of cell changes to white
                current_board[row][col] = true; //Cell is alive
            }
        }
    }

    public void mouseDragged(MouseEvent e) {
        mousePressed(e);  // Dragging the mouse into a square has the same effect as clicking in that square.
    }

    public void mouseClicked(MouseEvent e) {
    }  // Other methods required by the MouseListener and MouseMotionListener interfaces.

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
    }


}