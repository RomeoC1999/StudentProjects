package memory;

import crtournament.Screen;
import crtournament.StartScreen;
import crtournament.TourneyScreen;
import crtournament.Util;
import crstandard.gui.CRButton;
import crstandard.gui.CRLabel;
import crstandard.gui.CRUtilView;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Romeo Carrara
 * @since 29.11.2015
 */
public final class WarningDelete extends Screen{
    
    public CRLabel alertL= new CRLabel("Sei sicuro di voler eliminare il torneo?", fSize+6);
    
    public JPanel buttonsP= new JPanel();
    
    public CRButton yesB= new CRButton("Sì", fSize+6, Color.CYAN);
    public CRButton noB= new CRButton("No", fSize+6, CRUtilView.getLightRed());
    
    
    public WarningDelete(TourneyScreen screen) {
        
        super("Attenzione!");
        init();
        
        yesB.addActionListener((ActionEvent e) -> {
            screen.dayPanel.saveModifications(false);
            MemoryManager.writeFile();
            MemoryManager.delete();
            screen.dispose();
            dispose();
            new StartScreen(MemoryManager.getFile());
        });
        
    }
    
    public WarningDelete(StartScreen screen, File f){
        
        super("Attenzione!");
        
        if(f.isDirectory()){
            alertL.setText("Sei sicuro di voler eliminare l'intera cartella?");
            alertL.buildUpFont(-2);
        } else{
            alertL.setText("Sei sicuro di voler eliminare il torneo?");
        }
        
        init();
        
        yesB.addActionListener((ActionEvent e) -> {
            
            //Cancella tutti i file al suo interno
            if(f.isDirectory()){
                File[] files= f.listFiles();
                for(File tmp: files){
                    tmp.delete();
                }
            }
            
            f.delete();
            screen.dispose();
            dispose();
            new StartScreen(f.getParentFile());
        });
    }
    
    
    
    private void init(){
        
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setSize(30.0, 15.0);
        
        buttonsP.setLayout(new GridLayout(1, 0));
        
        buttonsP.add(yesB);
        buttonsP.add(noB);
        
        this.add(alertL, BorderLayout.CENTER);
        this.add(buttonsP, BorderLayout.SOUTH);
        
        noB.addActionListener((ActionEvent e) -> {
            dispose();
        });
        
        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent evt){
                dispose();
            }
            public void windowDeactivated(WindowEvent evt){
                dispose();
            }
        });
        
        setVisible(true);
    }
    
}
