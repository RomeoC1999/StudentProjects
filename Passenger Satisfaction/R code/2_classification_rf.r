

##### Data initialization

library(tree)
library(randomForest)

train = read.csv("Data/new_train.csv")
test = read.csv("Data/new_test.csv")

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

# Misclassification error of not-business customers
pred_not_business <- predict(fit_not_business, test_not_business, type = "class")
table_not_business = table(pred_not_business , test_not_business$satisfaction)
table_not_business

# Misclassification error
error = (table_business[1,2]+table_business[2,1]+table_not_business[1,2]+table_not_business[2,1])/dim(test)[1]
error


##### Random Forest

# Parameters initialization
m = round(sqrt(length(satisfaction_col)-1))   # m=4, p=14
ntree = 10

# Random forest for business class
rf.business = randomForest(formula = factor(satisfaction) ~ ., data = train_business, mtry = m,
                         importance = TRUE, ntree=ntree)
varImpPlot(rf.business)

# Random forest for not-business class
rf.not_business = randomForest(formula = factor(satisfaction) ~ ., data = train_not_business, mtry = m,
                           importance = TRUE, ntree=ntree)
varImpPlot(rf.not_business)









