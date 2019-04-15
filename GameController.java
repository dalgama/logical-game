import java.awt.event.ActionEvent ;
import java.awt.event.ActionListener ;
// import java.util.LinkedList ;

import java.lang.ClassNotFoundException ;
import java.lang.NullPointerException ;
import java.lang.IllegalStateException ;

import javax.swing.JButton ;
import javax.swing.JRadioButton ;
import javax.swing.JOptionPane ;

import java.io.IOException ;
import java.io.Serializable ;
import java.io.ObjectOutputStream ;
import java.io.ObjectInputStream ;
import java.io.FileOutputStream ;
import java.io.FileInputStream ;
import java.io.FileNotFoundException ;

import java.io.File ;

/**
 * The class <b>GameController</b> is the controller of the game. It has a method
 * <b>selectColor</b> which is called by the view when the player selects the next
 * color. It then computesthe next step of the game, and  updates model and view.
 *
 * @author Guy-Vincent Jourdan, University of Ottawa
 */
public class GameController implements ActionListener {

    /**
     * Reference to the view of the board
     */
    private GameView gameView ;

    /**
     * Reference to the model of the game
     */
    private GameModel gameModel ;

    private boolean boardMode = true ;
    private boolean captureMode = true ;

    private boolean change = true ;

    // private int checkLeft ;
    // private int checkRight ;
    // private int checkUp ;
    // private int checkDown ;

    private SettingOptions newWindow ;

    private GenericLinkedStack<GameModel> modelStack ;
    private GenericLinkedStack<GameModel> undoModelStack ;

    private boolean undone = false ;
    private boolean redone = false ;


    /**
     * Constructor used for initializing the controller. It creates the game's view 
     * and the game's model instances
     * 
     * @param size
     *            the size of the board on which the game will be played
     */
    public GameController(int size) {

        File savedFile = new File("./savedGame.ser");

        if (!savedFile.exists()) {
            gameModel = new GameModel(size) ;

            modelStack = new GenericLinkedStack<GameModel>() ;
            undoModelStack = new GenericLinkedStack<GameModel>() ;

            addModelToModelStack() ;
        }
        else {

            try {
                FileInputStream newCopy = new FileInputStream("savedGame.ser") ;
                ObjectInputStream modelCopy = new ObjectInputStream(newCopy) ;
                GameModel newModel = (GameModel) modelCopy.readObject() ;

                gameModel = newModel ;

                modelCopy.close() ;
                savedFile.delete() ;
            }
            catch(ClassNotFoundException k) {}
            catch(FileNotFoundException m) {}
            catch(IOException i) {}
        }

        // System.out.println() ;
        // System.out.println("CREATING...") ;
        // System.out.println("---------------------------------------------------------------") ;

        // System.out.println("undoModelStack size:" + undoModelStack.getSize()) ;
        // System.out.println("modelStack size:" + modelStack.getSize()) ;
        // System.out.println("---------------------------------------------------------------") ;

        // System.out.println("Created GameModel NumberOfSteps : " + modelStack.peek().getNumberOfSteps()) ;
        // System.out.println("Created GameModel NumberCaptured : " + modelStack.peek().getNumberCaptured()) ;
        // System.out.println("---------------------------------------------------------------") ;

        gameView = new GameView(gameModel, this) ;

        newWindow = new SettingOptions(this) ;
        newWindow.setVisible(false) ;

        // boardMode = true ;
        // captureMode = true ;

        gameView.update() ;
    }

// ************************************************************************************************************************************

