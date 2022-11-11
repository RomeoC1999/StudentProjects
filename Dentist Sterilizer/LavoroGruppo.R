# Lavoro di gruppo di Statistica
# A.A. 2018/2019, Ingegneria Matematica
# Docente: Laura Sangalli
# Francesca Behrens, Paolo Botta, Romeo Carrara


# Caricamento delle librerie per i test z e i grafici tridimensionali

library(rgl)
library(BSDA)
library(EnvStats)

rm(list=ls())
graphics.off()

dati <- read.table('VA131.txt', header=T)
attach(dati)


# Variabili e vettori utili nel corso delle analisi

# Valori delle grandezze misurati in condizioni di freddo
pv1C= pv1[which(startCond=="C")]
pp1C= pp1[which(startCond=="C")]
pv2C= pv2[which(startCond=="C")]
pp2C= pp2[which(startCond=="C")]
pv3C= pv3[which(startCond=="C")]
pph1C= pph1[which(startCond=="C")]
pph2C= pph2[which(startCond=="C")]
h2oC= h2o[which(startCond=="C")]
pesoC= peso[which(startCond=="C")]

# Valori delle grandezze misurati in condizioni di caldo
pv1H= pv1[which(startCond=="H")]
pp1H= pp1[which(startCond=="H")]
pv2H= pv2[which(startCond=="H")]
pp2H= pp2[which(startCond=="H")]
pv3H= pv3[which(startCond=="H")]
pph1H= pph1[which(startCond=="H")]
pph2H= pph2[which(startCond=="H")]
h2oH= h2o[which(startCond=="H")]
pesoH= peso[which(startCond=="H")]

# Differenze in uno stesso esperimento
pv1Diff= pv1C-pv1H
pp1Diff= pp1C-pp1H
pv2Diff= pv2C-pv2H
pp2Diff= pp2C-pp2H
pv3Diff= pv3C-pv3H
pph1Diff= pph1C-pph1H
pph2Diff= pph2C-pph2H
h2oDiff= h2oC-h2oH


# STATISTICA DECSRITTIVA

# pv1
x11()
par( mfrow= c( 2, 2 ) )
mat <- matrix(c(1,1,2,3), 2)
layout(mat, c(10,12), c(10,10))
boxplot( pv1C,pv1H, main = "pv1", at = c(1,2), names = c("Cold", "Hot"), ylab = 'sec')
hist( pv1C, prob = T, main = 'pv1 cold start', xlab = 'sec',xlim = c( (min(pv1)%/%10)*10 , (max(pv1)%/%10 + 1)*10 ), breaks = 10)
hist( pv1H, prob = T, main = 'pv1 hot start', xlab = 'sec', xlim = c( (min(pv1)%/%10)*10 , (max(pv1)%/%10 + 1)*10 ), breaks = 10)

# pp1
x11()
par( mfrow= c( 2, 2 ) )
mat <- matrix(c(1,1,2,3), 2)
layout(mat, c(10,12), c(10,10))
boxplot( pp1C,pp1H, main = "pp1", at = c(1,2), names = c("Cold", "Hot"), ylab = 'sec')
hist( pp1C, prob = T, main = 'pp1 cold start', xlab = 'sec',xlim = c( (min(pp1)%/%100)*100 , (max(pp1)%/%100 + 1)*100 ), breaks = 10)
hist( pp1H, prob = T, main = 'pp1 hot start', xlab = 'sec', xlim = c( (min(pp1)%/%100)*100 , (max(pp1)%/%100 + 1)*100 ), breaks = 10)

# pv2
x11()
par( mfrow= c( 2, 2 ) )
mat <- matrix(c(1,1,2,3), 2)
layout(mat, c(10,12), c(10,10))
boxplot( pv2C,pv2H, main = "pv2", at = c(1,2), names = c("Cold", "Hot"), ylab = 'sec')
hist( pv2C, prob = T, main = 'pv2 cold start', xlab = 'sec',xlim = c( (min(pp1)%/%100)*100 , (max(pp1)%/%100 + 0.5)*100 ), breaks = 10)
hist( pv2H, prob = T, main = 'pv2 hot start', xlab = 'sec', xlim = c( (min(pp1)%/%100)*100 , (max(pp1)%/%100 + 0.5)*100 ), breaks = 10)

# pp2
x11()
par( mfrow= c( 2, 2 ) )
mat <- matrix(c(1,1,2,3), 2)
layout(mat, c(10,12), c(10,10))
boxplot( pp2C,pp2H, main = "pp2", at = c(1,2), names = c("Cold", "Hot"), ylab = 'sec')
hist( pp2C, prob = T, main = 'pp2 cold start', xlab = 'sec',xlim = c( (min(pp2)%/%100 + 0.5)*100 , (max(pp2)%/%100 + 1)*100 ), breaks = 10)
hist( pp2H, prob = T, main = 'pp2 hot start', xlab = 'sec', xlim = c( (min(pp2)%/%100 + 0.5)*100 , (max(pp2)%/%100 + 1)*100 ), breaks = 10)

