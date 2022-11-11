library(car)
library(ROCit)  #ROC curve
library(lattice) #dotplot


flight <- read.csv("Data/new_train.csv")[-1]


flight$satisfaction <- as.factor(flight$satisfaction)
flight$Gender <- as.factor(flight$Gender)
flight$Customer.Type <- as.factor(flight$Customer.Type)
flight$Type.of.Travel <- as.factor(flight$Type.of.Travel)
flight$Class <- as.factor(flight$Class)

#get numeric regressors 
regressors <- labels(flight)[2][[1]][c(3,6:22)]

###
###
#####First model: effect of the categorical variable only on the intercept
###
formula <- 'satisfaction ~ Gender + Customer.Type + Type.of.Travel + Class'
for (i in regressors) {
  formula <- paste(formula, '+', i)
}
fit <- glm(formula , data=flight, family='binomial')
summary(fit)
# we remove the dependency on the gender and flight distance, indeed
a1 <- integer(24)
a2 <- integer(24)
a1[2] <- 1
a2[8] <-1
linearHypothesis(fit, rbind(a1, a2), c(0,0)) 
#new model
formula <- 'satisfaction ~ . - Gender - Flight.Distance'
fit <- glm(formula , data=flight, family='binomial')
summary(fit)

fit.aic <- fit$aic
#Likelihood ratio R^2_L
fit.R2L <- (fit$null.deviance - fit$deviance )/fit$null.deviance

#ROC curve
ROCit_obj <- rocit(score=fit$fitted.values,class = flight$satisfaction)
#Plot + get optimal p0 according to Youden index
a <- plot(ROCit_obj,values=T)
p0 <- a$`optimal Youden Index point`[4]
#AUC
fit.auc <- ROCit_obj$AUC

misc <- table( real = as.numeric(flight$satisfaction)-1, predicted = as.numeric( fit$fitted.values >= p0 ) )
misc

err <- (misc[2]+misc[3])/sum(misc)
err #10% of misclassification error on training set, not so good

#order the coefficients
coeff = fit$coefficients[order(fit$coefficients)]
coeff.rank <- data.frame(Name = 1:length(coeff), Value = 1:length(coeff))
for (i in 1:length(coeff) ) {
  coeff.rank[i,1] <- labels(coeff[i])
  coeff.rank[i,2] <- coeff[[i]]
}
coeff.rank

#compute now the confidence intervals for the beta, at level 0.05 with Bonferroni correction
alpha <- 0.05
p <- length(fit$coefficients)
conf.int <- confint(fit, level= 1-alpha/p)


#print intervals (except the intercept)
test <- data.frame(Name=labels(conf.int)[[1]],inf=conf.int[1:22],sup=conf.int[23:44])
test <- test[order(fit$coefficients),]
test <- data.frame(test,cen=coeff.rank$Value)
test <- test[-1,]
test2 <- cbind(inf = test$inf,cen = test$cen, sup = test$sup)
rownames(test2) <- test$Name
dotplot(test2,col='blue',xlab='',pch=c('|','•','|'),cex=c(2,3,2))


#print only the satisfaction level intervals
test3 <- test[c(4:6,10:20),]
test4 <- cbind(inf = test3$inf,cen = test3$cen, sup = test3$sup)
rownames(test4) <- test3$Name
dotplot(test4,col='blue',xlab='',pch=c('|','•','|'),cex=c(2,3,2))


#probability of satisfaction of the 'best' group again the 'worst'
#suppose no delays, age=40 and 3 stars in each evaluation
satis.regr <- labels(flight)[2][[1]][7:20]

x1.new <- data.frame(Gender='Male',Customer.Type='Loyal Customer',Age=40,Type.of.Travel='Business travel',Class='Business',
                     Flight.Distance=0,Departure.Delay.in.Minutes=0,Arrival.Delay.in.Minutes=0)
for (s in satis.regr) {
  x1.new[s] <- 3
}
1/( 1 + exp (-predict(fit,x1.new) ) )

x2.new <- data.frame(Gender='Male',Customer.Type='disloyal Customer',Age=40,Type.of.Travel='Personal Travel',Class='Eco Plus',
                     Flight.Distance=0,Departure.Delay.in.Minutes=0,Arrival.Delay.in.Minutes=0)
for (s in satis.regr) {
  x2.new[s] <- 3
}
1/( 1 + exp (-predict(fit,x2.new) ) )

#probability of satisfaction for group1 (see before), no delays, age=18 and 3 stars in each evaluation
#and for group1, no delays, age=80 and 3 stars in each evaluation
x3.new <- data.frame(Gender='Male',Customer.Type='Loyal Customer',Age=18,Type.of.Travel='Business travel',Class='Business',
                     Flight.Distance=0,Departure.Delay.in.Minutes=0,Arrival.Delay.in.Minutes=0)
for (s in satis.regr) {
  x3.new[s] <- 3
}
1/( 1 + exp (-predict(fit,x3.new) ) )

x4.new <- data.frame(Gender='Male',Customer.Type='Loyal Customer',Age=80,Type.of.Travel='Business travel',Class='Business',
                     Flight.Distance=0,Departure.Delay.in.Minutes=0,Arrival.Delay.in.Minutes=0)
for (s in satis.regr) {
  x4.new[s] <- 3
}
1/( 1 + exp (-predict(fit,x4.new) ) )


#test the classifier on the test set
flight.test <- read.csv("Data/new_test.csv")[-1]

flight.test$satisfaction <- as.factor(flight.test$satisfaction)
flight.test$Gender <- as.factor(flight.test$Gender)
flight.test$Customer.Type <- as.factor(flight.test$Customer.Type)
flight.test$Type.of.Travel <- as.factor(flight.test$Type.of.Travel)
flight.test$Class <- as.factor(flight.test$Class)


fitted.values.test <- 1/( 1 + exp (-predict(fit,flight.test) ) )
misc2 <- table( real = as.numeric(flight.test$satisfaction)-1, predicted = as.numeric( fitted.values.test >= p0 ) )
misc2

err2 <- (misc2[2]+misc2[3])/sum(misc2)
err2 