    /**
     * resets the game
     */
    public void reset() {

        gameModel.reset() ;

        modelStack = new GenericLinkedStack<GameModel>() ;
        undoModelStack = new GenericLinkedStack<GameModel>() ;

        addModelToModelStack() ;

        // try {
        //     modelStack.push(gameModel.clone()) ;
        // }
        // catch(CloneNotSupportedException exception) {}

        System.out.println() ;
        System.out.println("RESETING...") ;
        System.out.println("---------------------------------------------------------------") ;

        System.out.println("undoModelStack size: " + undoModelStack.getSize()) ;
        System.out.println("modelStack size: " + modelStack.getSize()) ;
        System.out.println("---------------------------------------------------------------") ;

        System.out.println("Reset GameModel NumberOfSteps : " + modelStack.peek().getNumberOfSteps()) ;
        System.out.println("Reset GameModel NumberCaptured : " + modelStack.peek().getNumberCaptured()) ;
        System.out.println("---------------------------------------------------------------") ;
        
        gameView.update() ;
    }

// ************************************************************************************************************************************

    public GameModel getGameModel() {
        return gameModel ;
    }


    public GenericLinkedStack<GameModel> getModelStack() {
        return modelStack ;
    }

    public GenericLinkedStack<GameModel> getUndoModelStack() {
        return undoModelStack ;
    }


    public void addModelToModelStack() {
        
        try {
            modelStack.push(gameModel.clone()) ;
        }
        catch(CloneNotSupportedException exception) {}
    }

// ************************************************************************************************************************************

    public void undo() {

        undone = true ;

        GameModel tmpModel = modelStack.pop() ;

        System.out.println("Popped!") ;
        // System.out.println(tmpModel) ;

        undoModelStack.push(tmpModel) ;

        System.out.println("Pushed!") ;

        gameModel = modelStack.peek() ;

        System.out.println("Peeked!") ;

        change = false ;

        System.out.println() ;
        System.out.println("UNDOING...") ;
        System.out.println("---------------------------------------------------------------") ;

        System.out.println("undoModelStack size: " + undoModelStack.getSize()) ;
        System.out.println("modelStack size: " + modelStack.getSize()) ;
        System.out.println("---------------------------------------------------------------") ;

        System.out.println("Undone GameModel NumberOfSteps : " + modelStack.peek().getNumberOfSteps()) ;
        System.out.println("Undone GameModel NumberCaptured : " + modelStack.peek().getNumberCaptured()) ;
        System.out.println("---------------------------------------------------------------") ;

        gameView.update() ;

        // System.out.println("undoModelStack size:" + undoModelStack.getSize()) ;
        // System.out.println("modelStack size:" + modelStack.getSize()) ;
    }

    public boolean getUndone() {
        return undone ;
    }

// ************************************************************************************************************************************

    /**
     * redoes the
     *
     */
    public void redo() {

        redone = true ;

        modelStack.push(undoModelStack.pop()) ;

        gameModel = modelStack.peek() ; 

        System.out.println() ;
        System.out.println("REDOING...") ;
        System.out.println("---------------------------------------------------------------") ;

        System.out.println("undoModelStack size: " + undoModelStack.getSize()) ;
        System.out.println("modelStack size: " + modelStack.getSize()) ;
        System.out.println("---------------------------------------------------------------") ;

        System.out.println("Redone GameModel NumberOfSteps: " + modelStack.peek().getNumberOfSteps()) ;
        System.out.println("Redone GameModel NumberCaptured: " + modelStack.peek().getNumberCaptured()) ;
        System.out.println("---------------------------------------------------------------") ;

        gameView.update() ;   

        // System.out.println("undoModelStack size:" + undoModelStack.getSize()) ;
        // System.out.println("modelStack size:" + modelStack.getSize()) ;    
    }

    public boolean getRedone() {
        return redone ;
    }

// ************************************************************************************************************************************

