import java.awt.* ;
import java.awt.event.* ;
import javax.swing.* ;
 
public class SettingOptions extends JFrame {

    private GameController gameController ;

    public SettingOptions(GameController gameController){

        this.gameController = gameController ;

        JRadioButton plane = new JRadioButton("Plane");
        plane.setActionCommand("Plane");

        JRadioButton torus = new JRadioButton("Torus");
        torus.setActionCommand("Torus");

        JRadioButton orthogonal = new JRadioButton("Orthogonal");
        orthogonal.setActionCommand("Orthogonal");

        JRadioButton diagonals = new JRadioButton("Diagonals");
        diagonals.setActionCommand("Diagonals");

        JButton ok = new JButton ("OK");
        ok.addActionListener(gameController) ;


        // add event handler

        plane.addActionListener(gameController) ;
        torus.addActionListener(gameController) ;
        
        orthogonal.addActionListener(gameController) ;
        diagonals.addActionListener(gameController) ;


        // add radio buttons to a ButtonGroup

        final ButtonGroup group1 = new ButtonGroup();
        group1.add(plane);
        group1.add(torus);
        plane.setSelected(true) ;

        final ButtonGroup group2 = new ButtonGroup();
        group2.add(orthogonal);
        group2.add(diagonals);
        orthogonal.setSelected(true) ;


        // Frame setting

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 200);
        
        Container cont = getContentPane();

        cont.setLayout(new GridLayout(0, 1));
        cont.add(new JLabel("Play on plane or torus?"));
        cont.add(plane);
        cont.add(torus);
        cont.add(new JLabel("Diagonal moves"));
        cont.add(orthogonal);
        cont.add(diagonals);
        add(ok,BorderLayout.SOUTH);
        setVisible(true);
    }
}