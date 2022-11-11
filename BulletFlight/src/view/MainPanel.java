package view;

import control.Controller;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.ToolTipManager;

/**
 *
 * @author Romeo Carrara
 * @since 2016.05.22
 */
public class MainPanel extends JPanel{
    
    private AnimationPanel animationPanel;
    private SliderPanel sliderPanel;
    
    private FlightsPanel flightsPanel;
    private FlightsInfoPanel flightsInfoPanel;
    
    private MainFrame mainFrame;
    
    private JPanel firstPanel= new JPanel();
    private JPanel secondPanel= new JPanel();
    
    
    /**
     * Create Main Panel
     * @param mainFrame the Main Frame
     */
    public MainPanel(MainFrame mainFrame){
        this.mainFrame= mainFrame;
        init();
    }
    
    
    /**
     * Initialize panel components
     */
    private void init(){
        
        JPanel centralPanel= new JPanel();
        JPanel eastPanel= new JPanel();
        
        Controller controller= new Controller(this);
        
        flightsPanel= new FlightsPanel(controller);
        flightsInfoPanel= new FlightsInfoPanel(controller);
        
        animationPanel= new AnimationPanel(controller);
        sliderPanel= new SliderPanel(controller);
        
        controller.resetAll();
        
        this.setLayout(new BorderLayout());
        this.add(centralPanel, BorderLayout.CENTER);
        this.add(eastPanel, BorderLayout.EAST);
        
        centralPanel.setLayout(new BorderLayout());
        centralPanel.add(animationPanel, BorderLayout.CENTER);
        centralPanel.add(sliderPanel, BorderLayout.SOUTH);
        
        eastPanel.setLayout(new BorderLayout());
        eastPanel.add(flightsPanel, BorderLayout.CENTER);
        eastPanel.add(flightsInfoPanel, BorderLayout.SOUTH);
                
        ToolTipManager.sharedInstance().setInitialDelay(0);
    }
    
    
    /**
     * @return the animationPanel
     */
    public AnimationPanel getAnimationPanel() {
        return animationPanel;
    }

    /**
     * @return the sliderPanel
     */
    public SliderPanel getSliderPanel() {
        return sliderPanel;
    }

    /**
     * @return the mainFrame
     */
    public MainFrame getMainFrame() {
        return mainFrame;
    }

    /**
     * @return the flightsPanel
     */
    public FlightsPanel getFlightsPanel() {
        return flightsPanel;
    }

    /**
     * @return the flightsInfoPanel
     */
    public FlightsInfoPanel getFlightsInfoPanel() {
        return flightsInfoPanel;
    }
    
    
}
