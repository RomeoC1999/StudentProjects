package crstandard.gui;

import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;

/**
 *
 * @author Romeo Carrara
 * @version 1.0 (17.12.2015)
 */
public class CRButton extends JButton implements CRTextComponent{
    
    /**
     * 
     * Constructor without parameters
     * @since 2015.12.17
     */
    public CRButton(){
        init("", 0, Color.BLACK);
    }
    
    /**
     * 
     * @param text text of label
     * @since 2015.12.17
     */
    public CRButton(String text){
        init(text, 0, Color.BLACK);
    }
    
    /**
     * 
     * @param size text of label
     * @since 2015.12.17
     */
    public CRButton(int size){
        init("", size, null);
    }
    
    /**
     * 
     * @param text text of label
     * @param size size font increase
     * @since 2015.12.17
     */
    public CRButton(String text, int size){
        init(text, size, null);
    }
    
    /**
     * 
     * @param text text of label
     * @param size size font increase
     * @param color new background color
     * @since 2015.12.17
     */
    public CRButton(String text, int size, Color color){
        init(text, size, color);
    }
    
    /**
     * 
     * @param text text of label
     * @param size size font increase
     * @param color new background color
     * @since 2015.12.17
     */
    public final void init(String text, int size, Color color){
        setText(text);
        buildUpFont(size);
        setCentralAlignment(true);
        
        if(color != null){
            this.setBackground(color);
        }
    }
    
    /**
     * 
     * @param sizeFont size font increase
     * @since 2015.12.17
     */
    public void buildUpFont(int sizeFont){
        Font f= this.getFont();
        this.setFont(new Font(f.getName(), f.getStyle(), f.getSize()+sizeFont));
    }
    
    /**
     * 
     * @param isCentral if the horizontal alignement is central or left
     * @since 2015.12.17
     */
    public void setCentralAlignment(boolean isCentral){
        if(isCentral == true){
            this.setHorizontalAlignment(CRButton.CENTER);
        }else{
            this.setHorizontalAlignment(CRButton.LEFT);
        }
    }
    
    /**
     * 
     * 
     * @since 2015.12.17
     */
    public void createBorder(){
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
    }
    
    /**
     * 
     * @param dim size of new border
     * @param color color of border
     * @since 2015.12.17
     */
    public void createBorder(int dim, Color color){
        this.setBorder(BorderFactory.createLineBorder(color, dim));
    }
    
    /**
     * 
     * Modifier font name of this text component
     * 
     * @param name new font name
     * @since 2015.12.17
     */
    public void setFontName(String name){
        Font f= this.getFont();
        this.setFont(new Font(name, f.getStyle(), f.getSize()));
    }
    
    /**
     * 
     * Modifier font style of this text component
     * 
     * @param style new font style
     * @since 2015.12.17
     */
    public void setFontStyle(int style){
        Font f= this.getFont();
        this.setFont(new Font(f.getName(), style, f.getSize()));
    }
    
    /**
     * 
     * Modifier font size of this text component
     * 
     * @param size new font size
     * @since 2015.12.17
     */
    public void setFontSize(int size){
        Font f= this.getFont();
        this.setFont(new Font(f.getName(), f.getStyle(), size));
    }
}
