package memory;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Romeo Carrara
 * @since 05.12.2015
 */
public class VersionManager {
    
    public static final String VERSION=  "2.8.0";
    
    private static final File memoryDir= new File(MemoryManager.memoryDir.getPath());
    private static final File dataDir= new File(MemoryManager.dataDir.getPath());
    private static File versionF= new File(dataDir.getPath() + File.separator + "Version");
    public static final File championshipsDir= new File(memoryDir + File.separator + "Championships");
    
    
    public static void updateVersion() {
        
        createFiles();
        
        String s= scan();
        
        //Se la versione non è aggiornata
        if(!s.equals(VERSION)) {
            print(VERSION);
        }
    }
    
    
    
    public static void createFiles() {
        
        if(!memoryDir.exists())
            memoryDir.mkdir();
        
        if(!dataDir.exists())
            dataDir.mkdir();
        
        
        if(!versionF.exists()){
            try {
                versionF.createNewFile();
                print("1.0.00");
            } catch (IOException exc) {
                System.out.println("Rilevata eccezione: " + exc.toString());
            }
        }
    }
    
    
    public static String scan() {
        
        try {
            FileReader fr= new FileReader(versionF.getPath());
            BufferedReader br= new BufferedReader(fr);
            String s= br.readLine();
            fr.close();
            return s;
            
        }catch(IOException exc) {
            
        }
        
        return null;
    }
    
    
    public static void print(String s) {
        
        try  {
            FileWriter fw= new FileWriter(versionF.getPath());
            fw.write(s);
            fw.close();
        }catch(Exception exc) {
            
        }
    }
    
    
   
    
}
