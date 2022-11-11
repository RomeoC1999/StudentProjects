

%% Mostra la ricerca del baricentro

%Dimensioni del contenitore e volume del liquido
hBott=2;
l=1;
b=1;
V=0.6;

[amed] = CercaBaricentro(b, l, hBott, V, true);
fprintf("Baricentro all'angolazione %g\n", amed);
