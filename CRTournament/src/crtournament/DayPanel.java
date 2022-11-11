package crtournament;

import crstandard.gui.CRLabel;
import memory.MemoryManager;
import crstandard.gui.CRPanel;
import crstandard.gui.CRTextField;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 *
 * @author Romeo Carrara
 * @since 27.11.2015
 */
public final class DayPanel extends CRPanel{
    
    public MyTextField[] fieldsA;
    public MyTextField[] fieldsB;
    
    public MyLabel[] labelsA;
    public MyLabel[] labelsB;
    
    public MyLabel sleepTeam= new MyLabel("");
    
    public CRPanel p0= new CRPanel();
    public CRPanel p1= new CRPanel();
    public CRPanel p2= new CRPanel();
    public CRPanel p3= new CRPanel();
    public CRPanel p5= new CRPanel();
    public CRPanel p4= new CRPanel();
    
    public static final String VS_CONST = " VS ";
    
    
    public DayPanel(int day) {
        
        super();
        
        int dim= Main.champ.matchesForDay;
        
        if(Main.champ.isOdd)
            dim--;
        
        setLayout(new GridLayout(1, 3));
        
        p1.setLayout(new GridLayout(0, 1));
        p2.setLayout(new GridLayout(0, 1));
        
        p0.setLayout(new BorderLayout());
        
        p3.setLayout(new GridLayout(0, 1));
        p4.setLayout(new GridLayout(0, 1));
        p5.setLayout(new GridLayout(0, 1));
        
        p0.add(p4, BorderLayout.WEST);
        p0.add(p3, BorderLayout.CENTER);
        p0.add(p5, BorderLayout.EAST);
        
        add(p1, p0, p2);
        
        
        fieldsA= new MyTextField[dim];
        fieldsB= new MyTextField[dim];
        labelsA= new MyLabel[dim];
        labelsB= new MyLabel[dim];
        
        
        
        for(int i=0; i<dim; i++){
            
            fieldsA[i]= new MyTextField("", i, 'a', 0);
            fieldsB[i]= new MyTextField("", i, 'b', 0);
            labelsA[i]= new MyLabel("");
            labelsB[i]= new MyLabel("");

            fieldsA[i].addKeyListener(new MyKeyAdapt());
            fieldsB[i].addKeyListener(new MyKeyAdapt());
            
            p1.add(labelsA[i]);
            p4.add(fieldsA[i]);
            p3.add(new MyLabel(VS_CONST));
            p5.add(fieldsB[i]);
            p2.add(labelsB[i]);
        }
        
        
        if(Main.champ.isOdd){
            
            MyLabel l= new MyLabel("Riposo");
            l.setForeground(new Color(80, 76, 132));
            
            p1.add(sleepTeam);
            p4.add(l);
            p3.add(new MyLabel(""));
            p5.add(new MyLabel(""));
            p2.add(new MyLabel(""));
        }
        
        setComponents(Main.champ.currentDay);
        
    }
    
    
    
    public void setComponents(int day) {
        
        Match match;
        Match matchSleep= null;
        
        if(day<Main.champ.numberDays) {
            
            int j=0;
            
            for(int i=0; i<Main.champ.matchesForDay; i++, j++) {

                match= Main.champ.v[day][i];
                
                if(match.ta.name.equals(Championship.SLEEP) || match.tb.name.equals(Championship.SLEEP)){
                    
                    matchSleep= match;
                    j--;
                    
                }else{
                    
                    labelsA[j].setText(match.ta.name);
                    labelsB[j].setText(match.tb.name);

                    if(match.pa == Match.IS_NOT_PLAYED)
                        fieldsA[j].setText("");
                    else
                        fieldsA[j].setText(""+match.pa);

                    if(match.pb == Match.IS_NOT_PLAYED)
                        fieldsB[j].setText("");
                    else
                        fieldsB[j].setText(""+match.pb);
                    
                    fieldsA[j].setNMatch(i);
                    fieldsB[j].setNMatch(i);
                }
                
            }
            
            
            if(matchSleep != null){
                if(matchSleep.ta.name.equals(Championship.SLEEP))
                    sleepTeam.setText(matchSleep.tb.name);
                else
                    sleepTeam.setText(matchSleep.ta.name);
            }
        }
        
        fieldsA[0].requestFocusInWindow();
    }
    
    
    public class MyTextField extends CRTextField {
        
