package crtournament;

import java.io.Serializable;

/**
 *
 * @author Romeo Carrara
 * @since 2016.05.15
 */
public interface PointsStrategy extends Serializable{
    
    
    public static final String SEPARATOR= "VS";
    
    /**
     * 
     * @param match
     * @return String representation of point<br>
     * For example: "4-1" means Team A has 4 points, Team B has 1 point
     */
    public abstract String resultAsString(Match match);
    
}
