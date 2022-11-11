package crtournament;

import crstandard.gui.CRButton;
import crstandard.gui.CRLabel;
import crstandard.gui.CRPanel;
import crstandard.gui.CRTextField;
import crstandard.gui.CRUtilView;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import memory.CRMail;

/**
 * @author Romeo Carrara
 * @since 2016.03.18
 */
public class SendingScreen extends Screen{
    
    private static final int localSize= 5;
    
    private CRLabel titleL= new CRLabel("", fSize+localSize+2);
    private CRLabel recipientL= new CRLabel("Inserire l'indirizzo del destinatario:", fSize+localSize);
    private CRTextField recipientF= new CRTextField("", fSize+localSize);
    
    private CRLabel senderL= new CRLabel("Inserisci un nickname:", fSize+localSize);
    private CRTextField senderF= new CRTextField("", fSize+localSize);
    private CRLabel errorL= new CRLabel("", fSize+localSize, Color.RED);
    
    private CRPanel buttonsP= new CRPanel(new GridLayout(1, 0));
    private CRButton sendB= new CRButton("Invia", fSize+localSize+5, CRUtilView.getCobalto());
    private CRButton backB= new CRButton("Torna indietro", fSize+localSize+5, CRUtilView.getLightRed());
    
    
    public SendingScreen(File file){
        
        super("Invio del file");
        this.setLayout(new GridLayout(0, 1));
        this.setDefaultCloseOperation(Screen.DO_NOTHING_ON_CLOSE);
        
        buttonsP.add(sendB);
        buttonsP.add(backB);
        
        titleL.setOpaque(true);
        titleL.setBackground(Color.CYAN);
        titleL.setText("Invia file: " + file.getName());
        
        add(titleL);
        add(new CRLabel());
        add(recipientL);
        add(recipientF);
        add(new CRLabel());
        add(senderL);
        add(senderF);
        add(errorL);
        add(new CRLabel());
        add(buttonsP);
        
        this.pack();
        this.setVisible(true);
        
        sendB.addActionListener(evt -> {
            send(file);
        });
        
        backB.addActionListener(evt -> {
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
        
    }
    
    
    private void send(File file){
        
        CRMail mail= new CRMail(CRMail.MAIL_SERVICE, senderF.getText(), recipientF.getText(), "Invio torneo");
        
        StringBuilder sb= new StringBuilder();
        
        sb.append("<html>");
        sb.append(senderF.getText());
        sb.append(" ti ha inviato un torneo!<br><br>");
        
        sb.append("Per aprirlo scarica l'allegato di nome <b>");
        sb.append(file.getName());
        sb.append("</b> e successivamente copialo nella cartella <b>Tornei</b>, vicino al programma");
        sb.append("<br>Poi apri il programma per visualizzare correttamente il torneo<br>");
        sb.append("</html>");
        
        boolean flag= true;
        
        try {
            //mail.send(false);
        } catch (Exception exc) {
            errorL.setText("Errore nell'invio del messaggio");
            flag= false;
        }
        
        if(flag){
            dispose();
        }
        
    }
    
}
