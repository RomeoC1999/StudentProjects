package crtournament;

import memory.WarningDelete;
import memory.MemoryManager;
import crstandard.gui.CRButton;
import crstandard.gui.CRLabel;
import crstandard.gui.CRPanel;
import crstandard.gui.CRUtilView;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JMenuBar;
import javax.swing.SwingUtilities;

/**
 *
 * @author Romeo Carrara
 * @since 28.11.2015
 */
public class StartScreen extends Screen{
    
    private CRButton newTourneyB= new CRButton("Nuovo torneo", fSize+8, Color.CYAN);
    private CRButton newFolderB= new CRButton("Nuova cartella", fSize+8, new Color(150, 230, 246));
    
    private CRLabel tmpL= new CRLabel("");
    
    private MyButton refB;
    private final Color REF_B_COLOR= Color.YELLOW;
    private CRButton settingsB= new CRButton("Impostazioni", fSize+1, Color.GREEN);
    
    private CRPanel northP= new CRPanel(new GridLayout(1, 0));
    private CRPanel centerP= new CRPanel(new BorderLayout());
    
    private CRPanel p1= new CRPanel(new BorderLayout());
    private CRPanel p2= new CRPanel(new BorderLayout());
    private CRPanel gridP= new CRPanel(new GridLayout(0, 1));
    
    private CRLabel adviceL= new CRLabel("Si è verificato un errore durante l'apertura del file", fSize+3, Color.RED.darker());
    private String mouseInstructions;
    private String settingsInstructions= "Premere il bottone in alto a destra per modificare le impostazioni";
    
    private ArrayList<MyButton> myButtons;
    //private CRButton evtSource;
    
