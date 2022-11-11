package crtournament;

import memory.MemoryManager;
import crstandard.gui.CRLabel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import memory.MemoryUtilities;

/**
 *
 * @author Romeo Carrara
 * @since 27.11.2015
 */
public class TeamsScreen extends Screen{
    
    public JLabel title= new JLabel("Inserire i nomi delle squadre");
    public JPanel fieldsP= new JPanel();
    public JPanel numbersP= new JPanel();
    public JButton okB= new JButton("Salva");
    
    public JTextField[] fieldsArray;
    
    public int calledBy;
    public int n;
    
    public JTextField nameTourneyF= new JTextField("");
    public CRLabel nameTourneyL= new CRLabel("Nome torneo: ", 6);
    
    public static final String ALREADY_USED= "Nome già in uso!";
    
    
    public TeamsScreen(int n, int calledBy) {
        
        super("Nomi delle squadre");
        this.setDefaultCloseOperation(Screen.DISPOSE_ON_CLOSE);
        this.ID= 2;
        this.calledBy= calledBy;
        this.n= n;
        
        fieldsArray= new JTextField[n];
        
        Font f= title.getFont();
        title.setFont(new Font(f.getName(), f.getStyle(), f.getSize()+fSize+4));
        title.setBackground(Color.BLUE);
        title.setHorizontalAlignment(JLabel.CENTER);
        
        
        okB.setFont(new Font(f.getName(), f.getStyle(), f.getSize()+fSize+4));
        okB.setBackground(new Color(177, 213, 249));
        okB.setHorizontalAlignment(JLabel.CENTER);
        okB.addActionListener(myList);
        
        
        nameTourneyF.setFont(new Font(f.getName(), f.getStyle(), f.getSize()+fSize+2));
        nameTourneyF.setBackground(Color.CYAN);
        nameTourneyF.setHorizontalAlignment(JLabel.CENTER);
        nameTourneyF.addActionListener(myList);
        nameTourneyL.setForeground(Color.BLUE);
        
        
        fieldsP.setLayout(new GridLayout(0, 1));
        numbersP.setLayout(new GridLayout(0, 1));
        
        
        fieldsP.add(nameTourneyF);
        numbersP.add(nameTourneyL);
        
        for(int i=0; i<n; i++) {
            numbersP.add(new CRLabel("Squadra " + (i+1) + ":", fSize+6));
            
            if(calledBy==1)
                fieldsArray[i]= new JTextField("");
            else if(calledBy == 4)
                fieldsArray[i]= new JTextField(Main.champ.teams[i].name);
            
            fieldsP.add(fieldsArray[i]);
            fieldsArray[i].addActionListener(myList);
            fieldsArray[i].setHorizontalAlignment(JTextField.CENTER);
            
            f= fieldsArray[i].getFont();
            fieldsArray[i].setFont(new Font(f.getName(), f.getStyle(), f.getSize()+fSize+2));
        }
        
        if(calledBy== 4) {
            nameTourneyF.setText(Main.champ.name);
        }
        
        
        JPanel centerP= new JPanel();
        centerP.setLayout(new BorderLayout());
        centerP.add(numbersP, BorderLayout.WEST);
        centerP.add(fieldsP, BorderLayout.CENTER);
        
        this.add(title, BorderLayout.NORTH);
        this.add(Util.getScroll(centerP), BorderLayout.CENTER);
        this.add(okB, BorderLayout.SOUTH);
        
        nameTourneyF.addActionListener(myList);
        
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                myClosing();
            }
        });
        
        setVisible(true);
    }
    
    
    
    ActionListener myList= (ActionEvent evt) -> {
        
        Object obj= evt.getSource();
        int i;
        
        
        if(obj== nameTourneyF) {
            fieldsArray[0].requestFocusInWindow();
        }
        
        
        if((obj == okB) || (obj == fieldsArray[fieldsArray.length-1])) {
            
            String s;
            String[] stringTeams= new String[n + (n%2)];
            
            if(calledBy== 1){
                Main.champ.createTeamsArray(n);
            }
            
            for(i=0; i<n; i++) {
                s= fieldsArray[i].getText();
                if(s.equals(""))
                    s= ("Squadra " + (i+1));
                stringTeams[i]= s;
            }
            
            
            if(n%2 !=0)
                stringTeams[n]= Team.SLEEP_CONST;
            
            
            File mainDir= MemoryManager.getDir();
            
            String text= nameTourneyF.getText();
            
            //Se il nome non appare
            if(((!MemoryUtilities.appear(new File(text))) || ((calledBy==4)&&(text.equals(Main.champ.name)))) && (!text.equals(ALREADY_USED))) {
                
                if(nameTourneyF.getText().equals("")) {
                    nameTourneyF.setText(MemoryUtilities.idealTourneyName());
                }
                
                /*if(nameTourneyF.getText().equals(ALREADY_USED)) {
                    nameTourneyF.setText(MemoryUtilities.idealTourneyName());
                }*/
                
                if(calledBy == 1) {
                    
                    for(i=0; i< stringTeams.length; i++) {
                        Main.champ.teams[i] = new Team(i+1, stringTeams[i]);
                    }
                    
                    Main.champ.init(n, System.currentTimeMillis());
                    Main.champ.name= nameTourneyF.getText();
                    
                    this.dispose();
                    Screen screen= new TourneyScreen();
                    
                    MemoryManager.setFile(new File(mainDir + File.separator + Main.champ.name));
                    MemoryManager.writeReadFile();
                    

                } else if(calledBy == 4){

                    for(i=0; i<Main.champ.teams.length; i++) {
                        //System.out.println("TeamsScreen - " + ChampionshipCR.champ.teams[i].name);
                        Main.champ.teams[i].name= stringTeams[i];
                    }
                    
                    this.dispose();
                    Main.champ.name= nameTourneyF.getText();
                    MemoryManager.writeReadFile();
                    
                    Screen screen= new TourneyScreen();
                }
            
            } else{
                nameTourneyF.setText(ALREADY_USED);
            }
        }
        
        
        for(i=0; i< fieldsArray.length-1; i++)
            if(obj == fieldsArray[i])
                fieldsArray[i+1].requestFocusInWindow();
    };
    
    
    public void myClosing() {
        
        if(calledBy==1) {
            dispose();
            System.exit(0);
        } else {
            dispose();
            Screen screen= new TourneyScreen();
        }
    }
    
    
}
