

%Date le dimensioni del contenitore, il volume del liquido e l'inclinazione
%Calcola le cordinate del baricentro
%Disegna il grafico se richiesto

%Tempo di esecuzione:
% Con grafico: 4/5 funzioni al secondo
% Senza grafico: 3/4 funzioni al secondo

function[xB, myPlot]= EquilibrioBottiglia(b, l, hBott, a, V, toPlot)

    %Calcolo dell'altezza e delle dimensioni della zona triangolare
    a=a*pi/180;
    Hmax= l*sin(a);
    if a==0
        a= 1e-9;
    end
    Vmax= (b*Hmax^2)/(sin(2*a));
    if V<Vmax
        h= sqrt(V*sin(2*a)/b);
        limxdx= (h/(sin(a)*cos(a)))-h*tan(a);
        limxsx= -h*tan(a);
    else
        h= cos(a)*(V-Vmax)/(b*l)+Hmax;
        limxdx= (Hmax/(sin(a)*cos(a)))-h*tan(a);
        limxsx= -h*tan(a);
    end
    if h>hBott*sin(pi/2+a)
        error("L'acqua fuoriesce dal bicchiere");
    end
    
    %Cerco il baricentro della parte inferiore
    syms x y
    A= double(int(int(1,x,y/tan(pi/2+a),y/tan(a)),y,0,min(h,Hmax)));
    xB= double(int(int(x,x,y/tan(pi/2+a),y/tan(a)),y,0,min(h,Hmax)))/A;
    yB= double(int(int(y,x,y/tan(pi/2+a),y/tan(a)),y,0,min(h,Hmax)))/A;
    
    %Considero anche il baricentro della parte superiore, se necessario
    if h>Hmax
        yS= (h-Hmax)/2+Hmax;
        xS= (yS-l*sin(a)/2)/tan(pi/2+a)+l*cos(a)/2;
        AS= (h-Hmax)*l/cos(a);
        xB=(xB*A+xS*AS)/(A+AS);
        yB=(yB*A+yS*AS)/(A+AS);
    else
        yS=0;
        xS=0;
    end
    
    %Grafico
    if toPlot
        quality= 30;
        pointswater=5000;
        lx= (linspace(0,l,quality))*cos(a);
        ly= (linspace(0,l,quality))*sin(a);
        l1x= (linspace(0,hBott,quality))*cos(pi/2+a);
        l1y= (linspace(0,hBott,quality))*sin(pi/2+a);
        l2x= l1x+l*cos(a);
        l2y= l1y+l*sin(a);
        lwx= linspace(limxsx,limxdx,quality);
        lwy= h*ones(quality)';
        pointsx= rand(pointswater, 1)*(l*cos(a)-limxsx)+limxsx;
        pointsy= rand(pointswater, 1)*h;
        ind= find(pointsy>pointsx*tan(a) & pointsy>pointsx*tan(pi/2+a) & pointsy<(pointsx-l*cos(a))*tan(pi/2+a)+l*sin(a));
        myPlot= plot(pointsx(ind),pointsy(ind),'c.', xB,yB,'r*', lx,ly,'k-', l1x,l1y,'k-', l2x,l2y,'k-', lwx,lwy,'b-');
        axis equal
        axis([min(-hBott,-l), max(hBott,l), 0, hBott+l]);
        if(xB<0)
            res='Falling';
        else
            res='Stable';
        end
        title(sprintf('Angle of: %d° -> %s', floor(a*180/pi), res));
    end
    
end











