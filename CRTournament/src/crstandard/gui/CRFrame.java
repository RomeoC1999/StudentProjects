package crstandard.gui;

import java.awt.Toolkit;
import javax.swing.JFrame;

/**
 *
 * @author Romeo Carrara
 * @version 1.0 (15.12.2015)
 */
public class CRFrame extends JFrame{
    
    private static int windows= 0;
    private static boolean idSetted= false;
    private int id;
    
    /**
     * 
     * constructor without parameters
     */
    public CRFrame(){
        init("", JFrame.EXIT_ON_CLOSE);
    }
    
    /**
     * 
     * @param title title of this frame
     */
    public CRFrame(String title){
        init(title, JFrame.EXIT_ON_CLOSE);
    }
    
    /**
     * 
     * @param closeOperation operation which close frame
     */
    public CRFrame(int closeOperation){
        init("", closeOperation);
    }
    
    /**
     * 
     * @param title title of this frame
     * @param closeOperation operation which close frame
     */
    public CRFrame(String title, int closeOperation){
        init(title, closeOperation);
    }
    
    /**
     * 
     * @param title title of this frame
     * @param closeOperation operation which close frame
     */
    public final void init(String title, int closeOperation){
        setTitle(title);
        setDefaultCloseOperation(closeOperation);
        setSize(50.0, 50.0);
        this.id= windows;
        windows++;
    }
    
    /**
     * 
     * @return ID of Frame
     */
    public int getId(){
        return this.id;
    }
    
    /**
     * 
     * set frame id.
     * when this method is called field "isSetted" will become true 
     * 
     * @param id new id code
     */
    public void setId(int id){
        this.id= id;
        idSetted= true;
    }
    
    /**
     * 
     * @return true if frames id aren't tidy
     */
    public boolean isSetted(){
        return idSetted;
    }
    
    /**
     * 
     * @param frame the frame to compare this frame against
     * @return true if ID of frames are equals
     */
    public boolean idEquals(CRFrame frame){
        if(getId() == frame.getId())
            return true;
        else
            return false;
    }
    
    /**
     * 
     * @param id the id code to compare this frame against
     * @return true if ID of frames are equals
     */
    public boolean idEquals(int id){
        if(getId() == id)
            return true;
        else
            return false;
    }
    
    /**
     * 
     * @param dim percntage dimension of width and height
     */
    public void setSize(double dim){
        setSize(dim, dim);
    }
    
    /**
     * 
     * @param width percentage width of screen
     * @param height percentage height of screen
     */
    public void setSize(double width, double height){
        
        Toolkit t= Toolkit.getDefaultToolkit();
        java.awt.Dimension d= t.getScreenSize();
        
        double w= d.getWidth()/100.0*width;
        double h= d.getHeight()/100.0*height;
        
        setSize(new java.awt.Dimension(Math.round((float)w), Math.round((float)h)));
        setLocationRelativeTo(null);
    }
    
    /**
     * @param components Components which they will be added on panel
     * 
     * @since 2016.02.23
     */
    public void add(javax.swing.JComponent... components){
        
        for(javax.swing.JComponent component : components){
            add(component);
        }
    }
    
}
