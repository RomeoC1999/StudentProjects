package crtournament;

import memory.MemoryManager;
import memory.MySettings;
import memory.SettingsManager;
import memory.VersionManager;


/**
 *
 * @author Romeo Carrara
 * @since 26.11.2015
 */
public class Main {
    
    public static Championship champ= new Championship();
    public static MySettings settings;
    public static StartScreen startScreen;
    
    
    public static void main(String[] args){
        
        settings= SettingsManager.readSettings();
        
        VersionManager.updateVersion();
        MemoryManager.checkDirectories();
        
        //startScreen= new StartScreen2();
        startScreen= new StartScreen(MemoryManager.championshipsDir);
    }
    
    
    
    //Possibiltà di inviare tornei via mail
    //Pulizia programma
    //Aggiungere testo cursore allo startScreen
    
    //Possibilità di inviare tornei
    //Mettere una guida
    //Creare foglio Excel
    //Eliminare i tornei realmente nel MemoryManager
    //Chiedere conferma per l'eliminazione del torneo
    //Riguardare il vector
    //Barra dei bottoni dinamica
    //Possibilità di stampare il torneo
    
}
