package crstandard.gui;

import java.awt.Color;
import java.awt.Font;

/**
 * @author Romeo Carrara
 * @version 1.0 (2016.02.03)
 */
public class CRUtilView {
    
    /**
     * @param f Font which will rise
     * @param n Number of units f will raise
     * @return new Font whose size is risen
     */
    public static Font riseFont(Font f, int n){
        return new Font(f.getName(), f.getStyle(), f.getSize()+n);
    }
    
    /**
     * @return Light-Red Color
     */
    public static Color getLightRed(){
        return new Color(255, 102, 102);
    }
    
    /**
     * @return Cobalto Color
     */
    public static Color getCobalto(){
        return new Color(128, 170, 255);
    }
    
    
}
