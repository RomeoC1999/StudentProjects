package model;

import java.awt.Color;
import bulletflight.Utilities;

/**
 *
 * @author Romeo Carrara
 * @since 2016.05.22
 */
public class Flight implements Comparable {
    
    private double initialSpeed;
    private double initialAngle;
    private double initialHeight;
    
    private double startTime;
    private double startSpace;
    private double xAcceleration;
    private double yAcceleration= GRAVITATIONAL_CONSTANT;
    
    private Color color;
    private String name;
    
    public static final double GRAVITATIONAL_CONSTANT= -9.8D;
    
    
    
    /**
     * @param initialSpeed initial speed of shot
     * @param initialAngle initial angle of speeds
     * @param initialHeight height of thrower
     * @param name the name of shot
     */
    public Flight(double initialSpeed, double initialAngle, double initialHeight, String name){
        this.initialSpeed= initialSpeed;
        this.initialAngle= initialAngle;
        this.initialHeight= initialHeight;
        this.name= name;
        this.color= Utilities.getRandColor();
    }
    
    /**
     * @param initialSpeed initial speed of shot
     * @param initialAngle initial angle of speeds
     * @param initialHeight height of thrower
     * @param startTime the time which shot will start
     * @param startSpace the horizontal space where shot will start
     * @param ax the horizontal acceleration
     * @param ay the vertical acceleration
     * @param name the name of shot
     * @param color the color for identifing this shot
     */
    public Flight(double initialSpeed, double initialAngle, double initialHeight, double startTime,
            double startSpace, double ax, double ay, String name, Color color){
        
        this(initialSpeed, initialAngle, initialHeight, name);
        
        this.startTime= startTime;
        this.startSpace= startSpace;
        this.xAcceleration= ax;
        this.yAcceleration= ay;
        this.color= color;
    }
    
    
    /**
     * @param time the time as seconds
     * @return horizontal speed of shot
     */
    public double getXSpeed(double time){
        if(time<startTime || (time>getFlightTime()&&!isInfiniteFlight())){
            return 0D;
        }else{
            return getXAcceleration()*(time-startTime) + getInitialXSpeed();
        }
    }
    
    /**
     * @param time the time as seconds
     * @return vertical speed of shot
     */
    public double getYSpeed(double time){
        if(time<startTime || (time>getFlightTime()&&!isInfiniteFlight())){
            return 0D;
        }else{
            return getInitialYSpeed()+(getYAcceleration()*(time-startTime));
        }
    }
    
    /**
     * @param time the time as seconds
     * @return composition of vertical and horizontal speed
     */
    public double getSpeed(double time){
        double x= getXSpeed(time);
        double y= getYSpeed(time);
        return Math.sqrt(Math.pow(x,2) + Math.pow(y,2));
    }
    
    /**
     * @return initial horizontal speed of shot
     */
    public double getInitialXSpeed(){
        return initialSpeed*Math.cos(Math.toRadians(initialAngle));
    }
    
    /**
     * @return the initial vertical speed of shot
     */
    public double getInitialYSpeed(){
        return initialSpeed*Math.sin(Math.toRadians(initialAngle));
    }
    
    /**
     * @return the final speed of shot
     */
    public double getFinalSpeed(){
        return getSpeed(getFlightTime());
    }
    
    /**
     * @return final vertical speed
     */
    public double getFinalYSpeed(){
        return getYSpeed(getFlightTime());
    }
    
    /**
     * @return final horizontal speed
     */
    public double getFinalXSpeed(){
        return getXSpeed(getFlightTime());
    }
    
    /**
     * @param time the time as seconds
     * @return horizontal path as metres
     */
    public double getHorizontalSpace(double time){
        if(time<startTime){
            return startSpace;
        }else if(time>getFlightTime()&&!isInfiniteFlight()){
            return getTotalHorizontalSpace();
        }else{
            time-= startTime;
            double res= startSpace + getInitialXSpeed()*time + getXAcceleration()/2D*time*time;
            return res;
        }
    }
    
