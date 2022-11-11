library(car)
library(ROCit)  #ROC curve
library(lattice) #dotplot


flight <- read.csv("Data/new_train.csv")[-1]


flight$satisfaction <- as.factor(flight$satisfaction)
flight$Gender <- as.factor(flight$Gender)
flight$Customer.Type <- as.factor(flight$Customer.Type)
flight$Type.of.Travel <- as.factor(flight$Type.of.Travel)
flight$Class <- as.factor(flight$Class)

###
###
#####Second model: effect of the categorical variable on the "satisfaction" regressors (the ones the companies can improve)
###
satis.regr <- labels(flight)[2][[1]][7:20]
satis.regr


#GENDER
formula <- 'satisfaction ~  '
for (i in satis.regr) {
  formula <- paste(formula, '+ Gender*', i)
}
fit2.gender <- glm(formula , data=flight, family='binomial')
summary(fit2.gender)

fit2.gender$aic
(fit2.gender$null.deviance - fit2.gender$deviance )/fit2.gender$null.deviance

#Bonferroni Confidence Intervals 
param <- 17:30
alpha <- 0.05
p <- length(param)
conf.int.gender <- confint(fit2.gender, parm=param , level= 1-alpha/p)

#print intervals
coeff.gender <- fit2.gender$coefficients[param]
coeff.order <- order(coeff.gender)
coeff.gender <- coeff.gender[coeff.order]
test <- data.frame(Name=labels(conf.int.gender)[[1]],inf=conf.int.gender[1:14],sup=conf.int.gender[15:28])
test <- test[coeff.order,]
test <- data.frame(test,cen=coeff.gender)
test2 <- cbind(inf = test$inf,cen = test$cen, sup = test$sup)
names <- 1:14
for (i in 1:14) names[i] <- strsplit(test$Name,':')[[i]][2]
rownames(test2) <- names

dotplot(test2,col='blue',xlab='',pch=c('|','•','|'),cex=c(2,3,2))




#CUSTOMER TYPE
formula <- 'satisfaction ~ '
for (i in satis.regr) {
  formula <- paste(formula, '+ Customer.Type*', i)
}
fit2.customer <- glm(formula , data=flight, family='binomial')
summary(fit2.customer)
fit2.customer$aic
(fit2.customer$null.deviance - fit2.customer$deviance )/fit2.customer$null.deviance

#Bonferroni Confidence Intervals 
param <- 17:30
alpha <- 0.05
p <- length(param)
conf.int.customer <- confint(fit2.customer, parm=param , level= 1-alpha/p)

#print intervals
coeff.customer <- fit2.customer$coefficients[param]
coeff.order <- order(coeff.customer)
coeff.customer <- coeff.customer[coeff.order]
test <- data.frame(Name=labels(conf.int.customer)[[1]],inf=conf.int.customer[1:14],sup=conf.int.customer[15:28])
test <- test[coeff.order,]
test <- data.frame(test,cen=coeff.customer)
test2 <- cbind(inf = test$inf,cen = test$cen, sup = test$sup)
names <- 1:14
for (i in 1:14) names[i] <- strsplit(test$Name,':')[[i]][2]
rownames(test2) <- names

dotplot(test2,col='blue',xlab='',pch=c('|','•','|'),cex=c(2,3,2))




#TRAVEL TYPE
formula <- 'satisfaction ~ '
for (i in satis.regr) {
  formula <- paste(formula, '+ Type.of.Travel *', i)
}
fit2.travel <- glm(formula , data=flight, family='binomial')
summary(fit2.travel)
fit2.travel$aic
(fit2.travel$null.deviance - fit2.travel$deviance )/fit2.travel$null.deviance


#Bonferroni Confidence Intervals 
param <- 18:30  #we remove Inflight.wifi.service because it has a very large estimate and it is not significant
alpha <- 0.05
p <- length(param)
conf.int.travel <- confint(fit2.travel, parm=param , level= 1-alpha/p)

