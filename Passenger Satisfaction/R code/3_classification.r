
#_________________________________________________________________________
########## Setup

library(tree)
library(rpart)
library(rgl)
library(leaps)
library(ROCit)

train = read.csv("Data/new_train.csv")
categorical_col = c(2,3,5,6,24)
satisfaction_col = c(8:21, 24)
train[categorical_col] = lapply(train[categorical_col], factor)
# train2[,24] = ifelse(train2[,24]=='satisfied', 1, 0)

# factor is needed, otherwise the classifier does not work properly



#_________________________________________________________________________
########## Regression

# Improvable factors explain about 50% of the variability

fit.full <- regsubsets(satisfaction~
                         Inflight.wifi.service + Departure.Arrival.time.convenient +
                         Ease.of.Online.booking + Gate.location + Food.and.drink + Online.boarding +
                         Seat.comfort + Inflight.entertainment + On.board.service + Leg.room.service +
                         Baggage.handling + Checkin.service + Inflight.service + Cleanliness,
                         data=train, nvmax=14)
summary(fit.full)

reg.summary <- summary(fit.full)

x11(height=7,width=14)
par(mfrow=c(1,3))
plot(reg.summary$rsq,xlab="Number of Variables",ylab="R-squared",type="b")
plot(reg.summary$adjr2,xlab="Number of Variables",ylab="Adjusted RSq",type="b")
plot(reg.summary$rss,xlab="Number of Variables",ylab="RSS",type="b")

max(reg.summary$adjr2) # Max 48%

reg.summary$adjr2[3] # With 3 regressors R^2 = 43.7%

coef(fit.full,1)
# Online.boarding
coef(fit.full,2)
# Online.boarding, Inflight.entertainment
coef(fit.full,3)
# Online.boarding, Inflight.entertainment, Leg.room.service 
coef(fit.full,4)
# Online.boarding, Inflight.entertainment, Leg.room.service, On.board.service
coef(fit.full,5)
# Online.boarding, Inflight.entertainment, Leg.room.service, Inflight.wifi.service, Departure.Arrival.time.convenient



#_________________________________________________________________________
########## Logistic regression

mod = glm(satisfaction ~
             Inflight.wifi.service + Departure.Arrival.time.convenient +
             Ease.of.Online.booking + Gate.location + Food.and.drink + Online.boarding +
             Seat.comfort + Inflight.entertainment + On.board.service + Leg.room.service +
             Baggage.handling + Checkin.service + Inflight.service + Cleanliness,
           family="binomial", data=train)

# Order the coefficients excluding the intercept
coeff = summary(mod)$coefficients[-1,1]
coeff = sort(coeff, decreasing = T)
coeff

### Coefficients higher than 0.2
# Online.boarding         1.01025422
# Inflight.wifi.service   0.62787784
# Leg.room.service        0.39859369
# On.board.service        0.36077638
# Inflight.entertainment  0.35669204
# Checkin.service         0.26026448



#_________________________________________________________________________
########## AUC

# Get satisfaction level names
train2 = train[satisfaction_col]
covariates = labels(train2)[2][[1]]
covariates = head(covariates, -1)     # Delete train$satisfaction 
covariates = c(covariates, "ALL IN")
cov_AUC = c(1:15)

# Computing the AUC
for (k in 1:15)
{
  if (k<15)   formula = as.formula(paste("satisfaction ~ ",covariates[k]))
  else        formula = satisfaction ~ .
  model = glm(formula , data=train2, family='binomial')
  ROCit_obj = rocit(score=model$fitted.values,class = train2$satisfaction)
  cov_AUC[k] = ROCit_obj$AUC
}

rank = data.frame(covariates,cov_AUC)
rank = rank[order(rank$cov_AUC,decreasing = T),]
rank

### AUC over 70%
# ALL IN                  0.9186816
# Online.boarding         0.8375912
# Inflight.entertainment  0.7495044
# Seat.comfort            0.7200363
# Inflight.wifi.service   0.7029512
# On.board.service        0.7020131



#_________________________________________________________________________
########## CART classification

# First model

fit = tree(satisfaction ~
             Inflight.wifi.service + Departure.Arrival.time.convenient +
             Ease.of.Online.booking + Gate.location + Food.and.drink + Online.boarding +
             Seat.comfort + Inflight.entertainment + On.board.service + Leg.room.service +
             Baggage.handling + Checkin.service + Inflight.service + Cleanliness,
           method="class", data=train)
x11()
plot(fit)
text(fit, pretty = 0)

# Second model (without Online.boarding)

fit = tree(satisfaction ~
             Inflight.wifi.service + Departure.Arrival.time.convenient +
             Ease.of.Online.booking + Gate.location + Food.and.drink + 
             Seat.comfort + Inflight.entertainment + On.board.service + Leg.room.service +
             Baggage.handling + Checkin.service + Inflight.service + Cleanliness,
           method="class", data=train)
x11()
plot(fit)
text(fit, pretty = 0)

# Third model (without Online.boarding and Inflight.entertainment)

fit = tree(satisfaction ~
             Inflight.wifi.service + Departure.Arrival.time.convenient +
             Ease.of.Online.booking + Gate.location + Food.and.drink + 
             Seat.comfort + On.board.service + Leg.room.service +
             Baggage.handling + Checkin.service + Inflight.service + Cleanliness,
           method="class", data=train)
x11()
plot(fit)
text(fit, pretty = 0)

# Most important factors: Online.boarding, Inflight.entertainment, Inflight.wifi.service



#_________________________________________________________________________
########## List of the factors

#  1) id
#  2) Gender
#  3) Customer.type, Loyal customer, disloyal customer
#  4) Age
#  5) Type.of.Travel, Personal Travel, Business Travel
#  6) Class, Business, Eco, Eco Plus
#  7) Flight.Distance

# Improvable factors (14)
#  8) Inflight.wifi.service
#  9) Departure.Arrival.time.convenient
# 10) Ease.of.Online.booking
# 11) Gate.location
# 12) Food.and.drink
# 13) Online.boarding
# 14) Seat.comfort
# 15) Inflight.entertainment
# 16) On.board.service
# 17) Leg.room.service
# 18) Baggage.handling
# 19) Checkin.service
# 20) Inflight.service
# 21) Cleanliness

# 22) Departure.Delay.in.minutes
# 23) Arrival.Delay.in.minutes
# 24) satisfaction



#_________________________________________________________________________
########## Evaluating the model -> By now, it's useless

evaluate <- function(col){
  
  res = rep(0,5)
  
  for(i in 1:5){
    n = length(which(train[,col]==i))
    res[i] = length(which(train[,col]==i & train[,"satisfaction"]==1))/n
  }
  
  res
}

evaluate("Online.boarding")
evaluate("Inflight.entertainment")
evaluate("Leg.room.service")
evaluate("On.board.service")
evaluate("Inflight.wifi.service")
evaluate("Departure.Arrival.time.convenient")
evaluate("Cleanliness")

