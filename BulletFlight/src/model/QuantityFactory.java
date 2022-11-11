package model;

import control.Controller;

/**
 * QuantityFactory class create an array of quantities<br>
 * There are five quantities can be created:<br>
 * <ul>
 * <li>Speed</li>
 * <li>X-Speed</li>
 * <li>Y-Speed</li>
 * <li>Height</li>
 * <li>Horizontal space</li>
 * </ul>
 * 
 * @author Romeo Carrara
 * @since 2016.06.02
 */
public class QuantityFactory {
    
    /**
     * 
     * @return array of quantities:<br>
     */
    public static Quantity[] getQuantities(){
        
        Quantity[] array= new Quantity[getQuantitiesCount()];
        
        array[0] = new SpeedQuantity();
        array[1] = new XSpeedQuantity();
        array[2] = new YSpeedQuantity();
        array[3] = new HeightQuantity();
        array[4] = new HorizontalSpaceQuantity();
        //array[5] = new AccelerationQuantity();
        
        return array;
    }
    
    
    /**
     * 
     * @return number of quantities
     */
    public static int getQuantitiesCount(){
      return 5;
    }
    
    public static class SpeedQuantity extends Quantity{
        public String getName(){
            return "V";
        }
        public String getAbsoluteName(){
            return "Velocità";
        }
        public String getSystemUnit(){
            return "m/s";
        }
        public double getValueFromTime(double time, Flight flight){
            return flight.getSpeed(time);
        }
    }
    
    
    public static class XSpeedQuantity extends Quantity{
        public String getName(){
            return "Vx";
        }
        public String getAbsoluteName(){
            return "Velocità orizzontale";
        }
        public String getSystemUnit(){
            return "m/s";
        }
        public double getValueFromTime(double time, Flight flight){
            return flight.getXSpeed(time);
        }
    }
    
    
    public static class YSpeedQuantity extends Quantity{
        public String getName(){
            return "Vy";
        }
        public String getAbsoluteName(){
            return "Velocità verticale";
        }
        public String getSystemUnit(){
            return "m/s";
        }
        public double getValueFromTime(double time, Flight flight){
            return flight.getYSpeed(time);
        }
    }
    
    
    public static class HeightQuantity extends Quantity{
        public String getName(){
            return "h";
        }
        public String getAbsoluteName(){
            return "Altezza";
        }
        public String getSystemUnit(){
            return "m";
        }
        public double getValueFromTime(double time, Flight flight){
            return flight.getHeight(time);
        }
    }
    
    
    public static class HorizontalSpaceQuantity extends Quantity{
        public String getName(){
            return Controller.DELTA + "sx";
        }
        public String getAbsoluteName(){
            return "Spazio";
        }
        public String getSystemUnit(){
            return "m";
        }
        public double getValueFromTime(double time, Flight flight){
            return flight.getHorizontalSpace(time);
        }
    }
    
    
}