# pv3
x11()
par( mfrow= c( 2, 2 ) )
mat <- matrix(c(1,1,2,3), 2)
layout(mat, c(10,12), c(10,10))
boxplot( pv3C,pv3H, main = "pv3", at = c(1,2), names = c("Cold", "Hot"), ylab = 'sec')
hist( pv3C, prob = T, main = 'pv3 cold start', xlab = 'sec',xlim = c( (min(pv3)%/%100)*100 , (max(pv3)%/%100 + 0.5)*100 ), breaks = 10)
hist( pv3H, prob = T, main = 'pv3 hot start', xlab = 'sec', xlim = c( (min(pv3)%/%100)*100 , (max(pv3)%/%100 + 0.5)*100 ), breaks = 10)

# pph1
x11()
par( mfrow= c( 2, 2 ) )
mat <- matrix(c(1,1,2,3), 2)
layout(mat, c(10,12), c(10,10))
boxplot( pph1C,pph1H, main = "pph1", at = c(1,2), names = c("Cold", "Hot"), ylab = 'sec')
hist( pph1C, prob = T, main = 'pph1 cold start', xlab = 'sec',xlim = c( (min(pph1)%/%100)*100 , (max(pph1)%/%100 + 1)*100 ), breaks = 10)
hist( pph1H, prob = T, main = 'pph1 hot start', xlab = 'sec', xlim = c( (min(pph1)%/%100)*100 , (max(pph1)%/%100 + 1)*100 ), breaks = 10)

# pph2
x11()
par( mfrow= c( 2, 2 ) )
mat <- matrix(c(1,1,2,3), 2)
layout(mat, c(10,12), c(10,10))
boxplot( pph2C,pph2H, main = "pph2", at = c(1,2), names = c("Cold", "Hot"), ylab = 'sec')
hist( pph2C, prob = T, main = 'pph2 cold start', xlab = 'sec',xlim = c( (min(pph2)%/%10)*10 , (max(pph2)%/%10 + 0.2)*10 ), breaks = 10)
hist( pph2H, prob = T, main = 'pph2 hot start', xlab = 'sec', xlim = c( (min(pph2)%/%10)*10 , (max(pph2)%/%10 + 0.2)*10 ), breaks = 10)

# h2o
x11()
par( mfrow= c( 2, 2 ) )
mat <- matrix(c(1,1,2,3), 2)
layout(mat, c(10,12), c(10,10))
boxplot( h2oC,h2oH, main = "h2o", at = c(1,2), names = c("Cold", "Hot"), ylab = 'cl')
hist( h2oC, prob = T, main = 'h2o cold start', xlab = 'cl',xlim = c( (min(h2o)%/%100)*100 , (max(h2o)%/%100 + 1)*100 ), breaks = 6)
hist( h2oH, prob = T, main = 'h2o hot start', xlab = 'cl', xlim = c( (min(h2o)%/%100)*100 , (max(h2o)%/%100 + 1)*100 ), breaks = 6)


# VERIFICA D'IPOTESI

# Test d'ipotesi per verificare se la condizione iniziale (caldo o freddo)
# incide in maniera significativa sui valori delle varie grandezze

# Non si conosce la varianza e non e' detto che i dati siano normali

n= dim(dati)[1]/2
n

# Essendo pero' molto numerosi (circa 100 se considerati accoppiati) eseguiamo comunque
# una verifica di ipotesi con la gaussiana

z.test(pv1Diff, sigma.x=sd(pv1Diff)/sqrt(n))
z.test(pp1Diff, sigma.x=sd(pp1Diff)/sqrt(n))
z.test(pv2Diff, sigma.x=sd(pv2Diff)/sqrt(n))
z.test(pp2Diff, sigma.x=sd(pp2Diff)/sqrt(n))
z.test(pv3Diff, sigma.x=sd(pv3Diff)/sqrt(n))
z.test(pph1Diff, sigma.x=sd(pph1Diff)/sqrt(n))
z.test(pph2Diff, sigma.x=sd(pph2Diff)/sqrt(n))
z.test(h2oDiff, sigma.x=sd(h2oDiff)/sqrt(n))

# Qualsiasi grandezza non e' ininfluente alle condizioni iniziali
# Occorre un modello lineare con predittore categorico


# RICERCA DI UN MODELLO LINEARE

