package nonogram;

import java.awt.Color;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import java.util.Observable;
import java.util.Scanner;
import java.util.Stack;
import javax.swing.JButton;

/**
 * Write a description of class Panel here.
 */
@SuppressWarnings({"deprecation", "serial"})
public class NonogramPanel implements Observer {
    /**
     * Constructor for Nonogram gird of PanelCell with clear, undo, save,
     * undo buttons and status area for user
     */
    public NonogramPanel() {

        Arrays.fill(isthisSelected, true);
        // Grid set up
        JFrame frame = new JFrame("Nonogram Game");
        wholeGame=new JPanel();
        //backGround = new JPanel(); //outer JPanel
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setSize(450,400); //need to expand size to see button layout
        
        frame.setLayout(new BorderLayout());
        
        Scanner scnr = new Scanner(System.in);
        Scanner fs = null;
        try {
            fs = new Scanner(new File(NGFILE));
        } catch (FileNotFoundException e) {
            System.out.println(NGFILE + "not found");
        }
        puzzle = new Nonogram(fs);
       // the puzzle

        stack = new Stack<Assign>();

        int state, row, col;
        int[] colArray = new int[10]; //declares array of size 10 for column size to store col arrays
        int[] rowArray = new int[10]; //declares array of size 10 for row size to store col arrays
        //Random random = new Random(); //declare random object
        
        grid = new JPanel(new GridLayout(Nonogram.MIN_SIZE, Nonogram.MIN_SIZE));
        cells = new PanelCell[Nonogram.MIN_SIZE ][Nonogram.MIN_SIZE ];
            for (row = 0; row < Nonogram.MIN_SIZE; row++) {
            for (col = 0; col < Nonogram.MIN_SIZE; col++) {
                cells[row][col] = new PanelCell(this, row, col);
                grid.add(cells[row][col]);
            }
        }
        //backGround.setLayout(new BorderLayout());
        //backGround.setPreferredSize(new Dimension(300, 200));
       // backGround.add(grid, BorderLayout.NORTH);
       
       
        wholeGame.setLayout(new BorderLayout());
        wholeGame.add(grid, BorderLayout.CENTER);

        
        /*Create new clear JButton object.
         & set up of buttons and status bar */
        clear = new JButton("Clear");
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                for (int row=0; row<Nonogram.MIN_SIZE; row++)
                    for (int col=0; col<Nonogram.MIN_SIZE; col++)
                        cells[row][col].clear();
                Arrays.fill(isthisSelected, true);
                Arrays.fill(puzzleIsSolved, false);
                clear();
            }
        });
        
        //Create new undo JButton object.
        undo = new JButton("Undo");
        undo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                undo();
            }
        });
        
        //Create new save JButton object.
        save = new JButton("Save");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                save();
            }
        });
        
        //Create new load JButton object.
        load = new JButton("Load");
        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                load();
            }
        });
        
        //Create new help JButton object.
        help = new JButton("help");
        help.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                help();
            }
        });
        
        //Create new quit JButton object.
        quit = new JButton("quit");
        quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                quit();
            }
        });

        JPanel button = new JPanel(new GridLayout());
        
        /////////////////////////////////////////////////////////////////////////////////////LABEL NUMS COLS START\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
        
        //declare an array of labels
        JLabel[] numberCol = new JLabel[5];
        //declare panels which will hold the labels
        JPanel numsPanelCol = new JPanel();
        
        numsPanelCol.setLayout(new BoxLayout(numsPanelCol, BoxLayout.Y_AXIS)); //set box layout for new panel
        
        int spacingCol = 40; //vertical spacing num
        
        for (int i = 0; i < 5; i++)
        {
            //deal with logic
            numberCol[i] = logic(colArray, numberCol[i], i);
            
            //horizontally aligns each label
            numberCol[i].setAlignmentX(Component.CENTER_ALIGNMENT);
            
            //creates a vertical spacing between each label
            switch(i) //checks each labels
            {
                case 0: //label 1
                    numsPanelCol.add(Box.createVerticalStrut(spacingCol-20));
                    numsPanelCol.add(numberCol[i]); //add label to the new panel
                    numsPanelCol.add(Box.createVerticalStrut(spacingCol+10));
                    break;
                case 1: //label 2
                    numsPanelCol.add(numberCol[i]); //add label to the new panel
                    numsPanelCol.add(Box.createVerticalStrut(spacingCol+10));
                    break;
                case 2: //label 3
                    numsPanelCol.add(numberCol[i]); //add label to the new panel
                    numsPanelCol.add(Box.createVerticalStrut(spacingCol));
                    break;
                case 3: //label 4
                    numsPanelCol.add(numberCol[i]); //add label to the new panel
                    numsPanelCol.add(Box.createVerticalStrut(spacingCol+10));
                    break;
                case 4: //label 5
                    numsPanelCol.add(numberCol[i]); //add label to the new panel
                    numsPanelCol.add(Box.createVerticalStrut(spacingCol));
                    break;
            }
        }
        
        
         /////////////////////////////////////////////////////////////////////////////////////LABEL NUMS COLS END\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
         
         /////////////////////////////////////////////////////////////////////////////////////LABEL NUMS ROWS START\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
         
         //declare an array of labels
        JLabel[] numberRow = new JLabel[5];
        //declare panels which will hold the labels
        JPanel numsPanelRow = new JPanel();
        
        numsPanelRow.setLayout(new BoxLayout(numsPanelRow, BoxLayout.X_AXIS)); //set box layout for new panel
        
        int spacingRow = 40; //vertical spacing num
        
        for (int i = 0; i < 5; i++)
        {
            //deal with logic
            numberRow[i] = logic(rowArray, numberRow[i], i);
            
            //horizontally aligns each label
            numberRow[i].setAlignmentY(Component.CENTER_ALIGNMENT);
            
            //creates a vertical spacing between each label
            switch(i) //checks each labels
            {
                case 0: //label 1
                    numsPanelRow.add(Box.createHorizontalStrut(spacingRow+10));
                    numsPanelRow.add(numberRow[i]); //add label to the new panel
                    numsPanelRow.add(Box.createHorizontalStrut(spacingRow+32));
                    
                    if(rows[i] == 3)
                    {
                        
                    }
                    break;
                case 1: //label 2
                    numsPanelRow.add(numberRow[i]); //add label to the new panel
                    numsPanelRow.add(Box.createHorizontalStrut(spacingRow+31));
                    break;
                case 2: //label 3
                    numsPanelRow.add(numberRow[i]); //add label to the new panel
                    numsPanelRow.add(Box.createHorizontalStrut(spacingRow+31));
                    break;
                case 3: //label 4
                    numsPanelRow.add(numberRow[i]); //add label to the new panel
                    numsPanelRow.add(Box.createHorizontalStrut(spacingRow+32));
                    break;
                case 4: //label 5
                    numsPanelRow.add(numberRow[i]); //add label to the new panel
                    numsPanelRow.add(Box.createHorizontalStrut(spacingRow));
                    break;
            }
            
        }
        
         
         /////////////////////////////////////////////////////////////////////////////////////LABEL NUMS ROWS END\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
         
         //To have a nested north borderlaout panel, we will create a north panel
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(numsPanelRow, BorderLayout.SOUTH);
        northPanel.add(new JScrollPane(status), BorderLayout.CENTER);
        
        wholeGame.add(northPanel, BorderLayout.NORTH);
        wholeGame.add(numsPanelCol, BorderLayout.WEST); //add numberPanel to wholeGame panel for it to come together
        wholeGame.add(button, BorderLayout.SOUTH);
        frame.add(wholeGame, BorderLayout.CENTER);
        //wholeGame.add(numsPanelRow, BorderLayout.NORTH); //add numberPanel to wholeGame panel for it to come together
            
        button.add(clear);
        button.add(undo);
        button.add(save);
        button.add(load);
        button.add(help);
        button.add(quit);
        
        
        status = new JTextArea();
        status.setEnabled(false);

        puzzle.addObserver(this);
        frame.setVisible(true);
        //frame.setPreferredSize(new Dimension(450, 800));
        frame.setResizable(false);
    }
    
    //logic Algorithm for colArray Num Labels by matching it to the Row & Col nums
    public JLabel logic(int[] Array, JLabel num1, int labelNum)
    {
        int chosenNum = 1; //chosen num to create a specific design for the nonogram
        for (int i = 0; i < Array.length; i++) //chooses a num from the array
        {
             Array[i] = 1;
            
        }

        switch(labelNum)
        {
            case 0:
                num1 = new JLabel(""+chosenNum); 
                return num1;
            case 1:
                num1 = new JLabel(chosenNum+", "+chosenNum);
                return num1;
            case 2:
                num1 = new JLabel(chosenNum+", "+chosenNum);
                return num1;
            case 3:
                num1 = new JLabel(chosenNum+", "+chosenNum);
                return num1;
            case 4:
                num1 = new JLabel(""+chosenNum);
                return num1;
        }
        return num1;
    }
    
    /**
     * Panel cells gets updated when cells are assigned
     */
    @Override
    public void update(Observable o, Object arg) {
        if (arg == null) throw new NonogramException("arg (cell) is null");
        Cell c = (Cell) arg;
        cells[c.getRow()][c.getCol()].isChanged(c.getState());
    }

    /**
     * resets the game
     */
    private void clear() {
       puzzle.clear();
       puzzle.addObserver(this);
       stack= new Stack<>();
    }

    /**
     * Load the game
     */
    private void load() {
        try {
            Scanner readscanner = new Scanner(new File(FILENAME));
            clear();
            while (readscanner.hasNextInt()) {
                Assign w = new Assign(readscanner);
                puzzle.setState(w);
                stack.push(w);
            }
            readscanner.close();
            System.out.println("Game has loaded");
        } catch (IOException e) {
            System.out.println("Error occured");
        }
    }

    /**
     * Undo last move
     */
    private void undo() {
        if (stack.empty()) return;

        Assign top = stack.pop();
        Stack<Assign> previousStack = stack;
        clear();
        for (Assign x : previousStack) {

            puzzle.setState(x);
            stack.add(x);
        }
    }

    /**
     * Save game as (FILENAME)
     */
    private void save() {
    try     {
    PrintStream ps = new PrintStream(new File(FILENAME));
    for (Assign x : stack)
        ps.println(x.toStringForFile());
    ps.close();
    System.out.println ("game SAVED");
    } catch (IOException e)
    {
      System.out.println ("error has occured");
    }
    }
    
    private void help() {
        //attach each text to the end of the next using the StringBuilder method
        StringBuilder help = new StringBuilder();
        help.append("Nonogram is a puzzle where you must colour in/fill in the grid according to the patterns\n"
        + "of contiguous full cells given in the rows and columns.  Full cells are shown as '@'\n"
        + "unknown cells as the 'Green' colour, and cells you are sure are empty as 'Green' again.\n"
        + "If a row or column is invalid (doesn't match the pattern) this will be marked with a 'x'\n"
        + "Once the puzzle is complete their is a pop up box that congratulates you!");
        
        
        JOptionPane.showMessageDialog(null, help, "HELP!", JOptionPane.INFORMATION_MESSAGE); //creates Option Pane message dialog and display 'StringBuilder' text.
    }
    
    private void quit() {
        int option = 0; //declare option
        
        option = JOptionPane.showConfirmDialog(null, "Are You Sure?", "QUIT?", JOptionPane.YES_NO_OPTION); //give it a JOptionPane confirmation box
        
        //if first option (0 Indexed) which is Yes is chosen...
        if (option == 0)
        {
            System.exit(1); //... then quit the game
        }
    }

    /**
     * Set status
     */
    void setStatus(String s) {
        
    }
    

    /**
     * Makes a user move on a particular cell, and where
     * appropriate uses player to make the corresponding computer move
     *
     * @param row   the cell row
     * @param col   the cell column
     * @param state the cell state
     */
    void makeMove(int row, int col, int state) {
        if ((row < 0) || (row > Nonogram.MIN_SIZE)) throw new NonogramException("invalid row (" + row + ")");
        if ((col < 0) || (col > Nonogram.MIN_SIZE)) throw new NonogramException("invalid col (" + col + ")");
        if (!Cell.isValidState(state)) throw new NonogramException("invalid state (" + state + ")");
        Assign userMove = new Assign(row, col, state);
        puzzle.setState(userMove);   //updates the puzzle state
        stack.add(userMove);    //use add to ad the next part
        status.setText("The user has made a move" + userMove); //need to add user move to showcase the actual move
        
        this.state = state;
        
        rows = puzzle.getRowNums(row); //gets and stores row values.
        cols = puzzle.getColNums(col); //gets and stores col values.
        
        puzzleSolved(row, col);
    }
    
    /**
     * Checks for selected cells
     * if they match the puzzle, row & col nums
     * or not
     */
    void puzzleSolved(int row, int col)
    {
        
        wrongCellSelected(row, col); //checks for, if the incorrect cells are selected
        //bool to store all correctly selected puzzles
        boolean allTrue;
        
        //to store selected cell row & col
        int focusRow;
        int focusCol;
        
        // loops through the wrong cells patterns when an x is chosen
        for (int i = 0, j = 8; i < isthisSelected.length; i++)
        {
            puzzleIsSolved[j] = isthisSelected[i]; //populate the full puzzle grid
            j++;
        }   
        
        //declare a two-dim array of type int
        //store the values of the cell coordinates (rows/cols)
        int[][] focusCells = 
        {
            {4, 2}, {3, 1}, {2, 0}, {1, 1}, {0, 2},
            {1, 3}, {2, 4}, {3, 3}
        };
        
        //loop through each cell
        for (int i = 0; i < focusCells.length; i++) 
        {
            focusRow = focusCells[i][0];
            focusCol = focusCells[i][1];
            
            //if row&col parsed is equal to selected row&col
            //pass focus row and col to isRowSolved
            //then return true incrementally to the selected puzzleIsSolved boolean in the current 'i' position in the array.
            if (row == focusRow && col == focusCol) 
            {
                if (state == 1) {
                    puzzle.isRowSolved(focusRow);
                    puzzle.isColSolved(focusCol);
                    puzzleIsSolved[i] = true;
                } 
                else 
                {
                    puzzleIsSolved[i] = false; //else give it a false value
                }
            }
        }
        
        allTrue = AllTrue(puzzleIsSolved); //get value for if all values are true or not
        
        
        if (allTrue) 
        { 
            puzzle.isSolved();
            option.showMessageDialog(null, "Well done! You've Solved the Puzzle!","COMPLETED!", JOptionPane.INFORMATION_MESSAGE);
        
            Arrays.fill(puzzleIsSolved, false);
        }
    }
    
    //calls wrongCellSelected in the puzzleSolved method
    void wrongCellSelected(int row, int col)
    {
        
        boolean allTrue;
        
        //declare a two-dim array of type int
        //store the values of the cell coordinates (rows/cols)
        int[][] focusCells = 
        {
            {4, 0}, {4, 1}, {4, 3}, {3, 0},
            {3, 2}, {3, 4}, {2, 1}, {2, 2},
            {2, 3}, {1, 0}, {1, 2}, {1, 4},
            {0, 0}, {0, 1}, {0, 3}, {0, 4}
        };
        
        for (int i = 0; i < focusCells.length; i++) 
        {
            int focusRow = focusCells[i][0];
            int focusCol = focusCells[i][1];
    
            if (row == focusRow && col == focusCol) 
            {
                isthisSelected[i] = (state != 1);
            }
        } 
    }
    
    //check if all the puzzleIsSolved booleans are true or not
    boolean AllTrue(boolean[] puzzleIsSolved)
    {
        for(boolean i: puzzleIsSolved)
        {
            if(!i)
            {
                return false;
            }
        }
        return true;
    }
    
    static void selectInterface() //option to select an interface
    {
        //declaring Variables at the top
        String selectMsg = "Select a Interface";
        int userResponse = 0;
        int selected = 0;
        
        //declare Objects at the top
        NonogramUI textUI = new NonogramUI(); //Text UI Class
        
        ButtonGroup btn = new ButtonGroup();
        JRadioButton select1 = new JRadioButton("GUI UI Display");
        JRadioButton select2 = new JRadioButton("Text UI Display");
        select1.setSelected(true); //selects first radio button
        
        btn.add(select1);
        btn.add(select2);
        
        Object[] options = {selectMsg, select1, select2}; //creates array to hold joption elements
        
        userResponse = (JOptionPane.showConfirmDialog(null, options, "Selection", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE)); //popup box assigned to user response of type int
        
        if (userResponse == 0)
        {
            //checks which option was chosen
            selected = select1.isSelected() ? 1 : 2; //change later
            
            switch (selected) //calls appropriate interface
            {
                case 1:
                    guiInterface(); //GUI call
                    break;
                case 2:
                    textInterface(); //TextUI call
                    break;
                default:
                    System.out.println("Error has occured!");
            }
        }
        else
        {
            System.exit(1);
        }
    }
    
    static void guiInterface()
    {
        new NonogramPanel(); //GUI call
    }
    
    static void textInterface()
    {
        NonogramUI textUI = new NonogramUI(); 
        textUI.menu(); //TextUI call        
    }
    
    public static void main(String[] args) {
        selectInterface();
    }
    
    private int[] rows = new int[25];
    private int[] cols = new int[25];
    
    boolean[] puzzleIsSolved = new boolean[24];
    boolean[] isthisSelected = new boolean[16];
    
    private int state;
    
    //private PanelCell panel = new PanelCell();
    private PanelCell[][] cells = null;
    private JPanel grid ;
    private JPanel wholeGame ;
    private JPanel backGround ;
    private JButton clear ;
    private JButton undo ;
    private JButton save ;
    private JButton load ;
    private JButton help ;
    private JButton quit ;
    private JTextArea status ;
    private Nonogram puzzle ;
    private Stack<Assign> stack ;
    private JOptionPane option ;
    private static final String FILENAME = "Nonogram.txt" ;;
    private static final String NGFILE = "nons/tiny.non" ;

    private static boolean traceOn = false;
}