    /**
     * 
     * @param time the time as seconds
     * @return the height of shot
     */
    public double getHeight(double time){
        if(time<startTime){
            return initialHeight;
        }else if(time>=getFlightTime() && !isInfiniteFlight()){
            return 0D;
        }else{
            time-= startTime;
            return initialHeight + (getInitialYSpeed()*time) + (0.5D*getYAcceleration()*(time*time));
        }
    }
    
    
    /**
     * @param x as horizontal Space
     * @return the height of shot
     */
    public double getHeightFromHorizontalSpace(double x){
        return getHeight(getTimeFromHorizontalSpace(x));
    }
    
    /**
     * @param x as horizontal Space
     * @return the time when shot was in x position
     */
    public double getTimeFromHorizontalSpace(double x){
        if(x < startSpace){
            return 0D;
        }else{
            if(getXAcceleration()==0){
                return (x-startSpace)/getInitialXSpeed()+startTime;
            }else{
                double v0x= getInitialXSpeed();
                double a= getXAcceleration()/2D;
                double delta= v0x*v0x - 4*(a*(startSpace-x));
                return (-v0x + Math.sqrt(delta))/(2*a)+startTime;
            }
        }
    }
    
    /**
     * @return horizontal range of shot
     */
    public double getTotalHorizontalSpace(){
        double t= getFlightTime();
        if(isInfiniteFlight()){
            return 0D;
        }else{
            return getHorizontalSpace(t);
        }
    }
    
    /**
     * @return flight time
     */
    public double getFlightTime(){
        double v0y= getInitialYSpeed();
        double delta= v0y*v0y - 4*(initialHeight*getYAcceleration()/2D);
        if(delta<0 || getYAcceleration()==0D){
            //System.err.println("#Flight #getFlightTime Delta<0");
            return 0D;
        }
        double t= (-v0y - Math.sqrt(delta))/(getYAcceleration());
        return t+startTime;
    }
    
    /**
     * @return the time as seconds which shot has reached max height
     */
    public double getTimeMaxHeight(){
        if(isInfiniteFlight()){
            return 0D;
        }else{
            return (-getInitialYSpeed())/getYAcceleration() + startTime;
        }
    }
    
    /**
     * @return max height as metres
     */
    public double getMaxHeight(){
        return getHeight(getTimeMaxHeight());
    }
    
    /**
     * @return the initialSpeed
     */
    public double getInitialSpeed() {
        return initialSpeed;
    }

    /**
     * @return the initialAngle
     */
    public double getInitialAngle() {
        return initialAngle;
    }

    /**
     * @return the initialHeight
     */
    public double getInitialHeight() {
        return initialHeight;
    }
    
    /**
     * 
     * @return true if initial x speed equals 0
     */
    public boolean isVertical(){
        if(Math.abs(getInitialAngle()) == 90D){
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * @return the name of Flight
     */
    public String toString(){
        return name;
    }
    
    /**
     * 
     * @param time as seconds
     * @return a string data of spped, angle, etc...
     */
    public String dataToString(double time){
        StringBuilder sb= new StringBuilder();
        sb.append("Tempo:    ").append(time).append(System.lineSeparator());
        sb.append("Velocità: ").append(getSpeed(time)).append(System.lineSeparator());
        sb.append("Altezza:  ").append(getHeight(time)).append(System.lineSeparator());
        sb.append("Spazio x: ").append(getHorizontalSpace(time)).append(System.lineSeparator());
        return sb.toString();
    }
    
    /**
     * 
     * @return <code>true</code> if flight time is infinite
     */
    public boolean isInfiniteFlight(){
        return (getFlightTime()==0D);
    }
    
    /**
     * @return the startTime
     */
    public double getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(double startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the color
     */
    public Color getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the startSpace
     */
    public double getStartSpace() {
        return startSpace;
    }

    /**
     * @param startSpace the startSpace to set
     */
    public void setStartSpace(double startSpace) {
        this.startSpace = startSpace;
    }

    @Override
    public int compareTo(Object o) {
        return -1;
    }

    /**
     * @return the xAcceleration
     */
    public double getXAcceleration() {
        return xAcceleration;
    }

    /**
     * @param xAcceleration the xAcceleration to set
     */
    public void setXAcceleration(double xAcceleration) {
        this.xAcceleration = xAcceleration;
    }

    /**
     * @return the yAcceleration
     */
    public double getYAcceleration() {
        return yAcceleration;
    }

    /**
     * @param yAcceleration the yAcceleration to set
     */
    public void setYAcceleration(double yAcceleration) {
        this.yAcceleration = yAcceleration;
    }
    
}
