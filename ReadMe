### Conway's Game of Life

# Task
The GOL simulation takes place on an n by n grid of cells or squares where each square can be alive or dead (two states). 
The state of the board (state of all of the cells) is transformed each move (or time period) by
determining if a cell (or square) remains alive or dead (or changes) by applying a set of very simple rules.
These rules look at the current state of each cell and the state of the cells neighbours and then determines if
it dies (becomes dead if alive) or comes back to life (becomes alive if dead); a new board/grid is then
generated. The “Game of Life” is typically a graphical animation of a sequence of these board configurations. 


Within the GOL, the board is infinitely large. This is obviously impractical for a computer based
implementation, thus it is usually restricted to an n by n grid. However this means that those cells on the
edges or in corners would have less than eight neighbours. To get around this problem the neighbourhood
“wraps around” on the board, i.e. neighbours for a square at the very top of a board include some of the ones
at the bottom of the board and far left wraps around to the right etc…

## Rules 
1) Dead to Alive: Only if three of the eight neighbours are alive
2) Alive to Dead: less than two neighbours alive or more than three


## Summary
Using Java Swing library within the Eclipse IDE, a graphical user interface was implemented to visualize the 
simulation through the various iterations. The project was of part the final year assessment for my programming module and I 
received over 90% for the task. 

Although the interactions of cells with neighbours was simple, the challenge for me was to use OOP techniques to create and
deal with objects effectively. 

As an additional challenge, rather than having a random inital arrangement of dead/alive cells, I attemped to programme
in a way that allows users to "Select" which cells are alive at the start by "toggling" the squares using their mouse.

Full list of requirements that were fulfilled are;
1) Storing the grid and counts internally as an array [or similar].
2) Creating and displaying a GOL grid, starting with a random arrangement of alive and dead cells, using
a different colour for alive and dead [Dead cells = Black/ Alive Cells = Red]. The size of the grid can be set as a variable 
within the code or user specified.
3) The use of a start, pause and reset button to start the simulation, pause the simulation and reset the
simulation to a starting position.
4) The displaying of each new grid [using the neighbourhood counts and transition rules] of the
simulation as each stage iterates.
5) The displaying of the iteration number as the simulation is displayed. Note that you should show
each stage of the simulation for a period of time otherwise the screen might be updated too quickly.
When the simulation is reset this iteration number should be reset to zero.
6) Allowing the user to select the starting arrangement by using the mouse, i.e. clicking which cells start
as alive. A system to allow the state of the cell to “toggle” between alive and dead using the mouse. 
(Press CTRL and click mouse on MAC, right click on PC to turn alive cells to dead cells).

## Images
![](Images/Screenshot%202019-08-08%20at%2020.07.19.png). 
![](https://github.com/GandalfTheJava/GameOfLife/blob/master/Images/Screenshot%202019-08-08%20at%2020.09.20.png)







