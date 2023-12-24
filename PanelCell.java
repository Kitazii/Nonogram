package nonogram;

import java.awt.Color;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Insets;
import java.awt.Dimension;

/**
 * Graphical representation of a cell in Nonogram
 */
@SuppressWarnings("serial")
public class PanelCell extends JButton implements ActionListener {
    private NonogramPanel    panel;
    private int              row;
    private int              col;
    private int              state;
    
    
    /**
     * this is for the constructor, mouse and key featuers
     * @param ng represents NonogramPanel
     * @param r represents row in the game
     * @param c represents colum in the game
     */

    public PanelCell(NonogramPanel ng, int r, int c)  {
        if (ng == null)
        throw new NonogramException("ng cannot be null");
        if (( r<0) || (r>Nonogram.MIN_SIZE))
        throw new NonogramException("row invalid, must be 0 <= row < ");
        if (( c<0) || (c>Nonogram.MIN_SIZE))
        throw new NonogramException("col invalid,must be 0 <= col < ");

        panel = ng;
        row   = r;
        col   = c;
        state= Nonogram.UNKNOWN;
       this.addActionListener(this);
    
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = Character.toUpperCase(e.getKeyChar());
                trace ("c: "+c);
                if (c!= EMPTY_CHAR) {
                    panel.setStatus("Must use" + FULL_CHAR);
                    return;
                }
                //panel.makeMove(row, col); //state);
                //panel.checkWin();
            }
        });
        setHorizontalAlignment(CENTER);
        setMargin(new Insets(5,5,5,5));
        setForeground(Color.red);
        setBackground(Color.green);
        setFont(font);
        setPreferredSize(new Dimension(40,40));
    }

    /**
     * To clear the text from the cell
     */

    
    /**
     * converting a character representing EMPTY, FULL, UNKNOWN
     */
    public int charToInt(char c) //changed to non-static from static
    {      
    int cti = 0;
    if (c == EMPTY_CHAR) {
            c = Nonogram.EMPTY;
            setBackground(Color.green);
        } else if (c == FULL_CHAR) {
            c = Nonogram.FULL;
            setBackground(Color.green);
        } else if (c == UNKNOWN_CHAR) {
            c = Nonogram.FULL;
            setBackground(Color.green);
        }
    return cti;
  }
    
    /**
     * Method trace for debugging (only when traceOn is true
     */
    public static void trace(String s) {
        if (traceOn)
        System.out.println("trace: " + s);
  }
    
    public static final char EMPTY_CHAR   = 'X';
    public static final char FULL_CHAR    = '@'; 
    public static final char UNKNOWN_CHAR = '.';
    public static final char INVALID_CHAR = '?';
    public static final char SOLVED_CHAR  = '*';  
    
    private static boolean traceOn = false;
    private static Font font = new Font("Dialog", Font.BOLD, 18);

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this)
        {
            changestate();
        }

    }

    private void changestate() {
        if (state==Nonogram.UNKNOWN)
        {
            state= Nonogram.FULL;
            panel.makeMove(row,col,state);
            //panel.checkWin();
            setBackground(Color.red); //sets background to red

        } else if (state == Nonogram.FULL)
        {
            setBackground(Color.green);
            state= Nonogram.EMPTY;
            panel.makeMove(row,col,state);
            //panel.checkWin();

        } else if (state== Nonogram.EMPTY)

        {
            state= Nonogram.UNKNOWN;
            panel.makeMove(row,col,state);
            //panel.checkWin();
            setBackground(Color.green);

        }


    }

    public void clear(){
        setText("");
        state=Nonogram.UNKNOWN;
    }
    public void isChanged(int states) {

        if (states==Nonogram.UNKNOWN)
        {
            setText("");
            setBackground(Color.green);

        } else if (states == Nonogram.FULL)
        {
            setText("@");
            setBackground(Color.red);

        } else if (states== Nonogram.EMPTY)

        {
            setText("X");
            setBackground(Color.green);


        }

    }
    
    //getter for state
    public int getState()
    {
        int state;
        state = this.state;
        return state;
    }
}
