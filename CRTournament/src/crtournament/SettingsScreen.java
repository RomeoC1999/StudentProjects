package crtournament;

import crstandard.gui.CRButton;
import crstandard.gui.CRLabel;
import crstandard.gui.CRPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JComboBox;
import memory.MemoryManager;
import memory.SettingsManager;

/**
 *
 * @author Romeo Carrara
 * @since 2016.03.06
 */
public class SettingsScreen extends Screen{
    
    
    private CRPanel westPanel= new CRPanel(new GridLayout(0, 1));
    private CRPanel centerPanel= new CRPanel(new GridLayout(0, 1));
    private CRPanel eastPanel= new CRPanel(new GridLayout(0, 1));
    
    private CRLabel sizeFontL= new CRLabel("Dimensione font: ", fSize+1);
    private CRButton saveB= new CRButton("Salva modifiche", fSize+2, Color.CYAN);
    
    private JComboBox<Integer> fontSizeComboBox= new JComboBox<>();
    
    public static final int standardDim= 20;
    
    
    public SettingsScreen(){
        super("Impostazioni");
        setSize(35.0, 22.0);
        init();
    }
    
    
    private void init(){
        
        add(westPanel, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);
        add(eastPanel, BorderLayout.EAST);
        add(saveB, BorderLayout.SOUTH);
        
        centerPanel.add(new CRLabel("Versione: ", fSize+1));
        eastPanel.add(new CRLabel(Main.settings.version));
        
        for(int i= 0; i<40; i++){
            fontSizeComboBox.addItem(new Integer(i));
        }
        
        centerPanel.add(sizeFontL);
        eastPanel.add(fontSizeComboBox);
        fontSizeComboBox.setSelectedItem(fSize+standardDim);
        fontSizeComboBox.setEditable(false);
        
        sizeFontL.setToolTipText(standardDim + " è la dimensione standard");
        fontSizeComboBox.setToolTipText(standardDim + " è la dimensione standard");
        
        
        saveB.addActionListener(evt -> {
            setNewData();
            backToStartScreen();
        });
        
        
        //Ascoltatore di finestra
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                backToStartScreen();
            }
        });
        
        
        setDefaultCloseOperation(Screen.DISPOSE_ON_CLOSE);
        setVisible(true);
        
    }
    
    
    public void backToStartScreen(){
        dispose();
        new StartScreen(MemoryManager.championshipsDir);
    }
    
    public void setNewData(){
        int newFontSize= (Integer)fontSizeComboBox.getSelectedItem();
        Main.settings.fSize= newFontSize-standardDim;
        Screen.fSize= newFontSize-standardDim;
        SettingsManager.writeSettings();
    }
    
}
