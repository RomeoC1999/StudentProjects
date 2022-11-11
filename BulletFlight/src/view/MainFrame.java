package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.SwingUtilities;

/**
 *
 * @author Romeo Carrara
 * @since 2016.05.22
 */
public class MainFrame extends JFrame{
    
    public MainFrame(){
        
        super("Projectile Motion");
        
        this.add(new MainPanel(this), BorderLayout.CENTER);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setSize(getIdealSize(65, 80));
        setLocationRelativeTo(null);
        
        JMenuBar menuBar= new JMenuBar();
        menuBar.add(new DevelopmentMenu());
        menuBar.add(new OptionMenu(this));
        this.setJMenuBar(menuBar);
        
        setVisible(true);
    }
    
    
    /**
     * 
     * @param widthPerc percentage of screen width
     * @param heightPerc percentage of screen height
     * @return dimension as pixels
     */
    private Dimension getIdealSize(float widthPerc, float heightPerc){
        
        Toolkit toolkit= Toolkit.getDefaultToolkit();
        Dimension dim= toolkit.getScreenSize();
        
        int width= (int)(dim.width/100F*widthPerc);
        int height= (int)(dim.height/100F*heightPerc);
        
        return new Dimension(width, height);
    }
    
}
