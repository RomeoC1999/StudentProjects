

%% Mostra il crollo della bottiglia

%Dimensioni del contenitore e volume del liquido
hBott=0.20;
l=0.10;
b=0.10;
V=0.0010;

%Mostra 30 grafici con angoli che vanno dagli 0 agli 85 gradi
degrees= linspace(0,85,30);

for a=degrees
    [xB, myPlot]= EquilibrioBottiglia(b, l, hBott, a, V, true);
    pause(0.20);     %Tempo, in secondi, che rimane ogni grafico
    delete(myPlot);
end