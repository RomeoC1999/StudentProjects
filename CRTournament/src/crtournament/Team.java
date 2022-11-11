package crtournament;

import java.io.Serializable;

/**
 *
 * @author Romeo Carrara
 * @since 26.11.2015
 */
public class Team implements Serializable{
    
    public String name;
    public int number;
    
    public int points=0;
    public int matches=0;
    
    public int goalsA= 0;
    public int goalsP=0;
    
    public int wins= 0;
    public int balances=0;
    public int defeats=0;
    
    
    public static final String SLEEP_CONST= "Riposo";
    
    
    public Team(int number, String name) {
        
        this.name= name;
        this.number= number;
        
    }
    
    
    public static Team getTeam(int n) {
        
        for (Team team : Main.champ.teams) {
            if (team.number == n) {
                return team;
            }
        }
        
        return null;
    }
    
    
    public void reset() {
        this.matches=0;
        this.points=0;
        
        this.goalsA=0;
        this.goalsP=0;
        
        this.wins=0;
        this.balances=0;
        this.defeats=0;
    }
    
    public int differentGoals() {
        
        return (this.goalsA -  this.goalsP);
    }
    
}
