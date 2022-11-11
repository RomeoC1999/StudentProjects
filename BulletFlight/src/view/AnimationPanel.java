package view;

import control.ChartController;
import control.Controller;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import model.FlightGroup;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.XYToolTipGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import bulletflight.Utilities;

/**
 *
 * @author Romeo Carrara
 * @since 2016.05.26
 */
public class AnimationPanel extends ObserverPanel{
    
    private JFreeChart freeChart;
    private ChartPanel chartPanel;
    private ChartController chartController;
    
    
    public AnimationPanel(Controller controller){
        this.controller= controller;
        controller.addObserver(this);
        init();
    }
    
    
    /**
     * Initialize panel components
     */
    private void init(){
        
        setLayout(new BorderLayout());
        
        freeChart= ChartFactory.createXYLineChart(
            "",     //Titolo del grafico
            "Space (m)",     //Asse dei valori
            "Height (m)",     //Asse delle categorie
            null,   //DatasSet
            PlotOrientation.VERTICAL,
            false,  //Legenda
            true,   //ToolTips
            false   //Url
        );
        
        
        //Rendering grid lines
        XYPlot plot= freeChart.getXYPlot();
        plot.getRenderer().setBaseToolTipGenerator(xyToolTipGenerator);
        
        chartPanel= new ChartPanel(freeChart);
        add(chartPanel, BorderLayout.CENTER);
        setVisible(true);
        
        this.addComponentListener(new ComponentAdapter(){
            public void componentResized(ComponentEvent evt){
                if(chartController!=null){
                    controller.updateAll(controller.getTimeBySlider());
                }
            }
        });
        
    }
    
    
    /**
     * 
     * @return dimension of Chart Panel
     */
    public Dimension getChartPanelDimension(){
        return chartPanel.getSize();
    }
    
    /**
     * Sets chart panel invisible
     */
    @Override
    public void resetFlights() {
        chartPanel.setEnabled(false);
    }
    
    
    /**
     * Sets chart panel visible and create a <code>Chart Controller</code> object
     * 
     * @param flights the FlightGroup object
     */
    @Override
    public void startFlights(FlightGroup flights) {
        chartPanel.setEnabled(false);
        chartController= new ChartController(flights, chartPanel.getSize());
    }
    
    
    /**
     * Creates ChartController object
     * 
     * @param time the time as second
     * @param flights the FlightGroup object
     */
    @Override
    public void update(double time, FlightGroup flights) {
        Utilities.setColorDataset(freeChart, chartController.getDataset(time), flights);
        chartController.setCorrectRange(freeChart);
    }
    
    
    private XYToolTipGenerator xyToolTipGenerator= new XYToolTipGenerator(){
        public String generateToolTip(XYDataset dataset, int series, int item) {
            double x= dataset.getXValue(series, item);
            double y= dataset.getYValue(series, item);
            return String.format("<html>h: %.2f<br>x: %.2f</html>", y, x);
        }
    };
    
}
