import java.lang.Exception ;
import java.io.Serializable ;

/**
 * The class <b>DotInfo</b> is a simple helper class to store the initial color and state
 * (captured or not) at the dot position (x,y)
 *
 * @author Guy-Vincent Jourdan, University of Ottawa
 */

public class DotInfo implements Cloneable, Serializable {

    /**
     * The coordinate of this DotInfo.
     */
    private int x;
    private int y;

    /**
     * The initial color at (x,y)
     */
    private int color;
    /**
     * Is that locatio captured ?
     */
    private boolean captured;

    /**
     * Constructor 
     * 
     * @param x
     *            the x coordinate
     * @param y
     *            the y coordinate
     * @param color
     *            the initial color
     */
    public DotInfo(int x, int y, int color){
        this.x = x;
        this.y = y;
        this.color = color;
    }

    /**
     * Getter method for the attribute x.
     * 
     * @return the value of the attribute x
     */
    public int getX(){
        return x;
    }
    
    /**
     * Getter method for the attribute y.
     * 
     * @return the value of the attribute y
     */
    public int getY(){
        return y;
    }
    
 
    /**
     * Setter for captured
     * @param captured
     *            the new value for captured
     */
    public void setCaptured(boolean captured) {
        this.captured = captured;
    }

    /**
     * Get for captured
     *
     * @return captured
     */
    public boolean isCaptured() {
        return captured;
    }

    /**
     * Get for color
     *
     * @return color
     */
    public int getColor() {
        return color ;
    }

    protected DotInfo clone() throws CloneNotSupportedException {
        return (DotInfo) super.clone() ;
    }
 }
