package model;

import java.util.Vector;
import javax.swing.JComboBox;

/**
 *
 * @author Romeo Carrara
 * @since 2016.07.05
 */
public class FlightGroup extends Vector<Flight>{
    
    public static final double STANDARD_TIME= 10D;
    
    public Flight getFlightWithMaxTime(){
        int iMax= 0;
        
        for(int i=1; i<this.size(); i++){
            if(this.get(i).getFlightTime()> this.get(iMax).getFlightTime()){
                iMax= i;
            }
        }
        
        return this.get(iMax);
    }
        
    
    public Flight getFlightWithMaxSpace(){
        int iMax= 0;
        
        for(int i=1; i<this.size(); i++){
            if(this.get(i).getTotalHorizontalSpace() > this.get(iMax).getTotalHorizontalSpace()){
                iMax= i;
            }
        }
        
        return this.get(iMax);
    }
    
    
    public Flight getFlightWithMaxHeight(){
        int iMax= 0;
        
        for(int i=1; i<this.size(); i++){
            if(this.get(i).getMaxHeight() > this.get(iMax).getMaxHeight()){
                iMax= i;
            }
        }
        
        return this.get(iMax);
    }
    
    
    public void resetComboBox(JComboBox cb){
        cb.removeAllItems();
        this.forEach(cb::addItem);
    }
    
    public boolean areThereVerticals(){
        boolean flag= false;
        for(Flight f : this){
            if(f.isVertical()){
                flag= true;
            }
        }
        return flag;
    }
    
    public boolean areThereHorizontals(){
        boolean flag= false;
        for(Flight f : this){
            if(!f.isVertical()){
                flag= true;
            }
        }
        return flag;
    }
    
    
}
