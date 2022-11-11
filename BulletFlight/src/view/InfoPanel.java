package view;

import control.Controller;
import model.Flight;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import model.FlightGroup;
import model.Quantity;
import model.QuantityFactory;

/**
 *
 * @author Romeo Carrara
 * @since 2016.05.22
 */
public class InfoPanel extends ObserverPanel{
    
    protected JTextField angleField= new JTextField("30", 6);
    protected JTextField speedField= new JTextField("100", 6);
    protected JTextField h0Field= new JTextField(6);
    
    private SpeedSlider retarderSlider= new SpeedSlider();
    private JButton startButton= new JButton();
    private JButton otherFlightsButton= new JButton("Other flights");
    private JButton printAllChartsButton= new JButton();
    
    private JTextField timeField= new JTextField(6);
    
    private JComboBox<String> flightsCB= new JComboBox<>();
    private QuantityPanel[] quantityPanels= new QuantityPanel[QuantityFactory.getQuantitiesCount()];
    
    
    public InfoPanel(Controller controller){
        this.controller= controller;
        controller.addObserver(this);
        init();
    }
    
    
    /**
     * Initialize panel components
     */
    private void init(){
        
        setLayout(new GridLayout(0, 1));
        
        add(new DualPanel("Angle   (°)   ", angleField));
        add(new DualPanel("Speed   (m/s) ", speedField));
        add(new DualPanel("Height  (m)   ", h0Field));
        
        JLabel speedLabel= new JLabel("Speed");
        speedLabel.setToolTipText("Speed of the slider");
        add(new DualPanel(speedLabel, retarderSlider));
        
        retarderSlider.addChangeListener(evt -> {
            //System.out.printf("Valore px: %d, Valore retarder: %f\n", retarderSlider.getValue(), retarderSlider.getRetarderTime());
        });
        
        add(startButton);
        startButton.addActionListener(evt -> {
            if(startButton.getText().equals("Start")){
                scanFields();
            }else{
                controller.resetAll();
            }
        });
        
        otherFlightsButton.setBackground(Color.GREEN.brighter());
        add(otherFlightsButton);
        otherFlightsButton.addActionListener(evt -> {
            //controller.openFlightsTable(angleField.getText(), speedField.getText(), h0Field.getText());
        });
        
        add(new JLabel());
        add(new JLabel());
        add(new DualPanel("Time (s)", timeField));
        add(new JLabel());
        
        timeField.addActionListener(evt -> {
            try{
                String text= timeField.getText();
                float time= Float.parseFloat(text);
                controller.updateAll(time);
            }catch(Exception exc){
                exc.printStackTrace();
            }
        });
        
        add(flightsCB);
        
        flightsCB.addActionListener(evt -> controller.updateAll(controller.getTimeBySlider()));
        
        Quantity[] array= QuantityFactory.getQuantities();
        for(int i=0; i<array.length; ++i){
            quantityPanels[i]= new QuantityPanel(array[i]);
            add(quantityPanels[i]);
        }
        
        printAllChartsButton.setBackground(Color.GREEN.darker());
        printAllChartsButton.setText("Print all plots");
        
        add(printAllChartsButton);
        
        printAllChartsButton.addActionListener(evt -> {
            for(QuantityPanel panel : quantityPanels){
                panel.printChart();
            }
        });
        
        setBorder(BorderFactory.createTitledBorder("Info"));
    }
    
    
    private void scanFields(){
        
        try{
            
            double angle= angleField.getText().isEmpty()  ? 0 : Double.parseDouble(angleField.getText());
            double speed= speedField.getText().isEmpty()  ? 0 : Double.parseDouble(speedField.getText());
            double h0= h0Field.getText().isEmpty()        ? 0 : Double.parseDouble(h0Field.getText());
            
            if(angle<0 || angle>90){
                JOptionPane.showMessageDialog(null, "Angle must be between tra 0° e 90°");
            }else if(speed<0 && angle!=90D){
                String msg= "Velocity must be positive if the angle is not 90°";
                JOptionPane.showMessageDialog(null, msg);
            }else{
                /*Flight movement= new Flight(speed, angle, h0);
                FlightGroup mg= new FlightGroup();
                mg.add(movement);
                controller.startAll(mg);
                controller.updateAll(0F);*/
            }
            
        }catch(Exception exc){
            exc.printStackTrace();
            System.err.printf("L'utente non ha inserito nel formato corretto angolo (%s), velocità (%s) o h0 (%s)", angleField.getText(), speedField.getText(), h0Field.getText());
            JOptionPane.showMessageDialog(null, "Insert correct values");
        }
        
    }
    
    
    /**
     * 
     * @return value of retarder
     */
    public double getSpeed(){
        double value= retarderSlider.getSpeed();
        return value;
    }
    
    
    
