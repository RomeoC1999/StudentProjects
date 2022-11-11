package crtournament;



import crstandard.gui.CRButton;
import crstandard.gui.CRPanel;
import crstandard.gui.CRUtilView;
import memory.MemoryManager;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import memory.MemoryUtilities;

/**
 *
 * @author Romeo Carrara
 * @since 09.12.2015
 */
public class Util {
    
    public static final int MAX_TEAMS=800;
    
    
    public static int round(double x){
        return (int)(x+0.5);
    }
    
    
    public static JScrollPane getScroll(JPanel p){
        JPanel panel= new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(p, BorderLayout.NORTH);
        panel.add(Box.createVerticalGlue(), BorderLayout.CENTER);
        return new JScrollPane(panel);
    }
    
    
    public static JComponent getTreePath(File dir){
        
        String path= dir.getPath();
        CRPanel gridP= new CRPanel(new GridLayout(0, 1));
        final int sizeB= 0;
        
        ArrayList<CRButton> buttons= new ArrayList<>();
        
        File currentDir= dir;
        
        do{
            CRButton button= new CRButton(currentDir.getName(), Screen.fSize+sizeB);
            
            button.addActionListener((ActionEvent evt) -> {
                Main.startScreen.dispose();
                Main.startScreen= new StartScreen(MemoryUtilities.getFileByString(button.getText()));
            });
            
            buttons.add(button);
            currentDir= currentDir.getParentFile();
            
        }while((currentDir!=null));
        //Ripeti finchè non si arriva a null
        
        Collections.reverse(buttons);
        
        for(CRButton b : buttons){
            gridP.add(b);
        }
        
        return getScroll(gridP);
    }
    
    
    
    public static void createArrayOfmatches(boolean mod, Match[][] v, int numberTeams){
        
        int numberDays=(numberTeams-(1-numberTeams%2));
	int matchesForDay=((numberTeams%2+numberTeams)/2);
        
        int[][][] tmp= new int[numberDays][matchesForDay][2];
        
        int i, j, k, num;
        
        if(mod== false)
	{
            for(i=0;i<matchesForDay;i++)
                for(j=0;j<2;j++)
                    tmp[0][i][j]=(i*2)+j+1;
	}
        else {
            
            Integer[] tmp2= new Integer[numberTeams + numberTeams%2];
            
            for(i=0; i<tmp2.length; i++)
                tmp2[i]= new Integer(i+1);
            
            List list= Arrays.asList(tmp2);
            
            Collections.shuffle(list);
            
            for(i=0; i<matchesForDay; i++)
                for(j=0; j<2; j++)
                    tmp[0][i][j]= (Integer)list.get(i*2 + j);
	}
        
        
        for(k=1;k<numberDays;k++){
            for(i=0;i<matchesForDay;i++) {
                for(j=0;j<2;j++) {
                    if(j==0)
                    {
                        if(i==0)
                            tmp[k][i][j]=tmp[k-1][i][j];
                        else
                        {
                            if(i==matchesForDay-1)
                                tmp[k][i][j]=tmp[k-1][i][j+1];
                            else
                                tmp[k][i][j]=tmp[k-1][i+1][j];
                        }
                    }
                    else
                    {
                        if(i==0)
                            tmp[k][i][j]=tmp[k-1][i+1][j-1];
                        else
                            tmp[k][i][j]=tmp[k-1][i-1][j];
                    }
                    
                }
            }
        }
        
        
        
        for(i=0; i<v.length; i++){
            for(j=0; j<matchesForDay; j++) {
                v[i][j]= new Match(Team.getTeam(tmp[i][j][0]), Team.getTeam(tmp[i][j][1]));
            }
        }
        
    }
    
    
    
    public static CRButton getButton(Component from, int x, int y){
        
        try{
            
            if(from instanceof Container){
                return getButton(((Container)from).findComponentAt(x, y), x, y);
            }
                
        }catch(Exception exc){
            exc.printStackTrace();
            return null;
        }
        
        return null;
    }
    
    
}
