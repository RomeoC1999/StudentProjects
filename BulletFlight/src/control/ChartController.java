package control;

import java.awt.Dimension;
import model.Flight;
import model.FlightGroup;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.Range;
import org.jfree.data.function.Function2D;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author Romeo Carrara
 * @since 2016.05.29
 */
public class ChartController {
    
    private FlightGroup flightGroup;
    private Dimension size;
    
    private boolean thereAreVerticals;
    private double maxSpace;
    private double maxHeight;
    
    
    /**
     * 
     * @param flightGroup the Flight Group Object
     * @param size the size of ChartPanel
     */
    public ChartController(FlightGroup flightGroup, Dimension size){
        this.flightGroup= flightGroup;
        this.size= size;
        
        this.thereAreVerticals= flightGroup.areThereVerticals();
        this.maxSpace= flightGroup.getFlightWithMaxSpace().getTotalHorizontalSpace();
        this.maxHeight= flightGroup.getFlightWithMaxHeight().getMaxHeight();
    }
    
    
    /**
     * 
     * @param time the time as seconds
     * @return a dataset of information about the Flght Group
     */
    public XYDataset getDataset(double time){
        
        XYSeriesCollection seriesCollection= new XYSeriesCollection();
        
        for(Flight f : flightGroup){
            
            double space= f.getHorizontalSpace(time);
            
            if(f.isVertical() || space <= f.getStartSpace()){
                XYSeries series= new XYSeries(f);
                for(double i=0D; i<=time; i+=0.01D){
                    series.add(f.getStartSpace(), f.getHeight(i));
                }
                seriesCollection.addSeries(series);
            }else{
                Function2D function= (x -> f.getHeightFromHorizontalSpace(x));
                if(space>=f.getTotalHorizontalSpace() && !f.isInfiniteFlight()) space= f.getTotalHorizontalSpace();
                XYSeries series= DatasetUtilities.sampleFunction2DToSeries(function, f.getStartSpace(), space, size.width, f);
                seriesCollection.addSeries(series);
                //System.out.printf("Tempo var: %f\nSpazio: %f\nHeight1: %f, Height2: %f", time, space, f.getHeight(time), f.getHeightFromHorizontalSpace(space));
            }
            
        }
        
        return seriesCollection;
    }
    
    
    
    /**
     * 
     * @param freeChart a chart will be setted to correct range
     */
    public void setCorrectRange(JFreeChart freeChart){
        setCorrectRange(freeChart, thereAreVerticals, maxSpace, maxHeight, size);
    }
    
    
    /**
     * 
     * @param freeChart a chart will be setted to correct range
     * @param flights the Flight Group Object
     * @param size the size of chart panel
     */
    public static void setCorrectRange(JFreeChart freeChart, FlightGroup flights, Dimension size){
        
        boolean thereAreVerticals= flights.areThereVerticals();
        double maxSpace= flights.getFlightWithMaxSpace().getTotalHorizontalSpace();
        double maxHeight= flights.getFlightWithMaxHeight().getMaxHeight();
        
        setCorrectRange(freeChart, thereAreVerticals, maxSpace, maxHeight, size);
            
    }
    
    
    /**
     * 
     * @param freeChart a chart will be setted to correct range
     * @param thereAreVerticals if there are horizontal flights
     * @param maxSpace the maxium horizontal space altough flights
     * @param maxHeight the maxium height altough flights
     * @param size the size of chart panel
     */
    public static void setCorrectRange(JFreeChart freeChart, boolean thereAreVerticals, double maxSpace, double maxHeight, Dimension size){
        
        if(!thereAreVerticals){
            
            XYPlot plot= freeChart.getXYPlot();

            NumberAxis domainAxis= (NumberAxis)plot.getDomainAxis();
            NumberAxis rangeAxis= (NumberAxis)plot.getRangeAxis();

            domainAxis.setRange(0D, maxSpace*1.15D);
            rangeAxis.setRange(0D, maxHeight*1.15D);
            //Le dimensioni hanno uno spazio vuoto del 15%

            Range xRange= domainAxis.getRange();
            Range yRange= rangeAxis.getRange();

            double ux= xRange.getLength()/size.getWidth();
            double uy= yRange.getLength()/size.getHeight();
            double u= Math.max(ux, uy);

            domainAxis.setRange(0D, size.getWidth()*u);
            rangeAxis.setRange(0D, size.getHeight()*u);
            
        }else{
            
            XYPlot plot= freeChart.getXYPlot();
            
            NumberAxis domainAxis= (NumberAxis)plot.getDomainAxis();
            NumberAxis rangeAxis= (NumberAxis)plot.getRangeAxis();
            
            if(maxSpace!=0D){
                double tmp= maxSpace*1.15D;
                domainAxis.setRange(-(tmp*0.15D), tmp);
            }else{
                double tmp= maxHeight/2D*1.15D;
                domainAxis.setRange(-tmp, tmp);
            }
            
            
            rangeAxis.setRange(0D, maxHeight*1.15D);
            //Le dimensioni hanno uno spazio vuoto del 15%

            Range xRange= domainAxis.getRange();
            Range yRange= rangeAxis.getRange();
            
            //u= space/pixels
            double ux= xRange.getLength()/size.getWidth();
            double uy= yRange.getLength()/size.getHeight();
            double u= Math.max(ux, uy);
            
            if(maxSpace!=0D){
                domainAxis.setRange(-size.getWidth()*u*0.15D, size.getWidth()*u*0.85D);
            }else{
                domainAxis.setRange(-size.getWidth()/2*u, size.getWidth()/2*u);
            }
            rangeAxis.setRange(0D, size.getHeight()*u);
            
        }
        
    }
    
}
