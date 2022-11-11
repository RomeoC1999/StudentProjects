package view;

import java.awt.Color;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 *
 * @author Romeo Carrara
 * @since 28.11.2015
 */
public class DevelopmentMenu extends JMenu{
    
    public JMenu m1= new JMenu("Developed by...");
    public JMenu m2= new JMenu("For...");
    public JMenu m3= new JMenu("Version...");
    
    public JMenuItem i1= new JMenuItem("Romeo Carrara, 09/07/2016");
    public JMenuItem i2= new JMenuItem("Prof. Borella");
    public JMenuItem i3= new JMenuItem("Beta");
    
    
    public DevelopmentMenu() {
        
        super("Development");
        
        this.add(m1);
        //this.add(m2);
        this.add(m3);
        
        m1.add(i1);
        //m2.add(i2);
        m3.add(i3);
        
        i1.setBackground(Color.CYAN);
        
    }
}
