package crtournament;

import crstandard.gui.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import memory.MemoryUtilities;

/**
 *
 * @author Romeo Carrara
 * @since 25.12.2015
 */
public class NewFolderScreen extends CRFrame{
    
    private final String ADVISE= "Nome già in uso!";
    
    private CRLabel insertionL= new CRLabel("Inserisci un nome:", Screen.fSize+5, Color.BLACK);
    private CRTextField insertionF= new CRTextField("", Screen.fSize+6);
    
    private CRButton okB= new CRButton("Crea Cartella", Screen.fSize+6, CRUtilView.getCobalto());
    private CRButton backB= new CRButton("Torna indietro", Screen.fSize+6, CRUtilView.getLightRed());
    
    private CRPanel southP= new CRPanel(new GridLayout(1, 0));
    
    /**
     * Il frame è una griglia di tre righe che contiene label, textfield e button
     * @param dir main directory 
     */
    public NewFolderScreen(File dir){
        
        super("Nuova cartella", CRFrame.DO_NOTHING_ON_CLOSE);
        setSize(37.0, 22.0);
        
        add(insertionL, BorderLayout.NORTH);
        add(insertionF, BorderLayout.CENTER);
        add(southP, BorderLayout.SOUTH);
        
        southP.add(backB);
        southP.add(okB);
        
        
        insertionF.addActionListener((ActionEvent e) -> {
            goOn(dir);
        });
        
        okB.addActionListener((ActionEvent e) -> {
            goOn(dir);
        });
        
        backB.addActionListener((ActionEvent e) -> {
            dispose();
            new StartScreen(dir);
        });
        
        
        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent evt){
                dispose();
                new StartScreen(dir);
            }
        });
        
        
        setVisible(true);
    }
    
    
    public final void goOn(File dir){
        
        String s= insertionF.getText();
        boolean flag= MemoryUtilities.appear(new File(s));
        
        if((flag==true) || s.equals(ADVISE)){
            insertionF.setText(ADVISE);
            
        } else{
            
            if(s.equals("")){
                s= MemoryUtilities.idealDirectoryName();
            }
            
            File d= new File(dir + File.separator + s);
            d.mkdir();
            this.dispose();
            new StartScreen(new File(d.getAbsolutePath()));
        }
    }
    
    
}