    /**
     * Callback used when the user clicks a button (reset or quit)
     *
     * @param e
     *            the ActionEvent
     */
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() instanceof DotButton) {

            if (gameModel.getNumberCaptured() == 0) {

                gameModel.capture(((DotButton)(e.getSource())).getRow(), ((DotButton)(e.getSource())).getColumn()) ;
    }

            // modelStack.push(gameModel.clone()) ;

            undone = false ;
            redone = false ;

            if (((DotButton)(e.getSource())).getColor() != gameModel.getCurrentSelectedColor()) {
                System.out.println() ;
                System.out.println("undoModelStack size: " + undoModelStack.getSize()) ;
                System.out.println("modelStack size: " + modelStack.getSize()) ;
                System.out.println("---------------------------------------------------------------") ;
            }

            // System.out.println("GameModel NumberOfSteps : " + modelStack.peek().getNumberOfSteps()) ;
            // System.out.println("GameModel NumberCaptured : " + modelStack.peek().getNumberCaptured()) ;
            // System.out.println("---------------------------------------------------------------") ;


            selectColor(((DotButton)(e.getSource())).getColor()) ;

            // System.out.println(gameModel.getNumberOfSteps()) ;

            gameView.update() ;
        }

        else if (e.getSource() instanceof JRadioButton) {

            JRadioButton clicked = (JRadioButton)(e.getSource()) ;
            
            if (clicked.getText().equals("Plane")) {
                System.out.println("plane") ;
                boardMode = true ;
                
                if (captureMode) {
                    floodPlaneOrthogonal() ;
                }
                else {
                    floodPlaneDiagonal() ;
                }
            }

            else if (clicked.getText().equals("Torus")) {
                System.out.println("torus") ;
                boardMode = false ;

                if (captureMode) {
                    floodTorusOrthogonal() ;
                }
                else {
                    floodTorusDiagonal() ;
                }

            }

            if (clicked.getText().equals("Orthogonal")) {
                System.out.println("orthogonal") ;
                captureMode = true ;

                if (boardMode) {
                    floodPlaneOrthogonal() ;
                }
                else {
                    floodTorusOrthogonal() ;
                }
            }

            else if (clicked.getText().equals("Diagonals")) {
                System.out.println("diagonals") ;
                captureMode = false ;

                if (boardMode) {
                    floodPlaneDiagonal() ;
                }
                else {
                    floodTorusDiagonal() ;
                }
            }
        }

        else if (e.getSource() instanceof JButton) {

            JButton clicked = (JButton)(e.getSource()) ;

            if (clicked.getText().equals("Quit")) {
                saveModel() ;
                System.exit(0) ;
            } 
            else if (clicked.getText().equals("Reset")) {
                reset() ;
            }
            else if (clicked.getText().equals("Undo")) {
                undo() ;
            }
            else if (clicked.getText().equals("Redo")) {
                redo() ;

                // if (undoModelStack.isEmpty()) {
                //     gameView.setTransparentRedo(false) ;
                // }
                // else {
                    // modelStack.push(undoModelStack.pop()) ;
                    // gameModel = modelStack.peek() ;


                    // gameView = new GameView(gameModel, this) ;
                //     // gameView.setTransparentRedo(true) ;
                // }
            }

            
            else if (clicked.getText().equals("Settings")) {
                newWindow.setVisible(true) ;
            }
            
            else if (clicked.getText().equals("OK")) {
                newWindow.setVisible(false) ;
            }
        } 
    }

// ************************************************************************************************************************************

    /**
     * <b>selectColor</b> is the method called when the user selects a new color.
     * If that color is not the currently selected one, then it applies the logic
     * of the game to capture possible locations. It then checks if the game
     * is finished, and if so, congratulates the player, showing the number of
     * moves, and gives to options: start a new game, or exit
     * @param color
     *            the newly selected color
     */
    public void selectColor(int color) {

        if (color != gameModel.getCurrentSelectedColor()) {

            change = true ;

            undoModelStack.clear() ;

            gameModel.setCurrentSelectedColor(color) ;

            // addModelToModelStack() ;
            
            gameModel.step() ;

            if (boardMode && captureMode) {
                // System.out.println("PO") ;
                floodPlaneOrthogonal() ;
            }
            else if (!boardMode && captureMode) {
                // System.out.println("TO") ;
                floodTorusOrthogonal() ;
            }
            else if (boardMode && !captureMode) {
                // System.out.println("PD") ;
                floodPlaneDiagonal() ;
            }
            else if (!boardMode && !captureMode) {
                // System.out.println("TD") ;
                floodTorusDiagonal() ;
            }

            addModelToModelStack() ;

            System.out.println("GameModel NumberOfSteps : " + modelStack.peek().getNumberOfSteps()) ;
            System.out.println("GameModel NumberCaptured : " + modelStack.peek().getNumberCaptured()) ;
            System.out.println("---------------------------------------------------------------") ;

            // gameView.update() ;

            if(gameModel.isFinished()) {

                gameView.update() ;

                Object[] options = {"Play Again", "Quit"} ;

                int n = JOptionPane.showOptionDialog(gameView, "Congratulations, you won in " 
                    + gameModel.getNumberOfSteps() + " steps!\n Would you like to play again?",
                    "Won", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    options, options[0]) ;
                if (n == 0) {
                    reset() ;
                } else {
                    System.exit(0) ;
                }   
            }            
        } 

        else {
            change = false ;
            // gameView.update() ;
        }       
    }

    public boolean getChange() {
        return change ;
    }

