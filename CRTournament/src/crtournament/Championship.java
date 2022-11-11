package crtournament;

import java.io.Serializable;

/**
 *
 * @author Romeo Carrara
 * @since 26.11.2015
 */
public final class Championship implements Serializable{
    
    public static final int MAX_TEAMS= 200;
    public static final String SLEEP= "Riposo";
    
    public int numberTeams;
    public int numberDays;
    public int matchesForDay;
    
    public boolean isOdd= true;
    public boolean randomMod;
    
    public int currentDay=0;
    
    public Match[][] v;
    public Team[] teams;
    
    public String name;
    public long creation;
    
    public PointsStrategy pointsStrategy= PersonalPointsStrategy.getStrategy(0);
    
    
    public Championship(){
        
    }
    
    
    
    public void init(int numberTeams, long creation) {
        
        this.creation= creation;
        
        //Operazioni per ottenere il numero di giornate, partite in una giornata e il numero di squadre
        this.numberDays=(numberTeams-(1-numberTeams%2));
	this.matchesForDay=((numberTeams%2+numberTeams)/2);
        this.numberTeams= numberTeams;
        
        
        v= new Match[numberDays][matchesForDay];
        
        if(numberTeams%2==0)
            isOdd= false;
        
        Util.createArrayOfmatches(this.randomMod, v, numberTeams);
        //createArrayOfMatches(this.randomMod);
    }
    
    
    public void createTeamsArray(int n) {
        
        if(n%2!=0)
            n++;
        
        teams= new Team[n];
    }
    
}
