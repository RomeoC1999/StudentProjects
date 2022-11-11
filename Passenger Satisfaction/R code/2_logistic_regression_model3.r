library(lattice) #dotplot
library(ROCit)  #ROC curve
library(multcomp)


flight <- read.csv("Data/new_train.csv")[-1]

#merge Eco Plus and Eco Class
flight[flight$Class=='Eco Plus',]$Class <- 'Eco'

#set categorical variables
flight$satisfaction <- as.factor(flight$satisfaction)
flight$Gender <- as.factor(flight$Gender)
flight$Customer.Type <- as.factor(flight$Customer.Type)
flight$Type.of.Travel <- as.factor(flight$Type.of.Travel)
flight$Class <- as.factor(flight$Class)

###
###
#####Third model: as model 1 + interaction between class and "satisfaction" regressors (the ones the companies can improve)
###

satis.regr <- labels(flight)[2][[1]][7:20]
satis.regr

formula <- 'satisfaction ~ . - Gender - Flight.Distance'
for (i in satis.regr) {
  formula <- paste(formula, '+ Class:', i)
}

fit3 <- glm(formula , data=flight, family='binomial')
summary(fit3)

#
#
##Confidence intervals
#
alpha <- 0.05
p <- 14*2 

#BUSINESS CLASS
param=6:19
conf.int <- confint(fit3,parm=param, level= 1-alpha/p)
coeff <- fit3$coefficients[param]
coeff.order <- order(coeff)
coeff <- coeff[coeff.order]
test <- data.frame(Name=labels(conf.int)[[1]],inf=conf.int[1:14],sup=conf.int[15:28])
test <- test[coeff.order,]
test <- data.frame(test,cen=coeff)
test2 <- cbind(inf = test$inf,cen = test$cen, sup = test$sup)
rownames(test2) <- test$Name
dotplot(test2,col='blue',xlab='',pch=c('|','•','|'),cex=c(2,3,2),main='Class Business')


#ECO CLASS 
names <- integer(14)
lowers <- integer(14)
estimates <- integer(14)
uppers <- integer(14)
start <- 6
for (i in 0:13) {
  coeff.vect <- integer(length(fit3$coefficients)) 
  coeff.vect[start + i] <- 1
  coeff.vect[start+16 + i] <- 1
  lh <- glht(fit3, linfct = t(coeff.vect))
  res <- confint(lh,level = 1-alpha/p)
  names[i+1] <- labels(fit3$coefficients)[start + i]
  lowers[i+1] <- res$confint[2]
  estimates[i+1] <- res$confint[1]
  uppers[i+1] <- res$confint[3]
}
#print intervals
test <- data.frame(Name=names,inf=lowers,cen=estimates,sup=uppers)
test <- test[order(test$cen),]
test2 <- cbind(inf = test$inf,cen = test$cen, sup = test$sup)
rownames(test2) <- test$Name
dotplot(test2,col='blue',xlab='',pch=c('|','•','|'),cex=c(2,3,2),main="Class ECO")


#
#
###GOF
#

fit3.aic <- fit3$aic
#Likelihood ratio R^2_L
fit3.R2L <- (fit3$null.deviance - fit3$deviance )/fit3$null.deviance

#ROC curve
ROCit_obj <- rocit(score=fit3$fitted.values,class = flight$satisfaction)
#Plot + get optimal p0 according to Youden index
a <- plot(ROCit_obj,values=T)
p0 <- a$`optimal Youden Index point`[4]
#AUC
fit3.auc <- ROCit_obj$AUC

misc <- table( real = as.numeric(flight$satisfaction)-1, predicted = as.numeric( fit3$fitted.values >= p0 ) )
misc

err <- (misc[2]+misc[3])/sum(misc)
err  #0.08684068 a bit better than model 1


#test the classifier on the test set
flight.test <- read.csv("Data/new_test.csv")[-1]

#merge Eco Plus and Eco Class
flight.test[flight.test$Class=='Eco Plus',]$Class <- 'Eco'

flight.test$satisfaction <- as.factor(flight.test$satisfaction)
flight.test$Gender <- as.factor(flight.test$Gender)
flight.test$Customer.Type <- as.factor(flight.test$Customer.Type)
flight.test$Type.of.Travel <- as.factor(flight.test$Type.of.Travel)
flight.test$Class <- as.factor(flight.test$Class)


fitted.values.test <- 1/( 1 + exp (-predict(fit3,flight.test) ) )
misc2 <- table( real = as.numeric(flight.test$satisfaction)-1, predicted = as.numeric( fitted.values.test >= p0 ) )
misc2

err2 <- (misc2[2]+misc2[3])/sum(misc2)
err2 #0.0883795

# Examples

#1
x1.new <- data.frame(Gender='Male',Customer.Type='Loyal Customer',Age=40,Type.of.Travel='Business travel',Class='Business',
                     Flight.Distance=0,Departure.Delay.in.Minutes=0,Arrival.Delay.in.Minutes=0)
for (s in satis.regr) {
  x1.new[s] <- 3
}
1/( 1 + exp (-predict(fit3,x1.new) ) )  #0.4792735


#2
x2.new <- data.frame(Gender='Male',Customer.Type='disloyal Customer',Age=40,Type.of.Travel='Personal Travel',Class='Eco',
                     Flight.Distance=0,Departure.Delay.in.Minutes=0,Arrival.Delay.in.Minutes=0)
for (s in satis.regr) {
  x2.new[s] <- 3
}
1/( 1 + exp (-predict(fit3,x2.new) ) )  #0.0006566643


#3
x3.new <- data.frame(Gender='Male',Customer.Type='disloyal Customer',Age=40,Type.of.Travel='Personal Travel',Class='Eco',
                     Flight.Distance=0,Departure.Delay.in.Minutes=0,Arrival.Delay.in.Minutes=0)
for (s in satis.regr) {
  x3.new[s] <- 5
}
1/( 1 + exp (-predict(fit3,x3.new) ) )  #0.2627622


#4
x4.new <- data.frame(Gender='Male',Customer.Type='Loyal Customer',Age=18,Type.of.Travel='Business travel',Class='Business',
                     Flight.Distance=0,Departure.Delay.in.Minutes=0,Arrival.Delay.in.Minutes=0)
for (s in satis.regr) {
  x4.new[s] <- 3
}
1/( 1 + exp (-predict(fit3,x4.new) ) )  #0.4960083


#5
x5.new <- data.frame(Gender='Male',Customer.Type='Loyal Customer',Age=80,Type.of.Travel='Business travel',Class='Business',
                     Flight.Distance=0,Departure.Delay.in.Minutes=0,Arrival.Delay.in.Minutes=0)
for (s in satis.regr) {
  x5.new[s] <- 3
}
1/( 1 + exp (-predict(fit3,x5.new) ) )  #0.4489913



