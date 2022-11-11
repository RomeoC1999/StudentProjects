package crtournament;

import crstandard.gui.CRFrame;
import javax.swing.JMenuBar;

/**
 *
 * @author Romeo Carrara
 * @since 26.11.2015
 */
public class Screen extends CRFrame{
    
    public int ID;
    public static int fSize= Main.settings.fSize;
    
    public Screen(String s) {
        
        super(s);
        
        setSize(50.0, 65.0);
        //setResizable(false);
        
        JMenuBar menuBar= new JMenuBar();
        menuBar.add(new Development());
        setJMenuBar(menuBar);
    }
    
    
    
    /*public final void setSize(double width, double height){
        double w= width+ width/100D*(4D*fSize);
        double h= width+ width/100D*(4D*fSize);
        super.setSize(w, h);
    }*/
    
    
}
