package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * A button whith the play-still function
 * 
 * @author Romeo Carrara
 * @since 2016.07.11
 */
public final class PlayStillButton extends JButton{
    
    private boolean playFunction= true;
    private ActionListener playListener;
    private ActionListener stillListener;
    private Color symbolColor= Color.BLACK;
    
    
    public PlayStillButton(){
        setBackground(Color.WHITE);
        
        addActionListener(evt -> invert());
        
    }
    
    
    /**
     * Sets a color for the symbol
     * @param color a Color to use for symbol 
     */
    public void setSymbolColor(Color color){
        this.symbolColor= color;
    }
    
    
    /**
     * @param actionListener an Action Listener fired when button has play function
     */
    public void setPlayListener(ActionListener actionListener){
        this.playListener= actionListener;
    }
    
    /**
     * @param actionListener an Action Listener fired when button has still function
     */
    public void setStillListener(ActionListener actionListener){
        this.stillListener= actionListener;
    }
    
    /**
     * You cannot set a text for this special button
     * 
     * @deprecated
     */
    public void setText(String text){
        super.setText("");
    }
    
    /**
     * You cannot set an icon for this special button
     * 
     * @deprecated
     */
    public void setIcon(Icon icon){
        
    }
    
    /**
     * @return <code>true</code> if the button is having play function
     */
    public boolean isPlay(){
        return playFunction;
    }
    
    /**
     * @return <code>true</code> if the button is having still function
     */
    public boolean isStill(){
        return !playFunction;
    }
    
    /**
     * Sets the play function
     */
    public void play(){
        if(isStill()){
            playFunction= true;
            if(playListener!=null){
                playListener.actionPerformed(null);
            }
        }
    }
    
    /**
     * Sets the still function
     */
    public void still(){
        if(isPlay()){
            playFunction= false;
            if(stillListener!=null){
                stillListener.actionPerformed(null);
            }
        }
    }
    
    /**
     * Inverts the function of Buttonn
     */
    public void invert(){
        if(isPlay()){
            still();
        }else{
            play();
        }
    }
    
    
    public void paintComponent(Graphics g){
        
        super.paintComponent(g);
        Graphics2D g2= (Graphics2D)g;
        Dimension dim= this.getSize();
        
        int side= (int)(Math.min(dim.height, dim.width)*0.4D);
        Point v= new Point((dim.width-side)/2, (dim.height-side)/2);
        
        g2.setColor(symbolColor);
        
        
        if(isPlay()){
            Polygon pol= new Polygon();
            pol.addPoint(v.x, v.y);
            pol.addPoint(v.x+side, v.y);
            pol.addPoint(v.x+side, v.y+side);
            pol.addPoint(v.x, v.y+side);
            g2.fill(pol);
        }else{
            Polygon pol= new Polygon();
            pol.addPoint(v.x, v.y);
            pol.addPoint(v.x+side, v.y+side/2);
            pol.addPoint(v.x, v.y+side);
            g2.fill(pol);
        }
        
    }
}
