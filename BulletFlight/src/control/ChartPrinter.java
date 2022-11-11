package control;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import model.FlightGroup;
import model.Quantity;
import model.QuantityFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;

/**
 * 
 * @author Romeo Carrara
 * @since 2016.06.02
 */
public class ChartPrinter {
    
    private File directory;
    private FlightGroup flights;
    private ArrayList<Quantity> quantitiesAlreadyPrinted= new ArrayList<>(QuantityFactory.getQuantitiesCount());
    
    private final int LENGHT_IMAGE= 800;
    private final int HEIGHT_IMAGE= 500;
    
    
    /**
     * Create a chart printer for Quantity objects
     * 
     * @param flights the MovemenGroup object
     */
    public ChartPrinter(FlightGroup flights){
        this.flights= flights;
    }
    
    
    /**
     * Create a chart quantity-time for this quantity if it isn't already printed<br>
     * Ask to user to choose a directory if it is null<br>
     * If the quantity is instanceof <code>HeightQuantity</code> print also height-space chart
     * 
     * @param quantity a quantity
     * @param size the size of Chart Panel
     */
    public void printChartImage(Quantity quantity, Dimension size){
        
        boolean flag;
        
        if(directory==null){
            flag= createDirectory();
        }else{
            flag= true;
        }
        
        File quantityDir= new File(directory, quantity.getAbsoluteName());
        
        if(quantityDir.exists()){
            flag= true;
        }else{
            flag= quantityDir.mkdir();
        }
        
        if(flag && quantitiesAlreadyPrinted.contains(quantity) == false){
            
            quantitiesAlreadyPrinted.add(quantity);
            
            File timeChartFile= new File(quantityDir, quantity.getAbsoluteName() + "-Tempo.png");
            File spaceChartFile= new File(quantityDir, quantity.getAbsoluteName() + "-Spazio.png");
            
            try {
                timeChartFile.createNewFile();
                JFreeChart timeChart= quantity.getTimeFreeChart(flights, size);
                ChartUtilities.saveChartAsPNG(timeChartFile, timeChart, size.width, size.height);
                
                if(flights.areThereHorizontals() && (quantity instanceof QuantityFactory.HorizontalSpaceQuantity == false)){
                    spaceChartFile.createNewFile();
                    JFreeChart spaceChart= quantity.getSpaceFreeChart(flights, size);
                    if(quantity instanceof QuantityFactory.HeightQuantity){
                        ChartController.setCorrectRange(spaceChart, flights, size);
                    }
                    ChartUtilities.saveChartAsPNG(spaceChartFile, spaceChart, size.width, size.height);
                }
                
            } catch (IOException exc) {
                exc.printStackTrace();
            }
            
        }
        
    }
    
    
    /**
     * Ask to user to choose a directory and create it
     * 
     * @return false whenever user don't choose a directory
     */
    public boolean createDirectory(){
        
        JFileChooser chooser= new JFileChooser();
        chooser.setMultiSelectionEnabled(false);
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result= chooser.showSaveDialog(null);
        
        if(result==JFileChooser.APPROVE_OPTION){
            File parent= chooser.getSelectedFile();
            directory= new File(parent, "Flights");
            directory.mkdir();
            return true;
        }else{
            return false;
        }
        
    }
    
}
