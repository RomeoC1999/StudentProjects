package memory;

import crtournament.Main;
import crtournament.Team;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 *
 * @author Romeo Carrara
 * @since 2016.02.25
 */
public class ClassificationManager {
    
    
    public static void printFile(Team[] t) {
        
        try {
            
            File mainDir= new File("Classifiche");
            File dir= new File(mainDir + File.separator + Main.champ.name);
            
            if(!mainDir.exists())
                mainDir.mkdir();
            
            if(!dir.exists())
                dir.mkdir();
            
            File fVoid= new File(dir + File.separator + Main.champ.name);
            File fExcel= new File(dir + File.separator + Main.champ.name + ".xls");
            File fText= new File(dir + File.separator + Main.champ.name + ".txt");
            File fWord= new File(dir + File.separator + Main.champ.name + ".doc");
            
            fVoid.createNewFile();
            fExcel.createNewFile();
            fText.createNewFile();
            fWord.createNewFile();
            
            write(fVoid, t);
            write(fExcel, t);
            write(fText, t);
            write(fWord, t);
            
        }catch(Exception exc) {
            
        }
    }
    
    
    private static void write(File f, Team[] t) {
        
        
        try {
            
            FileWriter fw= new FileWriter(f);
            BufferedWriter bw= new BufferedWriter(fw);

            int num= getMaxName(t);
            
            bw.write(Main.champ.name);
            bw.newLine();
            bw.newLine();
            
            bw.write("Pos\t");
            bw.write(getTeamName("Nome", num) + "\t");
            
            bw.write("Punti\t");
            bw.write("Vinte\t");
            bw.write("Pareggi\t");
            bw.write("Perse\t");

            bw.write("Fatti\t");
            bw.write("Subiti\t");
            bw.write("Diff.\t");
            
            bw.newLine();
                
            
            for(int i=0; i< t.length; i++) {
                
                bw.write((i+1) + "^\t");
                bw.write(getTeamName(t[i].name, num) + "\t");
                
                bw.write(t[i].points + "\t");
                bw.write(t[i].wins + "\t");
                bw.write(t[i].balances + "\t");
                bw.write(t[i].defeats + "\t");
                
                bw.write(t[i].goalsA + "\t");
                bw.write(t[i].goalsP + "\t");
                bw.write(t[i].differentGoals() + "\t");
                
                bw.newLine();
            }
            
            bw.close();
            fw.close();
            
        }catch(Exception exc) {
            
        }
    }
    
    
    private static String getTeamName(String s, int num) {
        
        int n= s.length();
        String tmp= s;
        
        for(int i=n; i<num+2; i++)
            tmp += " ";
        
        return tmp;
        
    }
    
    
    private static int getMaxName(Team[] t) {
        
        String tmp= t[0].name;
        
        for(int i=1; i<t.length; i++)
            if(t[i].name.length() > tmp.length())
                tmp= t[i].name;
        
        return tmp.length();
    }
    
}
