

##### Data initialization

library(tree)
library(randomForest)

train = read.csv("new_train.csv")
test = read.csv("new_test.csv")

###cambio il satisfaction in 1/0

# satisf = ifelse(train$satisfaction == 'satisfied', 1, 0)
# 
# train = data.frame(cbind(train[,-24],satisf))


###

categorical_col = c(2,3,5,6,24)
satisfaction_col = c(8:21, 24)

train_business = train[which(train[,6]=='Business'), satisfaction_col]
test_business = test[which(test[,6]=='Business'), satisfaction_col]

train_not_business = train[which(train[,6]!='Business'), satisfaction_col]
test_not_business = test[which(test[,6]!='Business'), satisfaction_col]


##### CART

# CART for business
fit_business = tree(factor(satisfaction) ~ ., method="class", data = train_business)
x11()
plot(fit_business)
text(fit_business, pretty = 0)

# CART for not-business
fit_not_business = tree(factor(satisfaction) ~ ., method="class", data = train_not_business)
x11()
plot(fit_not_business)
text(fit_not_business, pretty = 0)


# Misclassification error of business customers
pred_business <- predict(fit_business, test_business, type = "class")
table_business = table(pred_business , test_business$satisfaction)
table_business
error_bus = (table_business[1,2]+table_business[2,1])/dim(test_business)[1]
error_bus

# Misclassification error of not-business customers
pred_eco <- predict(fit_not_business, test_not_business, type = "class")
table_not_business = table(pred_eco, test_not_business$satisfaction)
table_not_business
error_non_bus = (table_not_business[1,2]+table_not_business[2,1])/dim(test_not_business)[1]
error_non_bus

# Misclassification error
error = (table_business[1,2]+table_business[2,1]+table_not_business[1,2]+table_not_business[2,1])/dim(test)[1]
error


##### Random Forest

# Parameters initialization
# m = round(sqrt(length(satisfaction_col)-1))   # m=4, p=14
m=4
ntree = 40

# Random forest for business class
rf.business = randomForest(formula = factor(satisfaction) ~ ., data = train_business, mtry = m,
                         importance = TRUE, ntree=ntree)
rf.business
x11()
varImpPlot(rf.business)

# Random forest for not-business class
rf.not_business = randomForest(formula = factor(satisfaction) ~ ., data = train_not_business, mtry = m,
                           importance = TRUE, ntree=ntree)
rf.not_business
x11()
varImpPlot(rf.not_business)









