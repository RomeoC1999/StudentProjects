package memory;

import crtournament.Main;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 *
 * @author Romeo Carrara
 * @since 2016.03.06
 */
public class SettingsManager {
    
    public static File settingsFile= new File(MemoryManager.dataDir + File.separator + "settings.txt");
    
    
    public static MySettings readSettings(){
        
        try{
            
            MySettings tmp= new MySettings();
            BufferedReader br= new BufferedReader(new FileReader(settingsFile));
            String s;
            int n;
            
            //Lettura della versione
            s= br.readLine();
            tmp.version= s;
            
            //Dimensione del Font
            s= br.readLine();
            tmp.fSize= Integer.parseInt(s);
            
            //Mostra istruzione nello startScreen?
            s= br.readLine();
            n= Integer.parseInt(s);
            tmp.showStartScreenInstructions= n==1 ? true : false;
            
            //Mostra pannello bottoni in ToruneyScreen
            s= br.readLine();
            n= Integer.parseInt(s);
            tmp.showButtonsPanelInTourneyScreen= n==1 ? true : false;
            
            br.close();
            return tmp;
            
        }catch(Exception exc){
            return new MySettings();
        }
        
    }
    
    public static void writeSettings(){
        
        try{
            
            BufferedWriter bw= new BufferedWriter(new FileWriter(settingsFile));
            
            //Versione
            bw.write(Main.settings.version);
            bw.newLine();
            
            //Dimensione del font
            bw.write(Integer.toString(Main.settings.fSize));
            bw.newLine();
            
            //Istruzione nello StartScreen
            if(Main.settings.showStartScreenInstructions){
                bw.write("1");
            } else{
                bw.write("0");
            }
            bw.newLine();
            
            //Mostrare bannello bottoni?
            if(Main.settings.showButtonsPanelInTourneyScreen){
                bw.write("1");
            } else{
                bw.write("0");
            }
            bw.newLine();
            
            
            bw.close();
            
        }catch(Exception exc){
            
        }
        
        
        
    }
    
}