        int id;
        char ch;
        int nMatch;
        
        public MyTextField(String s, int i, char ch, int match) {
            
            super(s, 5);
            this.setColumns(3);
            this.setFontStyle(Font.BOLD);
            this.id= i;
            this.ch= ch;
            this.nMatch= match;
        }
        
        public void setNMatch(int nMatch){
            this.nMatch= nMatch;
        }
    }
    
    
    public class MyLabel extends CRLabel {
        public MyLabel(String s) {
            super(s, Screen.fSize+3);
        }
    }
    
    
    public void saveModifications(boolean write) {
        
        int pa, pb;
        int cd= Main.champ.currentDay;
        String tmp;
        
        int match;
        
        
        for(int i=0; i<fieldsB.length; i++) {
            
            match= fieldsB[i].nMatch;
            
            try {
                tmp= fieldsA[i].getText();
                
                if(tmp.equals(""))
                    pa= Match.IS_NOT_PLAYED;
                else
                    pa= Integer.parseInt(tmp);
                
            } catch(Exception exc) {
                pa= Match.IS_NOT_PLAYED;
            }
            
            
            try {
                tmp= fieldsB[i].getText();
                
                if(tmp.equals(""))
                    pb= Match.IS_NOT_PLAYED;
                else
                    pb= Integer.parseInt(tmp);
                
            } catch(Exception exc) {
                pb= Match.IS_NOT_PLAYED;
            }
            
            
            if(!fieldsA[i].getText().equals(""))
                fieldsA[i].setText(""+ pa);
            
            if(!fieldsB[i].getText().equals(""))
                fieldsB[i].setText(""+ pb);
            
            Main.champ.v[cd][match].pa= pa;
            Main.champ.v[cd][match].pb= pb;
        }
        
        
        if(write){
            MemoryManager.writeFile();
        }
        
    }
    
    
    private class MyKeyAdapt extends KeyAdapter{
        
        public void keyPressed(KeyEvent evt) {
            
            int key= evt.getKeyCode();
            int num= ((MyTextField)evt.getSource()).id;
            char ch= ((MyTextField)evt.getSource()).ch;
            
            
            //Try per scrupolo
            try {
                
                //Se viene premuta FRECCIA DESTRA
                if(key== KeyEvent.VK_RIGHT)
                    if(ch== 'a')
                        fieldsB[num].requestFocusInWindow();
                    else
                        if(num< fieldsA.length-2)
                            fieldsA[num+1].requestFocusInWindow();


                //Se viene premuta FRECIIA SINISTRA
                if(key== KeyEvent.VK_LEFT)
                    if(ch== 'b')
                        fieldsA[num].requestFocusInWindow();
                    else
                        if(num>0)
                            fieldsB[num-1].requestFocusInWindow();
                

                //Se viene premuta FRECCIA SU
                if((num>0) && (key== KeyEvent.VK_UP))
                    if(ch== 'a')
                        fieldsA[num-1].requestFocusInWindow();
                    else
                        fieldsB[num-1].requestFocusInWindow();


                //Se viene premuto FREGGIA GIU'
                if((num< fieldsA.length-1) && (key== KeyEvent.VK_DOWN))
                    if(ch== 'a')
                        fieldsA[num+1].requestFocusInWindow();
                    else
                        fieldsB[num+1].requestFocusInWindow();
                

                //Se viene premuto ENTER
                if(key== KeyEvent.VK_ENTER)
                    if(ch== 'a')
                        fieldsB[num].requestFocusInWindow();
                    else
                        if(num< fieldsA.length-1)
                            fieldsA[num+1].requestFocusInWindow();
            
            } catch(Exception exc) {
                
            }
        }
    } 
    
    
}