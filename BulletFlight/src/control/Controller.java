package control;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import model.FlightGroup;
import model.Quantity;
import view.MainPanel;
import view.ObserverPanel;

/**
 *
 * @author Romeo Carrara
 * @since 2016.05.27
 */
public class Controller {
    
    private ArrayList<ObserverPanel> observerPanels= new ArrayList<>();
    private MainPanel mainPanel;
    private FlightGroup flights;
    private ChartPrinter chartPrinter;
    
    public static final String DELTA= new String(Character.toChars(916));
    public static final String SIGMA= new String(Character.toChars(931));
    
    
    /**
     * Create controller object
     * @param mainPanel the MainPanel
     */
    public Controller(MainPanel mainPanel){
        this.mainPanel= mainPanel;
    }
    
    /**
     * @return the Main Panel
     */
    public MainPanel getMainPanel(){
        return mainPanel;
    }
    
    /**
     * Add a panel to list of observerPanel
     * @param panel an observer panel
     */
    public void addObserver(ObserverPanel panel){
        observerPanels.add(panel);
    }
    
    /**
     * Resets all panels
     */
    public void resetAll(){
        observerPanels.forEach(panel -> panel.resetFlights());
        mainPanel.repaint();
    }
    
    /**
     * Starts all panels with FlightGroup
     * @return <code>true</code> if flight is regular
     */
    public boolean startAll(){
        
        FlightGroup fd= mainPanel.getFlightsPanel().getFlights();
        
        if(fd.getFlightWithMaxTime().getFlightTime()==0D){
            JOptionPane.showMessageDialog(mainPanel.getMainFrame(), "Impossibile far paritre i voli");
            return false;
        }else{
            this.flights= fd;
            observerPanels.forEach(panel -> panel.startFlights(flights));
            mainPanel.repaint();
            chartPrinter= new ChartPrinter(flights);
            return true;
        }
    }
    
    /**
     * Updates all panels
     * 
     * @param time time as seconds
     * @throws NullPointerException if movement is null
     */
    public void updateAll(double time){
        
        if(flights==null){
            System.err.println("#Controller #updateAll    Al tempo " + time + " l'oggetto movimento è nullo");
            throw new NullPointerException("Al tempo " + time + " l'oggetto movimento è nullo");
        }else{
            observerPanels.forEach(panel -> panel.update(time, flights));
        }
        
        mainPanel.repaint();
    }
    
    /**
     * Gives you knowledge about time by info panel
     * 
     * @return retarder value
     */
    public double getSpeed(){
        return mainPanel.getFlightsInfoPanel().getSpeed();
    }
    
    /**
     * Gives you knowledge about time by the slider
     * 
     * @return time as second
     */
    public double getTimeBySlider(){
        return mainPanel.getSliderPanel().getTime();
    }
    
    /**
     * Call a PrinterChart
     * 
     * @param quantity a quantity
     */
    public void printChart(Quantity quantity){
        
        if(mainPanel.getSliderPanel().isRunning()){
            mainPanel.getSliderPanel().setRunning(false);
            chartPrinter.printChartImage(quantity, mainPanel.getAnimationPanel().getChartPanelDimension());
            mainPanel.getSliderPanel().setRunning(true);
        }else{
            chartPrinter.printChartImage(quantity, mainPanel.getAnimationPanel().getChartPanelDimension());
        }
        
    }
    
    /**
     * @return teh FlightGroup designed in FlightsPanel
     * @see FlightsPanel
     */
    public FlightGroup getTemporaryFlightGroup(){
        return mainPanel.getFlightsPanel().getFlights();
    }
    
}