// ************************************************************************************************************************************

    /**
     * <b>floodPlaneOrthogonal</b> is the method that computes which new dots should be ``captured'' 
     * when a new color has been selected in "Plane" and "Orthogonal" settings. The Model is updated accordingly.
     */
    private void floodPlaneOrthogonal() {

        Stack<DotInfo> stack = new GenericLinkedStack<DotInfo>() ;
        for (int i = 0; i < gameModel.getSize(); i++) {
            for (int j = 0; j < gameModel.getSize(); j++) {
                if (gameModel.isCaptured(i,j)) {
                    stack.push(gameModel.get(i,j)) ;
                }
            }
        }

        while (!stack.isEmpty()) {

            DotInfo checkDot = stack.pop() ;

            //LEFT
            if ((checkDot.getX() > 0) && shouldBeCaptured(checkDot.getX()-1, checkDot.getY())) {
                gameModel.capture(checkDot.getX()-1, checkDot.getY()) ;
                stack.push(gameModel.get(checkDot.getX()-1, checkDot.getY())) ;
            }  

            //RIGHT
            if ((checkDot.getX() < gameModel.getSize()-1) && shouldBeCaptured(checkDot.getX()+1, checkDot.getY())) {
                gameModel.capture(checkDot.getX()+1, checkDot.getY()) ;
                stack.push(gameModel.get(checkDot.getX()+1, checkDot.getY())) ;
            }

            //UP
            if ((checkDot.getY() > 0) && shouldBeCaptured(checkDot.getX(), checkDot.getY()-1)) {
                gameModel.capture(checkDot.getX(), checkDot.getY()-1);
                stack.push(gameModel.get(checkDot.getX(), checkDot.getY()-1)) ;
            }  

            //DOWN
            if ((checkDot.getY() < gameModel.getSize()-1) && shouldBeCaptured(checkDot.getX(), checkDot.getY()+1)) {
                gameModel.capture(checkDot.getX(), checkDot.getY()+1);
                stack.push(gameModel.get(checkDot.getX(), checkDot.getY()+1)) ;
            }
        }
    }

