package crtournament;

import memory.VersionManager;
import java.awt.Color;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 *
 * @author Romeo Carrara
 * @since 28.11.2015
 */
public class Development extends JMenu{
    
    public JMenu m1= new JMenu("Sviluppato da...");
    public JMenu m2= new JMenu("Per...");
    public JMenu m3= new JMenu("Versione...");
    public JMenu m4= new JMenu("Col supporto di...");
    
    public JMenuItem i1= new JMenuItem("Romeo Carrara, 26/11/2015");
    public JMenuItem i2= new JMenuItem("Prof. Fabbri");
    public JMenuItem i3= new JMenuItem(VersionManager.VERSION);
    public JMenuItem i4= new JMenuItem("L. Valbonesi");
    public JMenuItem i5= new JMenuItem("Marta");
    
    
    public Development() {
        
        super("Sviluppo");
        
        this.add(m1);
        this.add(m2);
        this.add(m3);
        this.add(m4);
        
        m1.add(i1);
        m2.add(i2);
        m3.add(i3);
        
        m4.add(i4);
        m4.add(i5);
        
        i1.setBackground(Color.CYAN);
        
    }
}
