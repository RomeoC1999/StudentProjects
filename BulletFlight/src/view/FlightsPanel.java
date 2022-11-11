package view;

import control.Controller;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import model.Flight;
import model.FlightGroup;
import bulletflight.Utilities;

/**
 * A Panel for containing and creating the flights
 * 
 * @author Romeo Carrara
 * @since 2016.07.06
 */
public class FlightsPanel extends ObserverPanel{
    
    private boolean canEdit= true;
    private ArrayList<FlightButton> flightButtons= new ArrayList<>();
    private JPanel gridPanel= new JPanel(new GridLayout(0, 1));
    private JButton addFlightButton= new JButton("Add flight");
    
    
    /**
     * @param controller the Controller Object
     */
    public FlightsPanel(Controller controller){
        
        this.controller= controller;
        controller.addObserver(this);
        init();
        
        addFlightButton.addActionListener(evt -> {
            FlightDialog fd= new FlightDialog(controller);
            Flight f= fd.getFlight();
            if(f != null){
                FlightButton fb= new FlightButton(fd.getFlight());
                flightButtons.add(fb);
                gridPanel.add(fb);
                controller.getMainPanel().getMainFrame().setVisible(true);
            }
        });
    }
    
    
    private void init(){
        
        gridPanel.add(addFlightButton);
        addFlightButton.setBackground(Color.GREEN);
        
        setLayout(new BorderLayout());
        add(Utilities.getScroll(gridPanel), BorderLayout.CENTER);
        
        setBorder(BorderFactory.createTitledBorder("Flights"));
        setVisible(true);
    }
    
    
    @Override
    public void resetFlights() {
        canEdit= true;
        addFlightButton.setEnabled(true);
    }

    @Override
    public void startFlights(FlightGroup flights) {
        canEdit= false;
        addFlightButton.setEnabled(false);
    }

    @Override
    public void update(double time, FlightGroup flights) {
    }
    
    /**
     * @return a Flight Group with flights created by user
     */
    public FlightGroup getFlights(){
        FlightGroup flights= new FlightGroup();
        flightButtons.forEach(fb -> flights.add(fb.flight));
        return flights;
    }
    
    
    /**
     * A JButton with a Flight field<br>
     * If it is clicked it opens a Flight Dialog for editing the flight
     */
    private class FlightButton extends JButton{
        
        private Flight flight;
        
        public FlightButton(Flight flight){
            
            this.flight= flight;
            setText(flight.getName());
            setBackground(Color.WHITE);
            
            addActionListener((ActionEvent evt) -> {
                FlightDialog fd= new FlightDialog(controller, this.flight, canEdit);
                Flight f= fd.getFlight();
                if(f == null){
                    flightButtons.remove(this);
                    gridPanel.remove(this);
                    controller.getMainPanel().getMainFrame().setVisible(true);
                }else{
                    this.flight= f;
                    setText(this.flight.getName());
                }
            });
            
        }
        
    }
    
}