// ************************************************************************************************************************************

    /**
     * <b>floodTorusOrthogonal</b> is the method that computes which new dots should be ``captured'' 
     * when a new color has been selected in "Torus" and "Orthogonal" settings. The Model is updated accordingly.
     */
    private void floodTorusOrthogonal() {

        Stack<DotInfo> stack = new GenericLinkedStack<DotInfo>() ;
        for (int i = 0; i < gameModel.getSize(); i++) {
            for (int j = 0; j < gameModel.getSize(); j++) {
                if (gameModel.isCaptured(i,j)) {
                    stack.push(gameModel.get(i,j)) ;
                }
            }
        }

        while (!stack.isEmpty()) {

            DotInfo checkDot = stack.pop() ;

            // checkXOrthogonal(checkDot.getX()) ;
            // checkYOrthogonal(checkDot.getY()) ;

            // //LEFT
            // if (shouldBeCaptured(checkLeft, checkDot.getY())) {
            //     gameModel.capture(checkLeft, checkDot.getY()) ;
            //     stack.push(gameModel.get(checkLeft, checkDot.getY())) ;
            // }  

            // //RIGHT
            // if (shouldBeCaptured(checkRight, checkDot.getY())) {
            //     gameModel.capture(checkRight, checkDot.getY()) ;
            //     stack.push(gameModel.get(checkRight, checkDot.getY())) ;
            // }

            // //UP
            // if (shouldBeCaptured(checkDot.getX(), checkUp)) {
            //     gameModel.capture(checkDot.getX(), checkUp) ;
            //     stack.push(gameModel.get(checkDot.getX(), checkUp)) ;
            // }  

            // //DOWN
            // if (shouldBeCaptured(checkDot.getX(), checkDown)) {
            //     gameModel.capture(checkDot.getX(), checkDown) ;
            //     stack.push(gameModel.get(checkDot.getX(), checkDown)) ;
            // }


            if ((checkDot.getX() == 0) && shouldBeCaptured(gameModel.getSize()-1, checkDot.getY())) {
                gameModel.capture(gameModel.getSize()-1, checkDot.getY()) ;
                stack.push(gameModel.get(gameModel.getSize()-1, checkDot.getY())) ;
            }

            if ((checkDot.getX() > 0) && shouldBeCaptured(checkDot.getX()-1, checkDot.getY())) {
                gameModel.capture(checkDot.getX()-1, checkDot.getY()) ;
                stack.push(gameModel.get(checkDot.getX()-1, checkDot.getY())) ;
            }

            if ((checkDot.getX() == gameModel.getSize()-1) && shouldBeCaptured(0, checkDot.getY())) {
                gameModel.capture(0, checkDot.getY()) ;
                stack.push(gameModel.get(0, checkDot.getY())) ;
            }

            if ((checkDot.getX() < gameModel.getSize()-1) && shouldBeCaptured(checkDot.getX()+1, checkDot.getY())) {
                gameModel.capture(checkDot.getX()+1, checkDot.getY()) ;
                stack.push(gameModel.get(checkDot.getX()+1, checkDot.getY())) ;
            }

            if ((checkDot.getY() == 0) && shouldBeCaptured(checkDot.getX(), gameModel.getSize()-1)) {
                gameModel.capture(checkDot.getX(), gameModel.getSize()-1) ;
                stack.push(gameModel.get(checkDot.getX(), gameModel.getSize()-1)) ;
            }

            if ((checkDot.getY() > 0) && shouldBeCaptured(checkDot.getX(), checkDot.getY()-1)) {
                gameModel.capture(checkDot.getX(), checkDot.getY()-1) ;
                stack.push(gameModel.get(checkDot.getX(), checkDot.getY()-1)) ;
            } 

            if ((checkDot.getY() == gameModel.getSize()-1) && shouldBeCaptured(checkDot.getX(), 0)) {
                gameModel.capture(checkDot.getX(), 0) ;
                stack.push(gameModel.get(checkDot.getX(), 0)) ;
            }

            if ((checkDot.getY() < gameModel.getSize()-1) && shouldBeCaptured(checkDot.getX(), checkDot.getY()+1)) {
                gameModel.capture(checkDot.getX(), checkDot.getY()+1) ;
                stack.push(gameModel.get(checkDot.getX(), checkDot.getY()+1)) ;
            }
        }
    }



    // private void checkXOrthogonal(int x) {

    //     if (x - 1 == -1) {
    //         checkLeft = x + gameModel.getSize() ;
    //         checkRight = x + 1 ;
    //     }
    //     else if (x + 1 == gameModel.getSize()) {
    //         checkLeft = x - 1 ;
    //         checkRight = x - gameModel.getSize() ;
    //     }
    //     else {
    //         checkLeft = x - 1 ;
    //         checkRight = x + 1 ;
    //     }
    // }

    // private void checkYOrthogonal(int y) {

    //     if (y - 1 == 0) {
    //         checkUp = y + gameModel.getSize() ;
    //         checkDown = y + 1 ;
    //     }
    //     else if (y + 1 == gameModel.getSize()) {
    //         checkUp = y - 1 ;
    //         checkDown = y - gameModel.getSize() ;
    //     }
    //     else {
    //         checkUp = y - 1 ;
    //         checkDown = y + 1 ;
    //     }
    // }

