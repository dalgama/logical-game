import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

/**
 * In the application <b>FlodIt</b>, a <b>DotButton</b> is a specialized color of
 * <b>JButton</b> that represents a dot in the game. It can have one of six colors
 * 
 * The icon images are stored in a subdirectory ``data''. We have 3 sizes, ``normal'',
 * ``medium'' and ``small'', respectively in directory ``N'', ``M'' and ``S''.
 *
 * The images are 
 * ball-0.png => grey icon
 * ball-1.png => orange icon
 * ball-2.png => blue icon
 * ball-3.png => green icon
 * ball-4.png => purple icon
 * ball-5.png => red icon
 *
 *  <a href=
 * "http://developer.apple.com/library/safari/#samplecode/Puzzler/Introduction/Intro.html%23//apple_ref/doc/uid/DTS10004409"
 * >Based on Puzzler by Apple</a>.
 * @author Guy-Vincent Jourdan, University of Ottawa
 */
public class DotButton extends JButton {



    /**
     * The cell colors. Valid values are GameModel.COLOR_0, 
     * GameModel.COLOR_1, ... , GameModel.COLOR_5
     */
    private int color;
    private int iconSize;

    /**
     * The coordinate of this cell on the <b>Board</b>.
     */
    private int row, column;


    /**
     * An array is used to cache all the images. Since the images are not
     * modified, all the cells that display the same image reuse the same
     * <b>ImageIcon</b> object. Notice the use of the keyword <b>static</b>.
     */
    public static final int NUMBER_OF_SIZES = 3;
    public static final int SMALL_SIZE     = 0;
    public static final int MEDIUM_SIZE    = 1;
    public static final int LARGE_SIZE     = 2;

    private static final ImageIcon[][] icons = new ImageIcon[NUMBER_OF_SIZES][GameModel.NUMBER_OF_COLORS];


    /**
     * Constructor used for initializing a cell of a specified color.
     * 
     * @param row
     *            the row of this Cell
     * @param column
     *            the column of this Cell
     * @param color
     *            specifies the color of this cell
     * @param iconSize
     *            specifies the size to use, one of SMALL_SIZE, MEDIUM_SIZE or MEDIUM_SIZE
     */
    public DotButton(int row, int column, int color, int iconSize) {
    	this.row = row;
    	this.column = column;
    	this.color = color;
        this.iconSize = iconSize;
    	setBackground(Color.WHITE);
    	setIcon(getImageIcon());
    	Border emptyBorder = BorderFactory.createEmptyBorder(0, 0, 0, 0);
    	setBorder(emptyBorder);
    	setBorderPainted(false);
    }

    public DotButton(int color, int iconSize) {
        this(-1,-1,color,iconSize);
    }
 

     /**
     * Determine the image to use based on the cell color. Implements a caching mechanism.
     * 
     * @return the image to be displayed by the button
     */
    private ImageIcon getImageIcon() {
	
        String directory = (iconSize == SMALL_SIZE ? "S": (iconSize == MEDIUM_SIZE ? "M" : "N"));

        if (icons[iconSize][color] == null) {
        	    icons[iconSize][color] = new ImageIcon("data/" + directory + "/ball-" + Integer.toString(color) + ".png");
        }
        return icons[iconSize][color];
    }

    /**
     * Changes the cell color of this cell. The image is updated accordingly.
     * 
     * @param color
     *            the color to set
     */
    public void setColor(int color) {
    	this.color = color;
    	setIcon(getImageIcon());
    }

    /**
     * Getter for color
     *
     * @return color
     */
    public int getColor(){
        return color;
    }
 
    /**
     * Getter method for the attribute row.
     * 
     * @return the value of the attribute row
     */
    public int getRow() {
	   return row;
    }

    /**
     * Getter method for the attribute column.
     * 
     * @return the value of the attribute column
     */
    public int getColumn() {
	   return column;
    }
}
