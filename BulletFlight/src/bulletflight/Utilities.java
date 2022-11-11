package bulletflight;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.geom.Rectangle2D;
import java.util.Random;
import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import model.FlightGroup;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYDataset;

/**
 *
 * @author Romeo Carrara
 * @since 2016.06.07
 */
public class Utilities {
    
    /**
     * <code>
     * Random r= new Random();<br>
     * return new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256));
     * </code>
     * 
     * @return a random color
     */
    public static Color getRandColor(){
        Random r= new Random();
        return new Color(r.nextInt(256), r.nextInt(256), r.nextInt(256));
    }
    
    /**
     * Returns a Color defined by index argument<br>
     * <ul>
     * <li color="red">0: Red</li>
     * <li color="blue">1: Blue</li>
     * <li color="green">2: Green</li>
     * <li color="cyan">3: Cyan</li>
     * <li color="magenta">4: Magenta</li>
     * <li color="pink">5: Pink</li>
     * <li color="black">another number: random color</li>
     * </ul>
     * 
     * @param index an index
     * @return a color defined by the index
     * @see Utilities.getRandColor
     */
    public static Color getColor(int index){
        switch(index){
            case 0: return Color.RED;
            case 1: return Color.BLUE;
            case 2: return Color.GREEN;
            case 3: return Color.CYAN;
            case 4: return Color.MAGENTA;
            case 5: return Color.PINK;
            default: return getRandColor();
        }
    }
    
    /**
     * Increases the font size of a component
     * 
     * @param cmp a Component
     * @param raise the units to increase
     */
    public static void increaseFontSize(Component cmp, int raise){
        Font f= cmp.getFont();
        Font n= new Font(f.getName(), f.getStyle(), f.getSize()+raise);
        cmp.setFont(n);
    }
    
    /**
     * Creates a scroll with the grid given
     * 
     * @param grid a Panel with GridLayout
     * @return a scroll pane
     */
    public static JScrollPane getScroll(JPanel grid){
        JPanel panel= new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(grid, BorderLayout.NORTH);
        panel.add(Box.createVerticalGlue(), BorderLayout.CENTER);
        return new JScrollPane(panel);
    }
    
    /**
     * Sets the dataset in the chart and colors the series
     * @param chart a JFreeChart
     * @param dataset a Dataset
     * @param flights a Flight Group
     */
    public static void setColorDataset(JFreeChart chart, XYDataset dataset, FlightGroup flights){
        
        chart.getXYPlot().setDataset(dataset);
        XYItemRenderer renderer= chart.getXYPlot().getRendererForDataset(dataset);
        
        for(int i=0; i<flights.size(); i++){
            renderer.setSeriesPaint(i, flights.get(i).getColor());
            renderer.setSeriesShape(i, new Rectangle2D.Float(0F, 0F, 10F, 10F));
        }
    }
    
}