    private StartScreen startScreen;
    public File refDir;
    
    
    public StartScreen(File refDir) {
        super("Apertura torneo -> " + refDir.getName());
        this.refDir= refDir;
        this.init(refDir, false);
    }
    
    
    public StartScreen(File refDir, boolean b) {
        super("Apertura torneo -> " + refDir.getName());
        this.init(refDir, b);
    }
    
    
    public final void init(File refDir, boolean adviceFlag){
        
        setSize(45.0, 57.6);
        setDefaultCloseOperation(Screen.DISPOSE_ON_CLOSE);
        startScreen= this;
        
        try{
            refB= new MyButton(refDir.getParentFile(), null);
        }catch(Exception exc){
            refB= new MyButton(new File(""), null);
        }
        
        refB.setFontStyle(Font.ITALIC);
        refB.normalColor= REF_B_COLOR;
        refB.setNormalColor();
        
        //Creazione dei pannelli
        add(northP, BorderLayout.NORTH);
        add(centerP, BorderLayout.CENTER);
        centerP.add(p1, BorderLayout.CENTER);
        
        p1.add(Util.getScroll(gridP));
        p1.add(Util.getTreePath(refDir), BorderLayout.WEST);
        //p1.add(Util.getScroll(gridP, refDir.listFiles().length, 7), BorderLayout.CENTER);
        
        if(refDir.listFiles(f -> !f.isDirectory()).length ==0){
            mouseInstructions= "Per creare un torneo o una cartella utilizzare i bottoni qui sopra";
        }else{
            mouseInstructions= "Cliccare su un file per aprirlo oppure trasinarlo in una cartella";
        }
        
        CRPanel p1Tmp= new CRPanel(new GridLayout(0, 1));
        p1Tmp.add(new CRLabel(mouseInstructions, fSize+1));
        //p1Tmp.add(new CRLabel(Util.getPath(refDir), fSize+1, Color.BLUE));
        p1.add(p1Tmp, BorderLayout.NORTH);
        
        //Creazione newTourneyB
        northP.add(newTourneyB);
        newTourneyB.addActionListener((ActionEvent e) -> {
            dispose();
            MemoryManager.setFile(new File(refDir + File.separator + "TMP"));
            new InitScreen();
        });
        
        
        //Creazione newFolderB
        northP.add(newFolderB);
        newFolderB.addActionListener((ActionEvent e) -> {
            dispose();
            new NewFolderScreen(refDir);
        });
        
        
        
        //Creazione backB
        if(!refDir.getAbsolutePath().equals(MemoryManager.championshipsDir.getAbsolutePath())){
            
            CRPanel tmpP= new CRPanel(new BorderLayout());
            CRButton tmpB= new CRButton("X", fSize+3, REF_B_COLOR);
            tmpB.setForeground(REF_B_COLOR);
            
            tmpP.add(refB, BorderLayout.CENTER);
            tmpP.add(tmpB, BorderLayout.EAST);
            
            gridP.add(tmpP);
        }
        
        
        //Eventuale errore
        if(adviceFlag){
            centerP.add(adviceL, BorderLayout.NORTH);
        }
        
        
        //Creazione tidyDirectories
        int n= refDir.listFiles().length;
        ArrayList<File> list= new ArrayList<>(n);
        list.addAll(Arrays.asList(refDir.listFiles(f -> f.isDirectory())));
        list.addAll(Arrays.asList(refDir.listFiles(f -> !f.isDirectory())));
        File[] files= list.toArray(new File[n]);
        
        
        myButtons= new ArrayList<>(files.length);
        myButtons.add(refB);
        for(File f : files){
            Championship c= openChampionship(f);
            if(f!=null) gridP.add(new MyPanel(f, c));
        }
        
        
        //Ascoltatore di finestra
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                myClosing();
            }
        });
        
        settingsB.addActionListener(evt -> {
            dispose();
            new SettingsScreen();
        });
        
        JMenuBar menubar= new JMenuBar();
        menubar.setLayout(new BorderLayout());
        menubar.add(new Development(), BorderLayout.WEST);
        menubar.add(settingsB, BorderLayout.EAST);
        setJMenuBar(menubar);
        
        
        setVisible(true);
        
    }
    
    
    private void myClosing() {
        MemoryManager.writeFile();
        this.dispose();
        System.exit(0);
    }
    
    
    private Championship openChampionship(File f){
        try{
            ObjectInputStream ois= new ObjectInputStream(new FileInputStream(f));
            Championship c= (Championship)ois.readObject();
            ois.close();
            return c;
        }catch(Exception exc){
            return null;
        }
    }
    
    
    
    private void openTourney(File file){
        
        MemoryManager.setFile(file);
        dispose();
        boolean flag= true;
        
        try {
            MemoryManager.readFile();
        } catch(Exception exc) {
            System.out.println("Impossibile aprire il file");
            flag= false;
        }
        
        if(flag) {
            dispose();
            new TourneyScreen();
        } else {
            dispose();
            file.delete();
            new StartScreen(MemoryManager.championshipsDir, true);
        }
        
    }
    
    
    private class MyMouseAdapt extends MouseAdapter{
        
        private MyButton source;
        
        public MyMouseAdapt(MyButton source){
            this.source= source;
        }
        
        
        public void mouseReleased(MouseEvent evt){
            
            getRootPane().setCursor(Cursor.getDefaultCursor());
            
            Point click= evt.getLocationOnScreen();
            SwingUtilities.convertPointFromScreen(click, startScreen);
            
            Component released= findComponentAt(click);
            
            if(released instanceof MyButton){
                MyButton releasedB= (MyButton)released;
                MemoryManager.moveFile(source.file, releasedB.file, startScreen);
            }
            
            for(MyButton button : myButtons){
                button.setNormalColor();
            }
            
        }
        
        public void mouseDragged(MouseEvent evt){
            
            MyButton pressedLocal;
            
            try{
                pressedLocal= (MyButton)evt.getSource();
            }catch(Exception exc){
                return;
            }
            
            for(MyButton button : myButtons){
                Point p= evt.getLocationOnScreen();
                SwingUtilities.convertPointFromScreen(p, startScreen);
                button.darker(pressedLocal, startScreen.findComponentAt(p));
            }
        }
        
        
        public void mousePressed(MouseEvent evt){
            
            /*Toolkit t= Toolkit.getDefaultToolkit();
            java.awt.Image img= t.getImage("DeleteB.png");
            java.awt.Image img= source
            
            Cursor c= t.createCustomCursor(img, new Point(0, 0), "Cursore");
            getRootPane().setCursor(c);*/
            
            getRootPane().setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
        
        
    }
    
    private class MyButton extends CRButton{
        
        public File file;
        private Color normalColor;
        
        
        public MyButton(File file, Championship t){
            
            super(file.getName(), fSize+6);
            if(t!=null) this.setText(t.name);
            this.file= file;
            
            addMouseListener(new MyMouseAdapt(this));
            addMouseMotionListener(new MyMouseAdapt(this));
            addActionListener(evt -> click());
            
            //Se non è una directory
            if(!file.isDirectory()){
                setBackground(new Color(247, 229, 139));
                this.setToolTipText("Clicca per accedere al torneo, oppure trascinalo in un altra cartella");
            } else{
                this.setToolTipText("Clicca per entrare nella cartella, oppure trascinalo in un altra cartella");
                setBackground(new Color(235, 212, 100));
            }
            
            this.normalColor= getBackground();
        }
        
        public void darker(MyButton source, Object moved){
            
            setNormalColor();
            
            if(source==null){
                return;
            }
            
            if(moved instanceof MyButton){
                if(getText().equals(((MyButton)moved).getText()) && ((MyButton)moved).file.isDirectory()){
                    return;
                }
            }
            
            setBackground(normalColor.darker());
        }
        
        public void setNormalColor(){
            setBackground(normalColor);
        }
        
        private void click(){
            
            if(!file.isDirectory()){
                openTourney(file);
            } else {
                dispose();
                Main.startScreen= new StartScreen(file);
            }
        }
        
    }
    
    private class MyPanel extends CRPanel{
        
        private MyButton button;
        private CRButton exitB= new CRButton("X", fSize+3, CRUtilView.getLightRed());
        
        public MyPanel(File f, Championship t){
            
            super(new BorderLayout());
            
            button= new MyButton(f, t);
            myButtons.add(button);
            
            add(button, BorderLayout.CENTER);
            add(exitB, BorderLayout.EAST);
            
            exitB.addActionListener(evt -> {            //Se viene premuto elimina
                System.out.println("Tentativo di eliminazione");
                new WarningDelete(startScreen, f);
            });
            
        }
    }
    
}
