

/**
 * The class <b>FloodIt</b> launches the game
 *
 * @author Guy-Vincent Jourdan, University of Ottawa
 */
public class FloodIt {


   /**
     * <b>main</b> of the application. Creates the instance of  GameController 
     * and starts the game. If a game size (>10) is passed as parameter, it is 
     * used as the board size. Otherwise, a default value is passed
     * 
     * @param args
     *            command line parameters
     */
     public static void main(String[] args) {

        StudentInfo.display() ;

        int size = 10;
        //int size = 17;
        //int size = 22;
        if (args.length == 1) {
            try{
                size = Integer.parseInt(args[0]);
                if(size<10){
                    System.out.println("Invalide argument, using default...");
                    size = 12;
                }
            } catch(NumberFormatException e){
                System.out.println("Invalide argument, using default...");
            }
        }
        GameController game = new GameController(size);
    }


}