#Visualizzazione grafica
x11()
par(mfrow=c(3,3))
# pv1
plot(pv1H, pesoH, main="pv1", col="red",ylab = "",xlab = "")
points(pv1C,pesoC, col="blue")
# pp1
plot(pp1H, pesoH, main="pp1", col="red",ylab = "",xlab = "")
points(pp1C,pesoC, col="blue")
# pv2
plot(pv2H, pesoH, main="pv2", col="red",ylab = "",xlab = "")
points(pv2C,pesoC, col="blue")
# pp2
plot(pp2H, pesoH, main="pp2", col="red",ylab = "",xlab = "")
points(pp2C,pesoC, col="blue")
# pv3
plot(pv3H, pesoH, main="pv3", col="red",ylab = "",xlab = "")
points(pv3C,pesoC, col="blue")
# pph1
plot(pph1H, pesoH, main="pph1", col="red",ylab = "",xlab = "")
points(pph1C,pesoC, col="blue")
# pph2
plot(pph2H, pesoH, main="pph2", col="red",ylab = "",xlab = "")
points(pph2C,pesoC, col="blue")
# h2o
plot(h2oH, pesoH, main="h2o", col="red",ylab = "",xlab = "")
points(h2oC,pesoC, col="blue")
# legenda
plot(0,type='n',axes=FALSE,ann=FALSE)
legend("center", c("Hot start","Cold start"), fill=c("red","blue"), cex=1.8)

# Notiamo a vista che pv1 e pph2 possono essere scartati come predittori

# Viene creato un preditorre binario categorico (0 per Cold, 1 per hot)
sc= rep(0, dim(dati)[1])
sc[which(startCond=="H")]=1

# Si prova a creare un primo modello lineare che tenga conto di tutte le osservazioni

result1= lm(peso ~ sc+pp1+I(pp1*sc)+pv2+I(pv2*sc)+pp2+I(pp2*sc)+pv3+I(pv3*sc)+pph1+I(pph1*sc)+h2o+I(h2o*sc))
summary(result1)
# Dai p-value (e osservando le pendenze dei grafici) emerge che i termini di
# interazione tra pv2, pp2, pv3 e h2o con il predittore categorico sono eliminabili.
# Si puo' anche provare a rimuovere pph1

result2= lm(peso ~ sc+pp1+I(pp1*sc)+pv2+pp2+pv3+h2o)
summary(result2)
# Il modello e' ancora molto valido (indici R molto elevati)

# Dai grafici si nota che nel caso Cold, per la grandezza h2o, sembra esserci una
# correlazione logaritmica

result3= lm(peso ~ sc+pp1+I(pp1*sc)+pv2+pp2+pv3+I(h2o*sc)+I(log(h2o)*(1-sc)))
summary(result3)
# Non si notano miglioramenti significativi, si usa quindi il secondo modello

# Si studiano i residui

res2= result2$residuals
shapiro.test(res2)
qqnorm(res2)
# I residui sono normali

peso_capp= result2$fitted
plot(peso_capp, res2)
abline(h=0, lwd=2, col="blue")
# I residui sono anche omoschedastici


#INTERVALLO DI PREDIZIONE

# Si cerca di costruire un intervallo di predizione al 99% per un'osservazione futura.
# Trattandosi di regressione multipla è impossibile ottenere una formula analitica
# Possiamo solo fare delle stime numeriche attraverso dei pacchetti software

# Inseriamo dei valori (presi dal dataset) a titolo di esempio
test.pv1= 120;
test.pp1= 214;
test.pv2= 196;
test.pp2= 155;
test.pv3= 195;
test.pph1= 433;
test.pph2= 46;
test.h2o= 551;
test.sc= 0;
test.peso= 5500; #Da usare per le verifiche, sarà il nostro modello a stimarlo

# Costruzione intervalli di previsione con i dati di esempio
mydataframe = data.frame(sc=test.sc, pp1=test.pp1, "I(pp1*sc)"=I(test.pp1*test.sc), pv2=test.pv2, pp2=test.pp2, pv3=test.pv3, h2o=test.h2o)
test.result=predict(result2, mydataframe, interval='prediction', level=0.99)

# Stampa risultato con i dati di esempio
print(c("Peso stimato:",round(test.result[,1],digits=2),"peso massimo al 99%: ",round(test.result[,3],digits=2)),quote=FALSE)


# GRAFICO DEL MODELLO LINEARE

# Si prova a eliminare dal modello i tre predittori meno significativi
result4= lm(peso ~ sc+pp1+I(pp1*sc)+pv3)
summary(result4)
res4= result4$residuals
shapiro.test(res4)
peso_capp4= result4$fitted
plot(peso_capp4, res4)
abline(h=0, lwd=2, col="red")

# Si ottiene un modello meno preciso del precedente, in particolare
# la varianza dei residui e' maggiore e la loro normalita' minore
# Gli indici R e Radj sono ottimi
# Utilizziamo il modello solo per visualizzare graficamente il lavoro effettuato

open3d()
plot3d(x=pp1H, y=pv3H, z=pesoH/10 ,col='red',xlab="pp1",ylab="pv3",zlab="peso")
points3d(x=pp1C, y=pv3C, z=pesoC/10 ,col='blue')
box3d()
axes3d()

