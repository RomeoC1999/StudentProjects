package crstandard.gui;


import java.awt.LayoutManager;
import javax.swing.JPanel;

/**
 *
 * @author Romeo Carrara
 * @version 1.0 (26.01.2016)
 */
public class CRPanel extends JPanel{
    
    /**
     * 
     * Constructor without parameters
     * 
     * @since 2016.01.26
     */
    public CRPanel(){
        super();
    }
    
    /**
     * 
     * @param layoutManager LayoutManager for this panel
     * @since 2016.01.26
     */
    public CRPanel(LayoutManager layoutManager){
        
        super();
        this.setLayout(layoutManager);
    }
    
    /**
     * 
     * @param grid grid
     * @param dim number of elements
     * @param max numbero elements of grid returned
     * @return scroll if dim senior than max, else a panel which contains "max" rows
     */
    public static javax.swing.JComponent getIdealPanel(JPanel grid, int dim, int max){
        
        if(dim<=max){
            JPanel tmp= new JPanel();
            tmp.setLayout(new java.awt.BorderLayout());
            tmp.add(grid, java.awt.BorderLayout.NORTH);
            tmp.add(new javax.swing.JLabel(), java.awt.BorderLayout.SOUTH);
            return tmp;
        } else{
            return new javax.swing.JScrollPane(grid);
        }
        
    }
    
    /**
     * 
     * @param components Components which they will be added on panel
     */
    public void add(javax.swing.JComponent... components){
        
        for(javax.swing.JComponent component : components){
            this.add(component);
        }
    }
    
    
}
