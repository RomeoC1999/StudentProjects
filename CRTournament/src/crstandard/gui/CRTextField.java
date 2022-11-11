package crstandard.gui;

import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JTextField;

/**
 *
 * @author Romeo Carrara
 * @version 1.0 (17.12.2015)
 */
public class CRTextField extends JTextField implements CRTextComponent{
    
    /**
     * 
     * Constructor without parameters
     * 
     * @since 2015.12.17
     */
    public CRTextField(){
        init("", 0, Color.BLACK);
    }
    
    /**
     * 
     * @param text text of label
     * @since 2015.12.17
     */
    public CRTextField(String text){
        init(text, 0, Color.BLACK);
    }
    
    /**
     * 
     * @param size text of label
     * @since 2015.12.17
     */
    public CRTextField(int size){
        init("", size, Color.BLACK);
    }
    
    /**
     * 
     * @param text text of label
     * @param size size font increase
     * @since 2015.12.17
     */
    public CRTextField(String text, int size){
        init(text, size, Color.BLACK);
    }
    
    /**
     * 
     * @param text text of label
     * @param size size font increase
     * @param color new foreGround color
     * @since 2015.12.17
     */
    public CRTextField(String text, int size, Color color){
        init(text, size, color);
    }
    
    /**
     * 
     * @param text text of label
     * @param size size font increase
     * @param color new foreground color
     * @since 2015.12.17
     */
    public final void init(String text, int size, Color color){
        this.setText(text);
        this.buildUpFont(size);
        this.setCentralAlignment(true);
        this.setForeground(color);
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
            this.setHorizontalAlignment(CRTextField.CENTER);
        }else{
            this.setHorizontalAlignment(CRTextField.LEFT);
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
     * @since 2015.12.26
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
     * @since 2015.12.26
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
     * @since 2015.12.26
     */
    public void setFontSize(int size){
        Font f= this.getFont();
        this.setFont(new Font(f.getName(), f.getStyle(), size));
    }
}
