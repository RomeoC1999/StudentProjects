package crstandard.gui;

import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Romeo Carrara
 * @version 1.0 (15.12.2015)
 */
public class CRLabel extends JLabel implements CRTextComponent{
    
    /**
     * 
     * Constructor without parameters
     * 
     * @since 2015.12.15
     */
    public CRLabel(){
        init("", 0, Color.BLACK);
    }
    
    /**
     * 
     * @param text text of label
     * @since 2015.12.15
     */
    public CRLabel(String text){
        init(text, 0, Color.BLACK);
    }
    
    /**
     * 
     * @param size text of label
     * @since 2015.12.15
     */
    public CRLabel(int size){
        init("", size, Color.BLACK);
    }
    
    /**
     * 
     * @param text text of label
     * @param size size font increase
     * @since 2015.12.15
     */
    public CRLabel(String text, int size){
        init(text, size, Color.BLACK);
    }
    
    /**
     * 
     * @param text text of label
     * @param size size font increase
     * @param color new background color
     * @since 2015.12.15
     */
    public CRLabel(String text, int size, Color color){
        init(text, size, color);
    }
    
    /**
     * 
     * @param text text of label
     * @param size size font increase
     * @param color new foreground color
     * @since 2015.12.15
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
     * @since 2015.12.15
     */
    public void buildUpFont(int sizeFont){
        Font f= this.getFont();
        this.setFont(new Font(f.getName(), f.getStyle(), f.getSize()+sizeFont));
    }
    
    /**
     * 
     * @param isCentral if the horizontal alignement is central or left
     * @since 2015.12.15
     */
    public void setCentralAlignment(boolean isCentral){
        if(isCentral == true){
            this.setHorizontalAlignment(CRLabel.CENTER);
        }else{
            this.setHorizontalAlignment(CRLabel.LEFT);
        }
    }
    
    /**
     * 
     * @since 2015.12.15
     */
    public void createBorder(){
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
    }
    
    /**
     * 
     * @param dim size of new border
     * @param color color of border
     * @since 2015.12.15
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
     * Modifier font size of this text component
     * 
     * @param size new font size
     * @since 2015.12.26
     */
    public void setFontSize(int size){
        Font f= this.getFont();
        this.setFont(new Font(f.getName(), f.getStyle(), size));
    }
    
    
    public void setMargin(int margin){
        setMargin(margin, margin);
    }
    
    public void setMargin(int horizontalMargin, int verticalMargin){
        setMargin(horizontalMargin, verticalMargin, horizontalMargin, verticalMargin);
    }
    
    public void setMargin(int top, int left, int bottom, int right){
        Border margin= new EmptyBorder(top, left, bottom, right);
        setBorder(new CompoundBorder(getBorder(), margin));
    }
    
    
}
