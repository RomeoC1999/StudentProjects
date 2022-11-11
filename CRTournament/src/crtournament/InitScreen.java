package crtournament;

import crstandard.gui.CRButton;
import crstandard.gui.CRLabel;
import crstandard.gui.CRPanel;
import crstandard.gui.CRTextField;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBox;

/**
 *
 * @author Romeo Carrara
 * @since 26.22.2015
 */
public final class InitScreen extends Screen{
    
    private CRPanel numberTeamsPanel= new CRPanel(new BorderLayout());
    private CRLabel label = new CRLabel("Digita il numero delle squadre:         ", fSize+5);
    private CRTextField field = new CRTextField("", fSize+8);
    private CRButton okB= new CRButton("Avanti", fSize+5, Color.CYAN);
    private JCheckBox randMod= new JCheckBox("Crea in modalità random");
    private CRLabel alertL= new CRLabel("", fSize+2, Color.RED.darker());
    private CRPanel southP= new CRPanel(new GridLayout(0, 1));
    private String alertString= "Inserire un numero compreso tra 2 e " + Util.MAX_TEAMS + "!";
    
    public InitScreen() {
        super("Creazione torneo");
        init();
    }
    
    
    private void init(){
        
        setDefaultCloseOperation(Screen.EXIT_ON_CLOSE);
        ID= 1;
        
        numberTeamsPanel.setLayout(new BorderLayout());
        numberTeamsPanel.add(label, BorderLayout.CENTER);
        numberTeamsPanel.add(field, BorderLayout.EAST);
        
        label.setCentralAlignment(false);
        southP.add(alertL, randMod, okB);
        randMod.setSelected(true);
        
        add(numberTeamsPanel, BorderLayout.CENTER);
        add(southP, BorderLayout.SOUTH);
        
        field.requestFocusInWindow();
        field.setColumns(5);
        
        field.addActionListener((ActionEvent evt) -> {
            goOn();
        });
        
        okB.addActionListener((ActionEvent evt) -> {
            goOn();
        });
        
        pack();
        setVisible(true);
    }
    
    
    private void goOn(){
        
        boolean ok= true;
        int number= -1;
        String s= field.getText();
            
        try{
            number= Integer.parseInt(s);
        } catch(Exception e) {
            alertL.setText(alertString);
            ok= false;
        }

        if(ok){
            if(number<2 || number > Util.MAX_TEAMS){
                alertL.setText(alertString);
            } else{
                this.dispose();
                Main.champ.randomMod= randMod.isSelected();
                Screen screen= new TeamsScreen(number, this.ID);
            }

        }
    }
    
    
    private class saveTourney implements ActionListener{

        public void actionPerformed(ActionEvent e) {
        }
        
    }
}
