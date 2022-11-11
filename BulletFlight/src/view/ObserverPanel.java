package view;

import control.Controller;
import javax.swing.JPanel;
import model.FlightGroup;

/**
 *
 * @author Romeo Carrara
 * @since 2016.05.27
 */
public abstract class ObserverPanel extends JPanel{
    
    protected Controller controller;
    
    public abstract void resetFlights();
    public abstract void startFlights(FlightGroup flights);
    public abstract void update(double time, FlightGroup flights);
    
}