#print intervals
coeff.travel <- fit2.travel$coefficients[param]
coeff.order <- order(coeff.travel)
coeff.travel <- coeff.travel[coeff.order]
test <- data.frame(Name=labels(conf.int.travel)[[1]],inf=conf.int.travel[1:13],sup=conf.int.travel[14:26])
test <- test[coeff.order,]
test <- data.frame(test,cen=coeff.travel)
test2 <- cbind(inf = test$inf,cen = test$cen, sup = test$sup)
names <- 1:13
for (i in 1:13) names[i] <- strsplit(test$Name,':')[[i]][2]
rownames(test2) <- names

dotplot(test2,col='blue',xlab='',pch=c('|','•','|'),cex=c(2,3,2))





#CLASS 
formula <- 'satisfaction ~ '
for (i in satis.regr) {
  formula <- paste(formula, '+ Class *', i)
}
fit2.class <- glm(formula , data=flight, family='binomial')
summary(fit2.class)
fit2.class$aic
(fit2.class$null.deviance - fit2.class$deviance )/fit2.class$null.deviance

#Bonferroni Confidence Intervals
param <- 18:45
alpha <- 0.05
p <- 14*3
conf.int.class <- confint(fit2.class, parm=param , level= 1-alpha/p)

#print intervals
coeff.class.eco <- fit2.class$coefficients[18+2*0:13]
coeff.class.eco.plus <- fit2.class$coefficients[19+2*0:13]
coeff.order.eco <- order(coeff.class.eco)
coeff.order.eco.plus <- order(coeff.class.eco.plus)
coeff.class.eco <- coeff.class.eco[coeff.order.eco]
coeff.class.eco.plus <- coeff.class.eco.plus[coeff.order.eco.plus]
#eco
test <- data.frame(Name=labels(conf.int.class)[[1]][1+2*0:13],inf=conf.int.class[1+2*0:13],sup=conf.int.class[29+2*0:13])
test <- test[coeff.order.eco,]
test <- data.frame(test,cen=coeff.class.eco)
test2 <- cbind(inf = test$inf,cen = test$cen, sup = test$sup)
names <- 1:14
for (i in 1:14) names[i] <- strsplit(test$Name,':')[[i]][2]
rownames(test2) <- names
dotplot(test2,col='blue',xlab='',pch=c('|','•','|'),cex=c(2,3,2))
#eco plus
test <- data.frame(Name=labels(conf.int.class)[[1]][2+2*0:13],inf=conf.int.class[2+2*0:13],sup=conf.int.class[30+2*0:13])
test <- test[coeff.order.eco.plus,]
test <- data.frame(test,cen=coeff.class.eco.plus)
test2 <- cbind(inf = test$inf,cen = test$cen, sup = test$sup)
names <- 1:14
for (i in 1:14) names[i] <- strsplit(test$Name,':')[[i]][2]
rownames(test2) <- names
dotplot(test2,col='blue',xlab='',pch=c('|','•','|'),cex=c(2,3,2))

#eco=eco.plus??????
library(multcomp)

names <- integer(14)
lowers <- integer(14)
estimates <- integer(14)
uppers <- integer(14)
start <- 18
for (i in 0:13) {
  coeff.vect <- integer(45) 
  coeff.vect[start + 2*i] <- 1
  coeff.vect[start+1 + 2*i] <- -1
  lh <- glht(fit2.class, linfct = t(coeff.vect))
  res <- confint(lh,level = 1-alpha/p)
  names[i+1] <- strsplit(labels(fit2.class$coefficients)[start + 2*i],':')[[1]][2]
  lowers[i+1] <- res$confint[2]
  estimates[i+1] <- res$confint[1]
  uppers[i+1] <- res$confint[3]
}
#print intervals
test <- data.frame(Name=names,inf=lowers,cen=estimates,sup=uppers)
test <- test[order(test$cen),]
test2 <- cbind(inf = test$inf,cen = test$cen, sup = test$sup)
rownames(test2) <- test$Name
dotplot(test2,col='blue',xlab='',pch=c('|','•','|'),cex=c(2,3,2))

