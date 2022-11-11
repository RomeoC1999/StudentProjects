package memory;

import crtournament.Championship;
import crtournament.Main;
import crtournament.StartScreen;
import crtournament.Util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author Romeo Carrara
 * @since 27.11.2015
 */
public class MemoryManager {
    
    public static final File memoryDir= new File("Memoria");
    public static final File championshipsDir= new File("Tornei");
    public static final File dataDir= new File(memoryDir + File.separator + "Data");
    
    private static File f= null;    //File in esecuzione
    
    
    
    public static boolean exists() {
        return f.exists();
    }
    
    public static void delete() {
        f.delete();
    }
    
    public static void setFile(File file) {
        f= file;
    }
    
    public static File getFile(){
        return f;
    }
    
    public static File getDir(){
        return f.getParentFile();
    }
    
    
    
    public static void writeFile(){
        
        checkDirectories();
        SettingsManager.writeSettings();
        
        try{
            
            if(Main.champ.name.equals(f.getName())==false){
                f.renameTo(new File(f.getName()));
            }
            
            FileOutputStream fos= new FileOutputStream(f.getPath());
            ObjectOutputStream out= new ObjectOutputStream(fos);
            out.writeObject(Main.champ);
            out.close();
            fos.close();
        } catch(Exception exc) {
            //System.out.println("Rilevata eccezione nella lettura del file");
        }
        
    }
    
    
    public static void readFile() throws Exception{
        
        FileInputStream fis = null;
        ObjectInputStream in = null;
        
        try{
            fis= new FileInputStream(f.getPath());
            in= new ObjectInputStream(fis);
            Main.champ= (Championship)in.readObject();
            
        }catch(IOException | ClassNotFoundException exc){
            exc.printStackTrace();
            fis.close();
            in.close();
            throw new Exception();
        }
        
        fis.close();
        in.close();
    }
    
    
    public static void writeReadFile(){
        
        writeFile();
        
        try{
            readFile();
        }catch(Exception exc){

        }
    }
    
    
    public static void checkDirectories(){
        
        if(!championshipsDir.exists())
            championshipsDir.mkdir();
        
        if(!memoryDir.exists())
            championshipsDir.mkdir();
        
        if(!dataDir.exists())
            championshipsDir.mkdir();
    }
    
    
    public static boolean moveFile(File start, File dir, StartScreen startScreen){
        
        if(dir.isDirectory()){
            
            try{
                boolean flag= start.renameTo(new File(dir.getPath() + File.separator + start.getName()));
                startScreen.dispose();
                new StartScreen(dir);
                return flag;
            }catch(Exception exc){
                exc.printStackTrace();
            }
        }
        
        return false;
    }
}
