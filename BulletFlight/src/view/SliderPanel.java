package view;

import control.Controller;
import model.Flight;
import java.awt.BorderLayout;
import java.util.Hashtable;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.Timer;
import model.FlightGroup;

/**
 *
 * @author Romeo Carrara
 * @since 2016.05.22
 */
public class SliderPanel extends ObserverPanel{
    
    private JSlider slider;
    private PlayStillButton playButton= new PlayStillButton();
    private Timer timer;
    private boolean running= false;
    
    
    public SliderPanel(Controller controller){
        this.controller= controller;
        controller.addObserver(this);
        init();
    }
    
    
    /**
     * Initialize components of this panel
     */
    private void init(){
        
        setLayout(new BorderLayout());
        slider= new JSlider(JSlider.HORIZONTAL);
        add(slider, BorderLayout.CENTER);
        add(playButton, BorderLayout.WEST);
        
        playButton.setPlayListener(evt -> {
            if(slider.getValue() == slider.getMaximum()){
                slider.setValue(slider.getMinimum());
            }
            setRunning(true);
        });
        
        playButton.setStillListener(evt -> setRunning(false));
        
        
        timer= new Timer(0, null);
        timer.setDelay(70);
        
        timer.addActionListener(evt ->{
            
            if(running){
                if(slider.getValue()>=slider.getMaximum()){
                    //Se è giunto al termine
                    setRunning(false);
                }else{
                    int difference= (int)(timer.getDelay()*(double)controller.getSpeed());
                    slider.setValue(slider.getValue() + difference);
                }
            }
            
        });
        
        slider.setBorder(BorderFactory.createEmptyBorder(12, 8, 12, 8));
        slider.addChangeListener(evt -> controller.updateAll(getTime()));
    }
    
    
    /**
     * 
     * @return true if slider is running
     */
    public boolean isRunning(){
        return running;
    }
    
    
    /**
     * Set running field and change play/still button
     * 
     * @param mustRun whenever slider and all system must run
     */
    public void setRunning(boolean mustRun){
        
        if(mustRun){
            running= true;
        }else{
            running= false;
        }
    }
    
    
    /**
     * 
     * @return time as second on slider
     */
    public double getTime(){
        return slider.getValue()/1000D;
    }
    
    
    /**
     * Make slider enabled and remove ticks
     */
    @Override
    public void resetFlights() {
        timer.stop();
        playButton.setEnabled(false);
        slider.setEnabled(false);
        slider.setPaintLabels(false);
        slider.setPaintTicks(false);
    }
    
    
    /**
     * Create slider object and start timer
     * 
     * @param flights the FlightGroup object
     */
    @Override
    public void startFlights(FlightGroup flights) {
        
        Flight movement= flights.getFlightWithMaxTime();
        
        int flightTime= (int)(movement.getFlightTime()*1000D);
        int unit= flightTime/15;
        
        Hashtable<Integer, JLabel> labelTable= new Hashtable<>();
        
        for(int i=0; i<=15; ++i){
            labelTable.put(i*unit, new JLabel(String.format("%.1f", (i*unit)/1000D)));
        }
        
        playButton.setEnabled(true);
        slider.setEnabled(true);
        
        slider.setMinimum(0);
        slider.setMaximum(flightTime);
        slider.setValue(0);
        
        slider.setLabelTable(labelTable);
        slider.setMajorTickSpacing(unit);
        slider.setMinorTickSpacing(1);
        
        slider.setPaintLabels(true);
        slider.setPaintTicks(true);
        
        timer.start();
        setRunning(true);
    }
    
    
    /**
     * Sets slider value
     * 
     * @param time the time as second
     * @param movements the FlightGroup object
     */
    @Override
    public void update(double time, FlightGroup movements) {
        slider.setValue((int)(time*1000D));
        if(slider.getValue() == slider.getMaximum()){
            setRunning(false);
            playButton.still();
        }
    }
    
    
    /**
     * 
     * @return slider object
     */
    public JSlider getSlider(){
        return slider;
    }
    
}
