package crtournament;

import crstandard.gui.CRButton;
import crstandard.gui.CRPanel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Romeo Carrara
 * @since 2016.02.22
 */
public class DayButtons {
    
    public static final Color NOT_SELECTED= new Color(255, 204, 0);
    public static final Color SELECTED= new Color(255, 153, 102);
    public static final Color SPECIAL= new Color(255, 153, 0);
    
    private static int days;
    private static int number;
    private static int fSize= Screen.fSize+2;
    
    
    public static CRButton[] getArray(TourneyScreen caller, int n){
        
        days= Main.champ.numberDays;
        number= TourneyScreen.NUMBER_DAYS_BUTTONS;
        
        if(days<=number){
            return shortArray(caller, n);
        }
        
        CRButton[] buttons= new CRButton[number];
        
        //Inserimento bottoni speciali a inizio e fine
        buttons[0]= getSpecialLeft(caller);
        buttons[number-1]= getSpecialRight(caller);
        
        //Giornata ideale di partenza
        int start= n - ((number-1)/2)+1;
        
        //Se è negativo
        start = start<0 ? 0 : start;
        
        
        //Se non funziona la fine
        if(start+number-2>= days){
            int too= (start+number-2)-days;
            start-=too;
        }
        
        
        for(int i=1; i<number-1; i++){

            if(i+start-1==n){
                buttons[i]= new CRButton(Integer.toString(start+i), fSize, SELECTED);
            } else {
                buttons[i]= new CRButton(Integer.toString(start+i), fSize, NOT_SELECTED);
            }

            buttons[i].addActionListener(new MyActionListener(caller, i+start-1));
        }
        
        return buttons;
    }
    
    
    
    
    
    private static CRButton[] shortArray(TourneyScreen caller, int n){
        
        CRButton[] buttons= new CRButton[days+2];
        
        buttons[0]= getSpecialLeft(caller);
        buttons[days+1]= getSpecialRight(caller);
        
        for(int i=1; i<days+1; i++){

            if(i-1==n){
                buttons[i]= new CRButton(Integer.toString(i), fSize, SELECTED);
            } else {
                buttons[i]= new CRButton(Integer.toString(i), fSize, NOT_SELECTED);
            }
            
            buttons[i].addActionListener(new MyActionListener(caller, i-1));
        }

        return buttons;
    }
    
    
    private static CRButton getSpecialLeft(TourneyScreen caller){
        CRButton b= new CRButton("<<", fSize, SPECIAL);
        
        b.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                caller.changeDay(Main.champ.currentDay-1);
            }
        });
        
        return b;
    }
    
    private static CRButton getSpecialRight(TourneyScreen caller){
        CRButton b= new CRButton(">>", fSize, SPECIAL);
        
        b.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                caller.changeDay(Main.champ.currentDay+1);
            }
        });
        
        return b;
    }
    
    
    private static class MyActionListener implements ActionListener {
        
        private TourneyScreen caller;
        private int day;
                
        public MyActionListener(TourneyScreen caller, int day){
            this.caller= caller;
            this.day= day;
        }
        
        public void actionPerformed(ActionEvent e) {
            caller.changeDay(day);
        }
        
    }
    
    
    
    public static CRPanel getPanel(TourneyScreen caller, int n){
        
        CRButton[] buttons= getArray(caller, n);
        
        CRPanel tmp= new CRPanel(new java.awt.GridLayout(1, 0));
        
        for(CRButton b : buttons){
            tmp.add(b);
        }
        
        return tmp;
    }
    
    
}
