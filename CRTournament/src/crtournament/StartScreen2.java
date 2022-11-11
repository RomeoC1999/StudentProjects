package crtournament;

import crstandard.gui.CRButton;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import memory.MemoryManager;

/**
 *
 * @author Romeo Carrara
 * @since 2016.04.23
 */
public class StartScreen2 extends Screen{
    
    private CRButton openB= new CRButton("Apri torneo", 3+fSize, Color.GREEN);
    private CRButton newTourneyB= new CRButton("Nuovo torneo", 3+fSize, Color.CYAN);
    private CRButton settingsB= new CRButton("Impostazioni", 1+fSize, Color.GRAY);
    
    
    public StartScreen2(){
        
        super("Shcermata iniziale");
        
        init();
        
    }
    
    
    private void init(){
        
        setLayout(new FlowLayout());
        add(openB);
        add(newTourneyB);
        add(settingsB);
        
        
        openB.addActionListener(new tryOpen());
        
        newTourneyB.addActionListener(evt ->{
            dispose();
            new InitScreen();
        });
        
        settingsB.addActionListener(evt ->{
        });
        
        setVisible(true);
    }
    
    
    
    private class tryOpen implements ActionListener{
        
        public void actionPerformed(ActionEvent evt){
            
            JFileChooser fileChooser= new JFileChooser(MemoryManager.championshipsDir);
            
            fileChooser.setMultiSelectionEnabled(false);
            
            int result= fileChooser.showOpenDialog(null);
            File file= fileChooser.getSelectedFile();
            
            MemoryManager.setFile(file);
            
            boolean flag= true;
            
            try{
                MemoryManager.readFile();
                dispose();
                new TourneyScreen();
                
            }catch(Exception exc){
                
            }
            
        }
        
        
    }
}