    /**
     * Make input fields editable
     */
    @Override
    public void resetFlights() {
        startButton.setText("Start");
        startButton.setBackground(Color.GREEN);
        otherFlightsButton.setEnabled(true);
        
        angleField.setEditable(true);
        speedField.setEditable(true);
        h0Field.setEditable(true);
        
        timeField.setEditable(false);
        flightsCB.setEditable(false);
        flightsCB= new JComboBox<>();
        
        for(QuantityPanel panel : quantityPanels){
            panel.reset();
        }
        
        printAllChartsButton.setEnabled(false);
    }
    
    
    @Override
    public void startFlights(FlightGroup movements) {
        
        startButton.setText("New flight");
        startButton.setBackground(Color.CYAN);
        otherFlightsButton.setEnabled(false);
        
        angleField.setEditable(false);
        speedField.setEditable(false);
        h0Field.setEditable(false);
        
        timeField.setEditable(true);
        //flightsCB= movements.getComboBox();
        flightsCB.setSelectedIndex(0);
        
        for(QuantityPanel panel : quantityPanels){
            panel.start();
        }
        
        printAllChartsButton.setEnabled(true);
    }
    
    
    /**
     * Set the time in time field with Locale.US
     * 
     * @param time the time as second
     * @param flights the FlightGroup Object
     */
    @Override
    public void update(double time, FlightGroup flights){
        
        timeField.setText(String.format(Locale.US, "%.2f", time));
        
        for(QuantityPanel panel : quantityPanels){
            panel.update(time, flights.get(flightsCB.getSelectedIndex()));
        }
    }
    
    
    
    /**
     * Create Panel model for grid rows<br>
     * There are two plots:<br>
     * <ul>
     * <li><b>Center</b>: a label</li>
     * <li><b>East</b>: another component</li>
     * </ul>
     */
    private class DualPanel extends JPanel{
        
        /**
         * @param label string which will become a JLabel
         * @param cmp a component
         */
        public DualPanel(String label, Component cmp){
            this(new JLabel(label), cmp);
        }
        
        /**
         * 
         * @param cmp1 first component
         * @param cmp2 second component
         */
        public DualPanel(Component cmp1, Component cmp2){
            setLayout(new BorderLayout());
            add(cmp1, BorderLayout.CENTER);
            add(cmp2, BorderLayout.EAST);
        }
    }
    
    
    /**
     * Extends a JSlider with base is: <i>y = (1/200<sup>3</sup>)x<sup>3</sup></i><br>
     * <i>y = (1/(8*10<sup>6</sup>))x<sup>6</sup></i>
     */
    private class SpeedSlider extends JSlider{
        
        private double a= 1D/Math.pow(250D, 3);
        
        public SpeedSlider(){
            this.setMaximum(1200);
            this.setMinimum(0);
            this.setMinorTickSpacing(1);
            this.setSpeed(1D);
            
            this.addMouseMotionListener(new MouseAdapter(){
                public void mouseMoved(MouseEvent evt){
                    setToolTipText(getStringSpeed());
                }
            });
            
        }
        
        /**
         * 
         * @return speed as multiplier
         */
        public double getSpeed(){
            return a*Math.pow(getValue(), 3);
        }
        
        /**
         * How mutch bigger mest be flight spped
         * 
         * @param speed 
         */
        public void setSpeed(double speed){
            this.setValue((int)Math.pow(speed/a, 1/3D));
        }
        
        public String getStringSpeed(){
            
            double speed= getSpeed();
            
            if(speed<1D){
                return String.format("%.2f", speed);
            }else if(speed<10D){
                return String.format("%.1f", speed);
            }else{
                return String.format("%.0f", speed);
            }
            
        }
    }
    
    
    /**
     * A JPanel with three plots:<br>
     * <ul>
     * <li><b>West</b>: label with quantity name</li>
     * <li><b>Center</b>: value of quantity</li>
     * <li><b>East</b>: a button which print chart of quantity</li>
     * </ul>
     */
    private class QuantityPanel extends JPanel{

        private JButton chartButton;
        private JTextField field;
        private Quantity quantity;
        
        /**
         * @param quantity a Quantity
         */
        public QuantityPanel(Quantity quantity){

            this.quantity= quantity;

            chartButton= new JButton(Controller.SIGMA);
            chartButton.setBackground(Color.GREEN);
            
            field= new JTextField("x");
            field.setColumns(7);
            field.setEnabled(false);
            field.setDisabledTextColor(Color.BLACK);
            
            JPanel tmpPanel= new JPanel();
            tmpPanel.setLayout(new BorderLayout());
            tmpPanel.add(field, BorderLayout.CENTER);
            tmpPanel.add(chartButton, BorderLayout.EAST);
            
            setLayout(new BorderLayout());
            add(new JLabel(quantity.toString()), BorderLayout.CENTER);
            add(tmpPanel, BorderLayout.EAST);
            
            chartButton.addActionListener(evt -> printChart());
        }
        
        /**
         * Set chartButton disenabled
         */
        public void reset(){
            chartButton.setEnabled(false);
            chartButton.setToolTipText("");
            field.setText("");
        }
        
        /**
         * Set chartButton enabled
         */
        public void start(){
            chartButton.setEnabled(true);
            String name= quantity.getName();
            chartButton.setToolTipText(String.format("Click to obtain the plot %s/t and %s/%ss", name, name, Controller.DELTA));
        }
        
        /**
         * Change value of field to<br><code>quantity.getValueFromTime(time, movement)</code>
         * 
         * @param time as second
         * @param movement the Flight object
         */
        public void update(double time, Flight movement){
            double value= quantity.getValueFromTime(time, movement);
            field.setText(String.format("%.2f", value));
            field.setForeground(Color.BLACK);
        }
        
        /**
         * Ask to controller to print chart of quantity
         */
        public void printChart(){
            controller.printChart(quantity);
        }
        
        /**
         * Set the quantity
         * 
         * @param quantity the quantity
         */
        public void setQuantity(Quantity quantity){
            this.quantity= quantity;
        }
    }
    
}