// ************************************************************************************************************************************

    /**
     * <b>floodPlaneDiagonal</b> is the method that computes which new dots should be ``captured'' 
     * when a new color has been selected in "Plane" and "Diagonal" settings. The Model is updated accordingly.
     */
    private void floodPlaneDiagonal() {

        Stack<DotInfo> stack = new GenericLinkedStack<DotInfo>() ;
        for (int i = 0; i < gameModel.getSize(); i++) {
            for (int j = 0; j < gameModel.getSize(); j++) {
                if (gameModel.isCaptured(i,j)) {
                    stack.push(gameModel.get(i,j)) ;
                }
            }
        }

        while (!stack.isEmpty()) {

            DotInfo checkDot = stack.pop() ;

            //LEFT
            if (checkDot.getX() > 0) {
                
                //LEFT
                if (shouldBeCaptured(checkDot.getX()-1, checkDot.getY())) {
                    gameModel.capture(checkDot.getX()-1, checkDot.getY()) ;
                    stack.push(gameModel.get(checkDot.getX()-1, checkDot.getY())) ;
                } 

                //LEFT-UP
                if ((checkDot.getY() > 0) && shouldBeCaptured(checkDot.getX()-1, checkDot.getY()-1)) {
                    gameModel.capture(checkDot.getX()-1, checkDot.getY()-1) ;
                    stack.push(gameModel.get(checkDot.getX()-1, checkDot.getY()-1)) ;
                } 

                //LEFT-DOWN
                if ((checkDot.getY() < gameModel.getSize()-1) && shouldBeCaptured(checkDot.getX()-1, checkDot.getY()+1)) {
                    gameModel.capture(checkDot.getX()-1, checkDot.getY()+1) ;
                    stack.push(gameModel.get(checkDot.getX()-1, checkDot.getY()+1)) ;
                } 
            }

            //RIGHT
            if (checkDot.getX() < gameModel.getSize()-1) { 
                
                //RIGHT
                if (shouldBeCaptured(checkDot.getX()+1, checkDot.getY())) {
                    gameModel.capture(checkDot.getX()+1, checkDot.getY()) ;
                    stack.push(gameModel.get(checkDot.getX()+1, checkDot.getY())) ;
                }

                //RIGHT-UP
                if ((checkDot.getY() > 0) && shouldBeCaptured(checkDot.getX()+1, checkDot.getY()-1)) {
                    gameModel.capture(checkDot.getX()+1, checkDot.getY()-1) ;
                    stack.push(gameModel.get(checkDot.getX()+1, checkDot.getY()-1)) ;
                } 

                //RIGHT-DOWN
                if ((checkDot.getY() < gameModel.getSize()-1) && shouldBeCaptured(checkDot.getX()+1, checkDot.getY()+1)) {
                    gameModel.capture(checkDot.getX()+1, checkDot.getY()+1) ;
                    stack.push(gameModel.get(checkDot.getX()+1, checkDot.getY()+1)) ;
                }
            }

            //UP
            if ((checkDot.getY() > 0) && shouldBeCaptured(checkDot.getX(), checkDot.getY()-1)) {
                gameModel.capture(checkDot.getX(), checkDot.getY()-1);
                stack.push(gameModel.get(checkDot.getX(), checkDot.getY()-1)) ;
            }
            //DOWN
            if ((checkDot.getY() < gameModel.getSize()-1) && shouldBeCaptured(checkDot.getX(), checkDot.getY()+1)) {
                gameModel.capture(checkDot.getX(), checkDot.getY()+1);
                stack.push(gameModel.get(checkDot.getX(), checkDot.getY()+1)) ;
            }
        }
    }

