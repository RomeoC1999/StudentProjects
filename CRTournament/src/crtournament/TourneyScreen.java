package crtournament;

import memory.WarningDelete;
import memory.MemoryManager;
import crstandard.gui.CRButton;
import crstandard.gui.CRLabel;
import crstandard.gui.CRPanel;
import crstandard.gui.CRUtilView;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JMenuBar;

/**
 *
 * @author Romeo Carrara
 * @since 27.11.2015
 */
public final class TourneyScreen extends Screen{
    
    public static final int NUMBER_DAYS_BUTTONS= 11;
    
    public JComboBox<String> comboBox;
    public String[] daysString;
    
    public CRButton classifyB= new CRButton("Classifica", fSize+6, Color.CYAN);
    public CRButton deleteB= new CRButton("Elimina", fSize+6, CRUtilView.getLightRed());
    public CRButton changeNameB= new CRButton("Cambia nomi", fSize+6, Color.CYAN);
    public CRButton backStartB= new CRButton("Torna al menu", fSize+6, Color.CYAN);
    public CRButton sendB= new CRButton("Invia Torneo", fSize+6, Color.CYAN);
    
    public CRLabel title= new CRLabel("", fSize+24, Color.BLUE);
    
    public CRPanel southPanel= new CRPanel(new BorderLayout());
    public CRPanel centerP= new CRPanel(new BorderLayout());
    public DayPanel dayPanel;
    public CRPanel daysPanel= new CRPanel(new GridLayout(1, 0));
    public CRPanel buttonsPanel= new CRPanel(new GridLayout(0, 2));
    
    public JCheckBox showButtonsBox= new JCheckBox("Mostra pannello bottoni");
    
    
    public TourneyScreen() {
        
        super("");
        ID= 4;
        setDefaultCloseOperation(Screen.DISPOSE_ON_CLOSE);
        setSize(43.0, 73.0);
        setLayout(new BorderLayout());
        
        
        showButtonsBox.addActionListener((ActionEvent evt) -> {
            addButtonsPanel(Main.champ.currentDay);
        });
        
        if(Main.settings.showButtonsPanelInTourneyScreen){
            showButtonsBox.setSelected(true);
        } else {
            showButtonsBox.setSelected(false);
        }
        
        
        //dayButtons= new MyDayButton[ChampionshipCR.champ.numberDays];
        daysString= new String[Main.champ.numberDays];
        for(int i=0; i<daysString.length; i++) {
            daysString[i]= "Giornata " + (i+1);
        }
        
        addButtonsPanel(Main.champ.currentDay);
        
        comboBox= new JComboBox<>(daysString);
        comboBox.setSelectedIndex(Main.champ.currentDay);
        comboBox.setEditable(true);
        
        setTitle("Torneo, Giornata " + (Main.champ.currentDay+1));
        setTitle(Main.champ.currentDay);
        add(title, BorderLayout.NORTH);
        
        
        buttonsPanel.add(classifyB, backStartB, changeNameB, deleteB);
        
        southPanel.add(buttonsPanel, BorderLayout.CENTER);
        addButtonsPanel(Main.champ.currentDay);
        southPanel.add(daysPanel, BorderLayout.NORTH);
        
        add(southPanel, BorderLayout.SOUTH);
        dayPanel= new DayPanel(Main.champ.currentDay);
        add(Util.getScroll(dayPanel), BorderLayout.CENTER);
        
        
        JMenuBar menubar= new JMenuBar();
        menubar.setLayout(new BorderLayout());
        menubar.add(comboBox, BorderLayout.WEST);
        menubar.add(new Development(), BorderLayout.CENTER);
        menubar.add(showButtonsBox, BorderLayout.EAST);
        setJMenuBar(menubar);
        
        
        changeNameB.addActionListener((ActionEvent evt) -> {
            dayPanel.saveModifications(true);
            this.dispose();
            new TeamsScreen(Main.champ.numberTeams, this.ID);
        });
        
        deleteB.addActionListener((ActionEvent evt) -> {
            new WarningDelete(this);
        });
        
        backStartB.addActionListener((ActionEvent evt) -> {
            dayPanel.saveModifications(true);
            dispose();
            new StartScreen(MemoryManager.getFile().getParentFile());
        });
        
        classifyB.addActionListener((ActionEvent evt) -> {
            dayPanel.saveModifications(false);
            this.dispose();
            new Classification(0, false);
        });
        
        comboBox.addActionListener((ActionEvent evt) -> {
            changeDay(comboBox.getSelectedIndex());
        });
        
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                dayPanel.saveModifications(true);
                ((Screen)(evt.getSource())).dispose();
                System.exit(0);
            }
        });
        
        setVisible(true);
    }
    
    
    public void setTitle(int n) {
        title.setText("Giornata " + (n+1));
        comboBox.setSelectedIndex(n);
    }
    
    
    
    /**
     * Days start from 0
     * @param day
     */
    public void changeDay(int day){
        
        //Cambia se il numero è accettabile o diverso da quello corrente
        if(day>=0 && day< Main.champ.numberDays && day!= Main.champ.currentDay){
            
            dayPanel.saveModifications(false);
            Main.champ.currentDay= day;
            addButtonsPanel(day);
            
            dayPanel.setComponents(Main.champ.currentDay);
            setTitle("Torneo, Giornata " + (Main.champ.currentDay+1));
            setTitle(Main.champ.currentDay);
            
            setVisible(true);
        }
        
        
    }
    
    
    public void addButtonsPanel(int day){
        
        try{
            southPanel.remove(daysPanel);
        }catch(Exception exc){
            exc.printStackTrace();
        }
        
        if(showButtonsBox.isSelected()){
            daysPanel= DayButtons.getPanel(this, day);
            southPanel.add(daysPanel, BorderLayout.NORTH);
            Main.settings.showButtonsPanelInTourneyScreen= true;
        } else {
            Main.settings.showButtonsPanelInTourneyScreen= false;
        }
        
        setVisible(true);
        
    }
    
    
    /*public void sendFile(){
        
        MemoryManager.sendDir.mkdir();
        
        File f= MemoryManager.getFile();
        
        try{
            
            File n= new File(MemoryManager.sendDir + File.separator + f.getName());
            n.createNewFile();
            
            ObjectInputStream ois= new ObjectInputStream(new FileInputStream(f));
            Championship champ= (Championship)ois.readObject();
            
            ObjectOutputStream oos= new ObjectOutputStream(new FileOutputStream(n));
            oos.writeObject((Championship)champ);
            
            ois.close();
            oos.close();
            
        }catch(IOException | ClassNotFoundException exc){
            
            System.out.println(exc.toString());
        }
    }*/
}
