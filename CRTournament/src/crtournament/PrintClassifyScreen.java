package crtournament;

import crstandard.gui.CRButton;
import crstandard.gui.CRFrame;
import crstandard.gui.CRUtilView;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JTextArea;

/**
 *
 * @author Romeo Carrara
 * @since 07.12.2015
 */
public class PrintClassifyScreen extends CRFrame{
    
    private CRButton closeB= new CRButton("Ok", 8, CRUtilView.getLightRed());
    private JTextArea areaT= new JTextArea("");
    
    public PrintClassifyScreen(){
        
        setSize(27.0, 27.0);
        setDefaultCloseOperation(CRFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        
        areaT.setText("La classifica è stata\nstampata nella cartella\n\nClassifiche -> " + Main.champ.name);
        areaT.setEditable(false);
        
        Font f= areaT.getFont();
        areaT.setFont(new Font(f.getName(), f.getStyle(), f.getSize()+6));
        
        add(areaT, BorderLayout.CENTER);
        add(closeB, BorderLayout.SOUTH);
        
        
        //Ascoltatore del Bottone
        closeB.addActionListener((ActionEvent evt) -> {
            dispose();
        });
        
        //Ascoltatore di finestra
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                dispose();
            }
            public void windowDeactivated(WindowEvent evt){
                dispose();
            }
        });
        
        
        setVisible(true);
    }
    
}