// ************************************************************************************************************************************

    /**
     * <b>floodTorusDiagonal</b> is the method that computes which new dots should be ``captured'' 
     * when a new color has been selected in "Torus" and "Diagonal" settings. The Model is updated accordingly.
     */
    private void floodTorusDiagonal() {

        Stack<DotInfo> stack = new GenericLinkedStack<DotInfo>() ;
        for (int i = 0; i < gameModel.getSize(); i++) {
            for (int j = 0; j < gameModel.getSize(); j++) {
                if (gameModel.isCaptured(i,j)) {
                    stack.push(gameModel.get(i,j)) ;
                }
            }
        }

        while (!stack.isEmpty()) {

            DotInfo checkDot = stack.pop() ;

            // LEFT SIDE
            if (checkDot.getX() >= 0) {

                // Special case (AGAINST border wall)
                if (checkDot.getX() == 0) { 

                    // LEFT-Adjacent
                    if (shouldBeCaptured(gameModel.getSize()-1, checkDot.getY())) {
                        gameModel.capture(gameModel.getSize()-1, checkDot.getY()) ;
                        stack.push(gameModel.get(gameModel.getSize()-1, checkDot.getY())) ;
                    }

                    // LEFT-UP
                    if ((checkDot.getY() > 0) && shouldBeCaptured(gameModel.getSize()-1, checkDot.getY()-1)) {
                        gameModel.capture(gameModel.getSize()-1, checkDot.getY()-1) ;
                        stack.push(gameModel.get(gameModel.getSize()-1, checkDot.getY()-1)) ;
                    } 

                    // LEFT-DOWN
                    if ((checkDot.getY() < gameModel.getSize()-1) && shouldBeCaptured(gameModel.getSize()-1, checkDot.getY()+1)) {
                        gameModel.capture(gameModel.getSize()-1, checkDot.getY()+1) ;
                        stack.push(gameModel.get(gameModel.getSize()-1, checkDot.getY()+1)) ;
                    } 
                }

                // Normal case (NOT against any border wall)
                if (checkDot.getX() > 0) {
                
                    // LEFT-Adjacent
                    if (shouldBeCaptured(checkDot.getX()-1, checkDot.getY())) {
                        gameModel.capture(checkDot.getX()-1, checkDot.getY()) ;
                        stack.push(gameModel.get(checkDot.getX()-1, checkDot.getY())) ;
                    } 

                    // LEFT-UP
                    if ((checkDot.getY() > 0) && shouldBeCaptured(checkDot.getX()-1, checkDot.getY()-1)) {
                        gameModel.capture(checkDot.getX()-1, checkDot.getY()-1) ;
                        stack.push(gameModel.get(checkDot.getX()-1, checkDot.getY()-1)) ;
                    } 

                    // LEFT-DOWN
                    if ((checkDot.getY() < gameModel.getSize()-1) && shouldBeCaptured(checkDot.getX()-1, checkDot.getY()+1)) {
                        gameModel.capture(checkDot.getX()-1, checkDot.getY()+1) ;
                        stack.push(gameModel.get(checkDot.getX()-1, checkDot.getY()+1)) ;
                    } 
                }
            }

            // RIGHT SIDE
            if (checkDot.getX() <= gameModel.getSize()-1) { 

                // Special case (AGAINST border wall)
                if (checkDot.getX() == gameModel.getSize()-1) {

                    // RIGHT-Adjacent
                    if (shouldBeCaptured(0, checkDot.getY())) {
                        System.out.println("Captured!") ;
                        gameModel.capture(0, checkDot.getY()) ;
                        stack.push(gameModel.get(0, checkDot.getY())) ;
                    }

                    // RIGHT-UP
                    if ((checkDot.getY() > 0) && shouldBeCaptured(0, checkDot.getY()-1)) {
                        gameModel.capture(0, checkDot.getY()-1) ;
                        stack.push(gameModel.get(0, checkDot.getY()-1)) ;
                    } 

                    // RIGHT-DOWN
                    if ((checkDot.getY() < (gameModel.getSize()-1)) && shouldBeCaptured(0, checkDot.getY()+1)) {
                        gameModel.capture(0, checkDot.getY()+1) ;
                        stack.push(gameModel.get(0, checkDot.getY()+1)) ;
                    }
                }

                // Normal case (NOT against any border wall)
                if (checkDot.getX() < gameModel.getSize()-1) {
                
                    // RIGHT-Adjacent
                    if (shouldBeCaptured(checkDot.getX()+1, checkDot.getY())) {
                        gameModel.capture(checkDot.getX()+1, checkDot.getY()) ;
                        stack.push(gameModel.get(checkDot.getX()+1, checkDot.getY())) ;
                    }

                    // RIGHT-UP
                    if ((checkDot.getY() > 0) && shouldBeCaptured(checkDot.getX()+1, checkDot.getY()-1)) {
                        gameModel.capture(checkDot.getX()+1, checkDot.getY()-1) ;
                        stack.push(gameModel.get(checkDot.getX()+1, checkDot.getY()-1)) ;
                    } 

                    // RIGHT-DOWN
                    if ((checkDot.getY() < gameModel.getSize()-1) && shouldBeCaptured(checkDot.getX()+1, checkDot.getY()+1)) {
                        gameModel.capture(checkDot.getX()+1, checkDot.getY()+1) ;
                        stack.push(gameModel.get(checkDot.getX()+1, checkDot.getY()+1)) ;
                    }
                }
            }


            // UP-Adjacent
            if (checkDot.getY() >= 0) {

                // Special case (AGAINST border wall)
                if (checkDot.getY() == 0) {                

                    if (shouldBeCaptured(checkDot.getX(), gameModel.getSize()-1)) {
                        gameModel.capture(checkDot.getX(), gameModel.getSize()-1) ;
                        stack.push(gameModel.get(checkDot.getX(), gameModel.getSize()-1)) ;
                    }
                }

                // Normal case (NOT against any border wall)
                if (checkDot.getY() > 0) { 

                    if (shouldBeCaptured(checkDot.getX(), checkDot.getY()-1)) {
                        gameModel.capture(checkDot.getX(), checkDot.getY()-1) ;
                        stack.push(gameModel.get(checkDot.getX(), checkDot.getY()-1)) ;
                    }
                }
            }
            
            // DOWN-Adjacent
            if (checkDot.getY() <= gameModel.getSize()-1) {

                // Special case (AGAINST border wall)
                if (checkDot.getX() == gameModel.getSize()-1) {

                    if (shouldBeCaptured(checkDot.getX(), 0)) {
                        gameModel.capture(checkDot.getX(), 0) ;
                        stack.push(gameModel.get(checkDot.getX(), 0)) ;
                    }
                }

                // Normal case (NOT against any border wall)
                if (checkDot.getY() < (gameModel.getSize()-1)) {

                    if (shouldBeCaptured(checkDot.getX(), checkDot.getY()+1)) {
                        gameModel.capture(checkDot.getX(), checkDot.getY()+1) ;
                        stack.push(gameModel.get(checkDot.getX(), checkDot.getY()+1)) ;
                    }
                }   
            }
        }
    }

// ************************************************************************************************************************************

    /**
     * <b>shouldBeCaptured</b> is a helper method that decides if the dot
     * located at position (i,j), which is next to a captured dot, should
     * itself be captured
     * @param i
     *            row of the dot
     * @param j
     *            column of the dot
     */
    private boolean shouldBeCaptured(int i, int j) {

        if (!gameModel.isCaptured(i, j) && (gameModel.getColor(i,j) == gameModel.getCurrentSelectedColor())) {
            return true ;
        } 
        else {
            return false ;
        }
    }

// ************************************************************************************************************************************

    private void saveModel() {
        try {
            FileOutputStream savedGame = new FileOutputStream("savedGame.ser") ;
            ObjectOutputStream out = new ObjectOutputStream(savedGame) ;
            out.writeObject(gameModel) ;
            out.close() ;
        }
        catch(FileNotFoundException m) {}
        catch(IOException i) {}
    }
}
