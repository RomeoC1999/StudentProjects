package view;

import control.Controller;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import model.Flight;
import bulletflight.Utilities;

/**
 * A JDialog for creating and editing a Flight
 *
 * @author Romeo Carrara
 * @since 2016.07.06
 */
public class FlightDialog extends JDialog{
    
    private Flight flight;
    private boolean canSet= true;
    
    private final ArrayList<MyTextField> fieldsList= new ArrayList<>();
    
    private MyTextField speedF= new MyTextField();
    private MyTextField angleF= new MyTextField();
    private MyTextField h0F= new MyTextField();
    private MyTextField t0F= new MyTextField();
    private MyTextField x0F= new MyTextField();
    private MyTextField axF= new MyTextField();
    private MyTextField ayF= new MyTextField();
    private MyTextField nameF= new MyTextField();
    private JButton colorB= new JButton();
    
    private JButton delB= new JButton("Delete");
    private JButton backB= new JButton("Cancel");
    private JButton saveB= new JButton("Save");
    
    private final String ADVISE_CANNOT_EDIT= "You cannot edit parameters during the motion!";
    
    
    /**
     * 
     * @param controller the Controller Object
     */
    public FlightDialog(Controller controller){
        super(controller.getMainPanel().getMainFrame(), "New motion", JDialog.ModalityType.APPLICATION_MODAL);
        init(controller);
        
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    /**
     * 
     * @param controller the Controller Object
     * @param flight a Flight
     * @param canSet if user can change Flight fields
     */
    public FlightDialog(Controller controller, Flight flight, boolean canSet){
        
        super(controller.getMainPanel().getMainFrame(), flight.getName(), JDialog.ModalityType.APPLICATION_MODAL);
        this.flight= flight;
        this.canSet= canSet;
        
        init(controller);
        
        if(canSet==false){
            fieldsList.forEach(f -> f.setEditable(false));
        }
        
        NumberFormat formatter= NumberFormat.getInstance(Locale.getDefault());
        formatter.setMaximumFractionDigits(8);
        formatter.setMinimumFractionDigits(0);
        
        speedF.setText(formatter.format(flight.getInitialSpeed()));
        angleF.setText(formatter.format(flight.getInitialAngle()));
        h0F.setText(formatter.format(flight.getInitialHeight()));
        t0F.setText(formatter.format(flight.getStartTime()));
        x0F.setText(formatter.format(flight.getStartSpace()));
        axF.setText(formatter.format(flight.getXAcceleration()));
        ayF.setText(formatter.format(flight.getYAcceleration()));
        nameF.setText(flight.getName());
        colorB.setBackground(flight.getColor());
        
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    /**
     * Initialize the dialog and set listeners for buttons
     */
    private void init(Controller controller){
        
        JPanel gridP= new JPanel(new GridLayout(0, 2));
        JPanel buttonsP= new JPanel(new GridLayout(1, 3));
        this.setLayout(new BorderLayout());
        this.add(gridP, BorderLayout.NORTH);
        this.add(buttonsP, BorderLayout.SOUTH);
        
        gridP.add(new JLabel("Speed (m/s):        "));
        gridP.add(speedF);
        gridP.add(new JLabel("Angle (°):            "));
        gridP.add(angleF);
        gridP.add(new JLabel("Initial height (m):   "));
        gridP.add(h0F);
        
        gridP.add(new JLabel("Initial time (s):  "));
        gridP.add(t0F);
        gridP.add(new JLabel("Initial x position (m): "));
        gridP.add(x0F);
        gridP.add(new JLabel("Initial x acceleration (m/s^2): "));
        gridP.add(axF);
        gridP.add(new JLabel("Initial y acceleration (m/s^2): "));
        gridP.add(ayF);
        
        gridP.add(Box.createGlue());
        gridP.add(Box.createGlue());
        gridP.add(new JLabel("New flight:             "));
        gridP.add(nameF);
        gridP.add(new JLabel("Color:                 "));
        colorB.setBackground(Utilities.getColor(controller.getTemporaryFlightGroup().size()));
        gridP.add(colorB);
        gridP.add(Box.createGlue());
        gridP.add(Box.createGlue());
        
        ayF.setText(String.valueOf(Flight.GRAVITATIONAL_CONSTANT));
        
        delB.setBackground(Color.RED);
        buttonsP.add(delB);
        backB.setBackground(Color.CYAN);
        buttonsP.add(backB);
        saveB.setBackground(Color.GREEN);
        buttonsP.add(saveB);
        
        colorB.addActionListener(evt -> {
            if(canSet){
                Color c= JColorChooser.showDialog(this, "Color choice", colorB.getBackground());
                if(c!=null){
                    colorB.setBackground(c);
                }
            }else{
                JOptionPane.showMessageDialog(this, ADVISE_CANNOT_EDIT);
            }
        });
        
        delB.addActionListener(evt -> {
            if(canSet){
                this.flight= null;
                dispose();
            }else{
                JOptionPane.showMessageDialog(this, ADVISE_CANNOT_EDIT);
            }
        });
        
        backB.addActionListener(evt -> {
            dispose();
        });
        
        saveB.addActionListener(evt -> {
            if(canSet){
                boolean flag= scanFields();
                if(flag){
                    dispose();
                }
            }else{
                JOptionPane.showMessageDialog(this, ADVISE_CANNOT_EDIT);
            }
        });
        
        this.addWindowListener(new WindowAdapter(){
            public void windowDeactived(WindowEvent evt){
                dispose();
            }
            public void windowClosing(WindowEvent evt){
                dispose();
            }
        });
        
    }
    
    
    /**
     * Reads all fields and creates Flight Field if text field values are correct<br>
     * Shows an error Dialog if there are errors
     * 
     * @return <code>true</code> if all text fields are correct
     */
    private boolean scanFields(){
        
        try{
            
            fieldsList.forEach(f -> f.setText(f.getText().replaceAll(",", ".")));
            double speed= speedF.getText().isEmpty() ? 0D : Double.parseDouble(speedF.getText());
            double angle= angleF.getText().isEmpty() ? 0D : Double.parseDouble(angleF.getText());
            double h0= h0F.getText().isEmpty() ? 0D : Double.parseDouble(h0F.getText());
            
            String name= nameF.getText();
            
            if(speed==0D && angle==0D && h0==0D){
                JOptionPane.showMessageDialog(this, "At least one of the first three parameters must be non-zero");
                return false;
            }
            if(angle==0D && h0==0D){
                JOptionPane.showMessageDialog(this, "You need an initial angle or an initial height to proceed");
                return false;
            }
            if(h0==0D && speed==0D){
                JOptionPane.showMessageDialog(this, "You need an initial speed or an initial height to proceede");
                return false;
            }
            if(angle<-90D || angle>90D){
                JOptionPane.showMessageDialog(this, "The angle must be between -90 and 90");
                return false;
            }
            if(h0<0){
                JOptionPane.showMessageDialog(this, "Initial height cannot be negative");
                return false;
            }
            if(speed<0 && angle!=90D){
                JOptionPane.showMessageDialog(this, "Initial speed cannot be negative");
                return false;
            }
            
            
            Flight f= new Flight(speed, angle, h0, name);
            
            if(!t0F.getText().isEmpty()){
                double t0= Double.parseDouble(t0F.getText());
                f.setStartTime(t0);
            }
            if(!x0F.getText().isEmpty()){
                double x0= Double.parseDouble(x0F.getText());
                f.setStartSpace(x0);
            }
            if(!axF.getText().isEmpty()){
                double a= Double.parseDouble(axF.getText());
                f.setXAcceleration(a);
            }
            if(!ayF.getText().isEmpty()){
                double a= Double.parseDouble(ayF.getText());
                f.setYAcceleration(a);
            }
            
            
            f.setColor(colorB.getBackground());
            this.flight= f;
            return true;
            
        }catch(Exception exc){
            JOptionPane.showMessageDialog(this, "Insert correct values, use the point as decimal separator");
            exc.printStackTrace();
            return false;
        }
        
    }
    
    
    /**
     * 
     * @return the Flight created or <code>null</code> if informations contain errors
     */
    public Flight getFlight(){
        return flight;
    }
    
    
    
    private class MyTextField extends JTextField{
        public MyTextField(){
            fieldsList.add(this);
        }
        public MyTextField(String text){
            super(text);
            fieldsList.add(this);
        }
    }
    
}
