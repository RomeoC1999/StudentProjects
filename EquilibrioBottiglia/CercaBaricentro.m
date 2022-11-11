

%Date le dimensioni del contenitore, il volume del liquido e l'inclinazione
% restituisce l'angolo di quilibrio usando il metodo degli zeri
%Precisione di un grado in un tempo di 2 circa secondi

function [amed, precisione] = CercaBaricentro(b, l, hBott, V, animazione)

    amin=0;
    amax=50;
    
    for i=1:10
        amed=(amin+amax)/2;
        
        if animazione==true
            [xB, myPlot]= EquilibrioBottiglia(b, l, hBott, amed, V, true);
            pause(1);     %Tempo, in secondi, che rimane ogni grafico
            delete(myPlot);
        else
            [xB]= EquilibrioBottiglia(b, l, hBott, amed, V, false);
        end  
        
        if xB>0
            amin=amed;
        else
            amax=amed;
        end
    end
    
    precisione= 50/2^i;
    
    
end