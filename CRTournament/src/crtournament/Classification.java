package crtournament;

import crstandard.gui.CRButton;
import crstandard.gui.CRFrame;
import crstandard.gui.CRLabel;
import crstandard.gui.CRPanel;
import crstandard.gui.CRTable;
import crstandard.gui.CRUtilView;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.Comparator;
import javax.swing.JComboBox;
import javax.swing.JMenuBar;
import javax.swing.JTable;
import memory.ClassificationManager;

/**
 *
 * @author Romeo Carrara
 * @since 28.11.2015
 */
public final class Classification extends Screen{
    
    
    public static final int[] sorts= {0, 1, 2, 3, 4, 5, 6, 7};
    public JComboBox<String> sortMenu;
    public JComboBox<String> pointsMenu;
    
    public CRButton exitB= new CRButton("Torna indietro", fSize+5, CRUtilView.getLightRed());
    public CRButton printFileB= new CRButton("Stampa classifica su file", fSize+5, Color.CYAN);
    
    public Team[] cl;
    
    
    
    public Classification(int sort, boolean invert) {
        super("Classifica");
        init(sort, invert);
    }
    
    
    private void init(int sort, boolean invert){
        
        setDefaultCloseOperation(CRFrame.DISPOSE_ON_CLOSE);
        setSize(72.0, 65.0);
        
        CRPanel p2= new CRPanel(new GridLayout(0, 1));
        add(p2, BorderLayout.SOUTH);
        p2.add(printFileB);
        p2.add(exitB);
        
        usePanels(sort, invert);
        //useTable(sort, invert);
        
        String[] classifyString= new String[]{"Punti", "Giocate", "Vittorie", "Pareggi", "Sconfitte", "Punti fatti", "Punti subiti", "Differenza punti"};
        String[] pointsString= PersonalPointsStrategy.getNameArray();
        
        sortMenu= new JComboBox<>(classifyString);
        sortMenu.setEditable(false);
        sortMenu.setSelectedIndex(sort);
        
        pointsMenu= new JComboBox<>(pointsString);
        pointsMenu.setEditable(false);
        pointsMenu.setSelectedIndex(0);
        
        JMenuBar menubar= new JMenuBar();
        menubar.setLayout(new BorderLayout());
        menubar.add(sortMenu, BorderLayout.WEST);
        menubar.add(pointsMenu, BorderLayout.EAST);
        menubar.add(new Development(), BorderLayout.CENTER);
        this.setJMenuBar(menubar);
        
        
        sortMenu.addActionListener(evt ->{
            int act= sortMenu.getSelectedIndex();
            if(act != (-1)) {
                dispose();
                new Classification(act, false);
            }
        });
        
        pointsMenu.addActionListener(evt ->{
            int act= pointsMenu.getSelectedIndex();
            if(act != (-1)) {
                Main.champ.pointsStrategy= PersonalPointsStrategy.getStrategy(act);
                dispose();
                new Classification(sortMenu.getSelectedIndex(), false);
            }
        });
        
        
        exitB.addActionListener(evt ->{
            dispose();
            new TourneyScreen();
        });
        
        
        printFileB.addActionListener(evt-> {
            ClassificationManager.printFile(cl);
            new PrintClassifyScreen();
        });
        
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                dispose();
                new TourneyScreen();
            }
        });
        
        setVisible(true);
    }
    
    
    
    
    public Team[] getTidyTeams(Match[][] m, int sort, boolean invert) {
        
        int i, j;
        
        int nTeams= Main.champ.numberTeams;
        
        for(i=0; i<nTeams; i++) {
            Main.champ.teams[i].reset();
        }
        
        
        PointsStrategy pointsStrategy= Main.champ.pointsStrategy;
        
        for(i=0; i< m.length; i++)
            for(j=0; j<m[0].length; j++)
                m[i][j].updateTeams(Main.champ.teams, pointsStrategy);
        
        Team[] v= new Team[nTeams];
        
        for(i=0; i<v.length; i++){
            v[i]= Main.champ.teams[i];
        }
        
        Arrays.sort(v, getComparator(sort));
        
        return v;
    }
    
    private Comparator<Team> getComparator(int sort){
        
        switch(sort){
            
            case 0: return new Comparator<Team>(){
                public int compare(Team a, Team b){
                    if(a.points!=b.points){
                        return b.points-a.points;
                    }else{
                        if(a.differentGoals()!=b.differentGoals()){
                            return b.differentGoals()-a.differentGoals();
                        }else{
                            return b.goalsA-a.goalsA;
                        }
                    }
                    
                }
            };
                
            case 1: return ((a, b) -> b.matches-a.matches);
            case 2: return ((a, b) -> b.wins-a.wins);
            case 3: return ((a, b) -> b.balances-a.balances);
            case 4: return ((a, b) -> b.defeats-a.defeats);
            case 5: return ((a, b) -> b.goalsA-a.goalsA);
            case 6: return ((a, b) -> b.goalsP-a.goalsP);
            case 7: return ((a, b) -> b.differentGoals()-a.differentGoals());
            
            default:
                System.out.println("Invocata ricorsione metodo getComparator, class Classification");
                return getComparator(0);
        }
    }
    
    
    
    private class MyTable extends CRTable{
        
        private int sort;
        
        public MyTable(int sort, boolean invert){
            cl= getTidyTeams(Main.champ.v, sort, invert);
            this.sort= sort;
        }
        
        
        public int getRowCount() {
            return cl.length;
        }
        
        @Override
        public int getColumnCount() {
            return 10;
        }
        
        @Override
        public Object getValueAt(int row, int col) {
            
            switch(col){
                case 0:     return row+1;
                case 1:     return cl[row].name;
                case 2:     return cl[row].points;
                case 3:     return cl[row].matches;
                case 4:     return cl[row].wins;
                case 5:     return cl[row].balances;
                case 6:     return cl[row].defeats;
                case 7:     return cl[row].goalsA;
                case 8:     return cl[row].goalsP;
                case 9:     return cl[row].differentGoals();
                default:    return null;
            }
            
        }
        
        @Override
        public String getColumnName(int col) {
            
            System.out.println("Aggiunta del nome della colonna");
            
            switch(col){
                case 0:     return "Pos";
                case 1:     return "Squadra";
                case 2:     return "Punti";
                case 3:     return "G";
                case 4:     return "V";
                case 5:     return "X";
                case 6:     return "S";
                case 7:     return "Fatti";
                case 8:     return "Subiti";
                case 9:     return "Diff";
                default:    return null;
            }
        }
        
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            
            CRLabel label= new CRLabel("", fSize+5);
            label.setText(value.toString());
            
            if(column-2 == sort){
                label.setOpaque(true);
                label.setBackground(new Color(102, 255, 102));  //Verde limone chiaro
            }
            
            int n= 20;
            label.setBorder(javax.swing.BorderFactory.createEmptyBorder(n, 0, n, 0));
            
            
            return label;
        }
        
        
    }
    
    
    
    private void useTable(int sort, boolean invert){
        MyTable table= new MyTable(sort, invert);
        JTable jTable= new JTable(table);
        jTable.setDefaultRenderer(Object.class, table);
        jTable.setBackground(Color.GRAY);
        this.add(jTable, BorderLayout.CENTER);
    }
    
    private void usePanels(int sort, boolean invert){
        
        cl= getTidyTeams(Main.champ.v, sort, invert);
        
        CRPanel mainPanel= new CRPanel(new BorderLayout());
        
        CRPanel positionsP= new CRPanel(new GridLayout(0, 1));
        CRPanel namesP= new CRPanel(new GridLayout(0, 1));
        CRPanel matchesP= new CRPanel(new GridLayout(0, 1));
        
        mainPanel.add(positionsP, BorderLayout.WEST);
        mainPanel.add(namesP, BorderLayout.CENTER);
        mainPanel.add(matchesP, BorderLayout.EAST);
        
        this.add(Util.getScroll(mainPanel), BorderLayout.CENTER);
        
        CRPanel p2= new CRPanel(new GridLayout(0, 1));
        this.add(p2, BorderLayout.SOUTH);
        p2.add(printFileB);
        p2.add(exitB);
        
        positionsP.add(new MyTitleLabel(" Pos "));
        namesP.add(new MyTitleLabel(" Squadra "));
        
        matchesP.setLayout(new GridLayout(0, 8));
        
        matchesP.add(new MyTitleLabel(" Punti "));
        matchesP.add(new MyTitleLabel(" G "));
        matchesP.add(new MyTitleLabel(" V "));
        matchesP.add(new MyTitleLabel(" X "));
        matchesP.add(new MyTitleLabel(" P "));
        
        matchesP.add(new MyTitleLabel(" Fatti  "));
        matchesP.add(new MyTitleLabel(" Subiti "));
        matchesP.add(new MyTitleLabel(" Diff.  "));
        
        
        for(int i=0; i<cl.length; i++) {
            
            positionsP.add(new MyLabel(Integer.toString(i+1)));
            
            namesP.add(new MyLabel(cl[i].name));
            
            MyLabel label;
            
            switch(sort){
                
                case 0: label= new MyLabel(Integer.toString(cl[i].points)); break;
                case 1: label= new MyLabel(Integer.toString(cl[i].matches)); break;
                case 2: label= new MyLabel(Integer.toString(cl[i].wins)); break;
                case 3: label= new MyLabel(Integer.toString(cl[i].balances)); break;
                case 4: label= new MyLabel(Integer.toString(cl[i].defeats)); break;
                case 5: label= new MyLabel(Integer.toString(cl[i].goalsA)); break;
                case 6: label= new MyLabel(Integer.toString(cl[i].goalsP)); break;
                case 7: label= new MyLabel(Integer.toString(cl[i].differentGoals())); break;
                default: label= new MyLabel(""); break;
            }
            
            
            label.setOpaque(true);
            label.setBackground(new Color(102, 255, 102));  //Verde limone chiaro
            
            if(sort!=0) matchesP.add(new MyLabel(Integer.toString(cl[i].points))); else matchesP.add(label);
            if(sort!=1) matchesP.add(new MyLabel(Integer.toString(cl[i].matches))); else matchesP.add(label);
            if(sort!=2) matchesP.add(new MyLabel(Integer.toString(cl[i].wins))); else matchesP.add(label);
            if(sort!=3) matchesP.add(new MyLabel(Integer.toString(cl[i].balances))); else matchesP.add(label);
            if(sort!=4) matchesP.add(new MyLabel(Integer.toString(cl[i].defeats))); else matchesP.add(label);
            if(sort!=5) matchesP.add(new MyLabel(Integer.toString(cl[i].goalsA))); else matchesP.add(label);
            if(sort!=6) matchesP.add(new MyLabel(Integer.toString(cl[i].goalsP))); else matchesP.add(label);
            if(sort!=7) matchesP.add(new MyLabel(Integer.toString(cl[i].differentGoals()))); else matchesP.add(label);
            
        } 
        
    }
    
    
    private class MyPanel extends CRPanel {
        public MyPanel() {
            super(new GridLayout(0, 1));
        }
    }

    private class MyLabel extends CRLabel {
        public MyLabel(String s) {
            super(s, fSize+4);
            createBorder();
        }
    }

    private class MyTitleLabel extends MyLabel {
        public MyTitleLabel(String s) {
            super(s);
            setForeground(Color.BLUE);
            buildUpFont(fSize+3);
        }
    }
}
