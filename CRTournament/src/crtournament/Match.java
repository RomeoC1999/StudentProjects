package crtournament;

import java.io.Serializable;

/**
 *
 * @author Romeo Carrara
 * @since 26.11.2015
 */
public class Match implements Serializable{
    
    public Team ta, tb;
    public int pa, pb;
    
    public static final int IS_NOT_PLAYED = Integer.MIN_VALUE;
    
    
    public Match(Team team1, Team team2) {
        
        ta= team1;
        tb= team2;
        
        pa= IS_NOT_PLAYED;
        pb= IS_NOT_PLAYED;
    }
    
    
    public String print() {
        
        return  ta.name + " vs " + tb.name + "\n";
    }
    
    
    
    public void updateTeams(Team[] v, PointsStrategy pointsStrategy) {
        
        boolean flag= true;
        
        //System.out.println(ta.name + " vs " + tb.name + " Punteggio: " + pa + " - " + pb);
        
        
        if(pa==IS_NOT_PLAYED)
            flag= false;
        
        if(pb==IS_NOT_PLAYED)
            flag= false;
        
        if(ta.name.equals(Championship.SLEEP))
            flag= false;
        
        if(tb.name.equals(Championship.SLEEP))
            flag= false;
        
        
        if(flag) {
            
            String[] points= pointsStrategy.resultAsString(this).split(PointsStrategy.SEPARATOR);
            
            v[ta.number-1].points+= Integer.parseInt(points[0]);
            v[tb.number-1].points+= Integer.parseInt(points[1]);
            
            v[ta.number-1].matches++;
            v[tb.number-1].matches++;
            
            
            v[ta.number-1].goalsA+= pa;
            v[ta.number-1].goalsP+= pb;
            
            v[tb.number-1].goalsA+= pb;
            v[tb.number-1].goalsP+= pa;
            
        }
    }

}
