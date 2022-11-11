package memory;

import java.io.File;

/**
 *
 * @author Romeo Carrara
 * @since 2016.04.04
 */
public class MemoryUtilities {
    
    
    public static String idealTourneyName(){
        for(int i=1; true; i++){
            if(!appear(new File("Torneo " + i))){
                return "Torneo "+i;
            }
        }
    }
    
    
    public static String idealDirectoryName(){
        for(int i=1; true; i++){
            if(!appear(new File("Cartella " + i))){
                return "Cartella "+i;
            }
        }
    }
    
    
    public static boolean appear(File file){
        return appear(file, MemoryManager.championshipsDir);
    }
    
    
    public static boolean appear(File file, File dir){
        
        for(File tmp : dir.listFiles()){
            
            if(tmp.getName().equals(file.getName()) && MemoryManager.getFile().getName().equals(file.getName())==false){
                return true;
            }
                
            if(tmp.isDirectory()){
                if(appear(file, tmp)){
                    return true;
                }
            }
        }
        
        return false;
    }
    
    
    public static int getNumberOfParents(File file){
        
        File tmp= file.getParentFile();
        int counter=0;
        
        while(tmp!=null && tmp.getName().equals(file.getName())){
            tmp= tmp.getParentFile();
            ++counter;
        }
        
        return counter;
    }
    
    
    public static File getFileByString(String name){
        return findFileIn(MemoryManager.championshipsDir, name);
    }
    
    
    public static File findFileIn(File dir, String name){
        
        if(dir.getName().equals(name)){
            return dir;
        }
        
        if(dir.isDirectory()){
            
            File tmp= null;
            
            for(File f : dir.listFiles()){
                tmp= findFileIn(f, name);
                if(tmp!=null){
                    return tmp;
                }
            }
            
            return null;
            
        }else{
            return null;
        }
    }
}
