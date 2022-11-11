package memory;

import crtournament.Screen;

/*
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;*/

/**
 *
 * @author Romeo Carrara
 * @since 06.12.2015
 */
public class WarningSending extends Screen{
    
    public WarningSending(){
        super("Invia");
    }
    
    
    /*
    public MyTextArea area= new MyTextArea();
    public JButton exitB= new JButton("Esci");
    
    public WarningSending(){
        
        super("Indicazioni");
        this.setMyDim(40.0, 20.0);
        
        this.add(area, BorderLayout.CENTER);
        this.add(exitB, BorderLayout.SOUTH);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        area.setText("");
        area.setEditable(false);
        
        area.append("INDICAZIONI PER L'INVIO DEL TORNEO\n\n");
        area.append("Nella cartella principale \"Da inviare\"\n");
        area.append("è stato creato il file \"" + MemoryManager.getFile().getName() + "\"\n\n");
        area.append("Inviarlo tramite mail o chiavetta alla persona interessata\n\n");
        area.append("Per aprirlo è sufficiente incollarlo nella cartella \"" + MemoryManager.addDir.getName() + "\"\n\n");
        area.append("Una volta inviato è vivamente consigliato cancellare la cartella\n");
        area.append("(Il vostro torneo non verrà eliminato)");
        
        
        exitB.setBackground(new Color(235, 73, 73));
        exitB.setFont(new Font(this.getFont().getName(), this.getFont().getStyle(), this.getFont().getSize()+8));
        exitB.setHorizontalAlignment(JButton.CENTER);
        
        
        exitB.addActionListener((ActionEvent evt) -> {
            this.dispose();
        });
    }
    
    
    public class MyTextArea extends JTextArea{
        
        public MyTextArea(){
            
        }
    }*/
}
