package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.UIManager;

/**
 * A JMenu which contains the program options
 * 
 * @author Romeo Carrara
 * @since 2016.06.04
 */
public class OptionMenu extends JMenu{
    
    private JFrame parent;
    
    private boolean systemLookAndFeel= true;
    private JMenuItem systemLookAndFeelItem= new JMenuItem("Use independent Look&Feel");
    
    
    
    /**
     * 
     * @param parent the JFrame parent
     */
    public OptionMenu(JFrame parent){
        super("Options");
        this.parent= parent;
        init();
    }
    
    
   public void init(){
       
       add(systemLookAndFeelItem);
       systemLookAndFeelItem.addActionListener(systemLookAndFeelActionListener);
       
       
   }
    
    ActionListener systemLookAndFeelActionListener= new ActionListener(){
        public void actionPerformed(ActionEvent evt){
            
            //Se è falso allora bisogna usare lo standard
            if(systemLookAndFeel==false){
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    systemLookAndFeel= true;
                    systemLookAndFeelItem.setText("Use independent Look&Feel");
                } catch (Exception exc) {
                    exc.printStackTrace();
                }
            }else{
                try {
                    UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                    systemLookAndFeel= false;
                    systemLookAndFeelItem.setText("Usa operative system Look&Feel");
                } catch (Exception exc) {
                    exc.printStackTrace();
                }
            }
            
            parent.repaint();
        }
    };
    
    
    
}
