package crtournament;
/*
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;*/

/**
 *
 * @author Romeo Carrara
 * @since 06.12.2016
 */
public class AddChampsManager {
    
    /*
    public static void addNewFiles(){
        
        if(!addDir.exists())
            addDir.mkdir();
        
        File[] adds= addDir.listFiles();
        
        for(File f: adds){
            add(f);
            f.delete();
        }
        
    }
    
    
    public static void add(File f){
        
        try{
            
            boolean flag= false;
            
            File n= new File(championshipsDir + File.separator + f.getName());
            
            if(n.exists()){
                flag= true;
                n.createNewFile();
            }
        
            ObjectInputStream ois= new ObjectInputStream(new FileInputStream(f));
            Championship champ= (Championship)ois.readObject();
            
            ObjectOutputStream oos= new ObjectOutputStream(new FileOutputStream(n));
            oos.writeObject((Championship)champ);
            
            if(flag==false)
                ChampionshipCR.filesList.addLast(champ.name);
            
            ois.close();
            oos.close();
            
        }catch(Exception exc){
            
            System.out.println(exc.toString());
        }
    }*/
    
}
