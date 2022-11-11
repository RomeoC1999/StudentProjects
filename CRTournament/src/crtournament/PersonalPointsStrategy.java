package crtournament;

/**
 *
 * @author Romeo Carrara
 * @since 2016.05.15
 */
public class PersonalPointsStrategy {
    
    
    public static String[] getNameArray(){
        
        String[] array= new String[2];
        
        for(int i=0; i<array.length; ++i){
            array[i]= getName(i);
        }
        
        return array;
    }
    
    
    public static PointsStrategy getStrategy(int index){
        
        switch(index){
            case 0:     return football;
            case 1:     return basket;
            default:    return null;
        }
        
    }
    
    public static String getName(int index){
        
        switch(index){
            case 0:     return "Metodo punteggi calcio";
            case 1:     return "Metodo punteggio basket";
            default:    return null;
        }
        
    }
    
    
    public static PointsStrategy football= new PointsStrategy(){
        
        public String resultAsString(Match match){
            
            int pa= match.pa;
            int pb= match.pb;
            
            String a, b;
            
            if(pa>pb){
                a= "3";
                b= "0";
            }else if(pa==pb){
                a= "1";
                b= "1";
            }else{
                a= "0";
                b= "3";
            }
            
            return a + PointsStrategy.SEPARATOR + b;
        }
    };
    
    public static PointsStrategy basket= new PointsStrategy(){
        
        public String resultAsString(Match match){
            
            int pa= match.pa;
            int pb= match.pb;
            
            String a, b;
            
            if(pa>pb){
                a= "3";
                b= "1";
            }else if(pa==pb){
                a= "2";
                b= "2";
            }else{
                a= "1";
                b= "3";
            }
            
            return a + PointsStrategy.SEPARATOR + b;
        }
    };
    
    
}
