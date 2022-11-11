package model;

import java.awt.Dimension;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.function.Function2D;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import bulletflight.Utilities;

/**
 * 
 * @author Romeo Carrara
 * @since 2016.06.02
 */
public abstract class Quantity{
    
    /**
     * @return quantity's name
     */
    public abstract String getName();
    
    /**
     * @return quantity's absolute name
     */
    public abstract String getAbsoluteName();
    
    /**
     * @return the system unit of quantity
     */
    public abstract String getSystemUnit();
    
    /**
     * @param time as second
     * @param flight the MOvement object
     * @return value of this quantity at the time "time"
     */
    public abstract double getValueFromTime(double time, Flight flight);
    
    /**
     * This method call getValueFromTime:<br>
     * <code>return getValueFromTime(space*movement.getXSpeed(), movement);</code>
     * 
     * @param space horizontal space as metres
     * @param flight the Flight object
     * @return value of this quantity at the time "space"
     */
    public double getValueFromSpace(double space, Flight flight){
        return getValueFromTime(flight.getTimeFromHorizontalSpace(space), flight);
    }
    
    /**
     * <code>String.format("%s (%s)", getName(), getSystemUnit())</code>
     * 
     * @return a string representation like "q: value IS"
     */
    public String toString(){
        return String.format("%s (%s)", getName(), getSystemUnit());
    }
    
    /**
     * <code>String.format("%s (%s)", getAbsoluteName(), getSystemUnit())</code>
     * 
     * @return a string representation like "quantity: value IS"
     */
    public String toAbsoluteString(){
        return String.format("%s (%s)", getAbsoluteName(), getSystemUnit());
    }
    
    /**
     * 
     * @param flights the Flight Group to represent
     * @param size the size of Chart Panel
     * @return a chart like quantity-time
     */
    public JFreeChart getTimeFreeChart(FlightGroup flights, Dimension size){
        
        XYSeriesCollection collection= new XYSeriesCollection();
        
        for(Flight f : flights){
            XYSeries series;
            Function2D function= (t -> getValueFromTime(t, f));
            if(f.isInfiniteFlight()){
                series= DatasetUtilities.sampleFunction2DToSeries(function, f.getStartTime(), flights.getFlightWithMaxTime().getFlightTime(), size.width, f);
            }else{
                series= DatasetUtilities.sampleFunction2DToSeries(function, f.getStartTime(), f.getFlightTime(), size.width, f);
            }
            collection.addSeries(series);
        }
        
        String yAxisName= toAbsoluteString();
        String name= getAbsoluteName() + "-Tempo";
        
        JFreeChart chart= ChartFactory.createXYLineChart(name, "Tempo (s)", yAxisName, null);
        Utilities.setColorDataset(chart, collection, flights);
        
        return chart;
    }
    
    /**
     * 
     * @param flights the Flight Group to represent
     * @param size the size of Chart Panel
     * @return a chart like quantity-time
     */
    public JFreeChart getSpaceFreeChart(FlightGroup flights, Dimension size){
        
        XYSeriesCollection collection= new XYSeriesCollection();
        
        for(Flight f : flights){
            
            double space= f.getTotalHorizontalSpace();
            
            if(!f.isVertical()){
                Function2D function= (x -> getValueFromSpace(x, f));
                XYSeries series= DatasetUtilities.sampleFunction2DToSeries(function, f.getStartSpace(), f.getTotalHorizontalSpace()-0.0000001D, size.width, f);
                collection.addSeries(series);
            }
            
        }
        
        String yAxisName= toAbsoluteString();
        String name= getAbsoluteName() + "-Spazio";
        
        JFreeChart chart= ChartFactory.createXYLineChart(name, "Spazio (m)", yAxisName, null);
        Utilities.setColorDataset(chart, collection, flights);
        
        return chart;
    }
    
    
}

