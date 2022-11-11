train = read.csv("Data/new_train.csv")

#set char data as factor
categorical_col = c(2,3,5,6,24)
train[categorical_col] = lapply(train[categorical_col] , factor)

#pie chart for categorical data
library(lessR)
PieChart(Gender, hole = 0, values = "%", values_size=1.5, data = train,
         fill = c("lightblue", "pink"), main = "")
PieChart(Customer.Type, hole = 0, values = "%", values_size=1.5, data = train,
         fill = c("lightblue", "pink"), main = "")
PieChart(Type.of.Travel, hole = 0, values = "%", values_size=1.5, data = train,
         fill = c("lightblue", "pink"), main = "")
PieChart(Class, hole = 0, values = "%", values_size=1.5, data = train,
         fill = c("lightblue", "pink","lightgreen"), main = "")
PieChart(satisfaction, hole = 0, values = "%", values_size=1.5, data = train,
         fill = c("lightblue", "pink"), main = "")

#GENDER ANALYSIS
gender_var = factor(c(rep("Male satisfied", dim(train[which(train$Gender=="Male" & train$satisfaction=="satisfied"),])[1]), 
                       rep("Female satisfied", dim(train[which(train$Gender=="Female" & train$satisfaction=="satisfied"),])[1]),
                       rep("Male not satisfied", dim(train[which(train$Gender=="Male" & train$satisfaction=="neutral or dissatisfied"),])[1]),
                       rep("Female not satisfied", dim(train[which(train$Gender=="Female" & train$satisfaction=="neutral or dissatisfied"),])[1])))
gender = data.frame(gend = gender_var)
PieChart(gend, hole = 0, values = "%", values_size=1.5, data = gender,
         fill = c("pink","red","lightblue","blue"), main = "")
#proportion test
alpha = 0.05
satisfied = c(dim(train[which(train$Gender=="Male" & train$satisfaction=="satisfied"),])[1],
              dim(train[which(train$Gender=="Female" & train$satisfaction=="satisfied"),])[1]) 
total = c(dim(train[which(train$Gender=="Male"),])[1], 
          dim(train[which(train$Gender=="Female"),])[1])
prop.test(satisfied,total, alternative = "two.sided", conf.level = 1-alpha)



#CUSTOMER TYPE ANALYSIS
customer.type_var = factor(c(rep("Loyal customer satisfied", dim(train[which(train$Customer.Type=="Loyal Customer" & train$satisfaction=="satisfied"),])[1]), 
                      rep("Disloyal customer satisfied", dim(train[which(train$Customer.Type=="disloyal Customer" & train$satisfaction=="satisfied"),])[1]),
                      rep("Loyal customer not satisfied", dim(train[which(train$Customer.Type=="Loyal Customer" & train$satisfaction=="neutral or dissatisfied"),])[1]),
                      rep("Disloyal customer not satisfied", dim(train[which(train$Customer.Type=="disloyal Customer" & train$satisfaction=="neutral or dissatisfied"),])[1])))
customer.type = data.frame(cust = customer.type_var)
PieChart(cust, hole = 0, values = "%", values_size=1.5, data = customer.type,
         fill = c("pink","red","lightblue","blue"), main = "")
#proportion test 
alpha = 0.05
satisfied = c(dim(train[which(train$Customer.Type=="Loyal Customer" & train$satisfaction=="satisfied"),])[1],
              dim(train[which(train$Customer.Type=="disloyal Customer" & train$satisfaction=="satisfied"),])[1]) 
total = c(dim(train[which(train$Customer.Type=="Loyal Customer"),])[1], 
          dim(train[which(train$Customer.Type=="disloyal Customer"),])[1])
prop.test(satisfied,total, alternative = "two.sided", conf.level = 1-alpha)


#TRAVEL TYPE ANALYSIS
travel.type_var = factor(c(rep("Personal travel satisfied", dim(train[which(train$Type.of.Travel=="Personal Travel" & train$satisfaction=="satisfied"),])[1]), 
                             rep("Business travel satisfied", dim(train[which(train$Type.of.Travel=="Business travel" & train$satisfaction=="satisfied"),])[1]),
                             rep("Personal travel not satisfied", dim(train[which(train$Type.of.Travel=="Personal Travel" & train$satisfaction=="neutral or dissatisfied"),])[1]),
                             rep("Business travel not satisfied", dim(train[which(train$Type.of.Travel=="Business travel" & train$satisfaction=="neutral or dissatisfied"),])[1])))
travel.type = data.frame(travel = travel.type_var)
PieChart(travel, hole = 0, values = "%", values_size=1.5, data = travel.type,
         fill = c("pink","red","lightblue","blue"), main = "")
#proportion test 
alpha = 0.05
satisfied = c(dim(train[which(train$Type.of.Travel=="Personal Travel" & train$satisfaction=="satisfied"),])[1],
              dim(train[which(train$Type.of.Travel=="Business travel" & train$satisfaction=="satisfied"),])[1]) 
total = c(dim(train[which(train$Type.of.Travel=="Personal Travel"),])[1], 
          dim(train[which(train$Type.of.Travel=="Business travel"),])[1])
prop.test(satisfied,total, alternative = "two.sided", conf.level = 1-alpha)


#CLASS ANALYSIS
class_var = factor(c(rep("Business satisfied", dim(train[which(train$Class=="Business" & train$satisfaction=="satisfied"),])[1]), 
                      rep("Eco satisfied", dim(train[which(train$Class=="Eco" & train$satisfaction=="satisfied"),])[1]),
                      rep("Business not satisfied", dim(train[which(train$Class=="Business" & train$satisfaction=="neutral or dissatisfied"),])[1]),
                      rep("Eco not satisfied", dim(train[which(train$Class=="Eco" & train$satisfaction=="neutral or dissatisfied"),])[1]),
                      rep("Eco Plus satisfied", dim(train[which(train$Class=="Eco Plus" & train$satisfaction=="satisfied"),])[1]),
                      rep("Eco Plus not satisfied", dim(train[which(train$Class=="Eco Plus" & train$satisfaction=="neutral or dissatisfied"),])[1])))
classes = data.frame(cls = class_var)
PieChart(cls, hole = 0, values = "%", values_size=1.5, data = classes,
         fill = c("pink","red","lightblue","lightgreen","green","blue"), main = "")
#proportion test 
alpha = 0.05
satisfied = c(dim(train[which(train$Class=="Business" & train$satisfaction=="satisfied"),])[1],
              dim(train[which(train$Class=="Eco" & train$satisfaction=="satisfied"),])[1],
              dim(train[which(train$Class=="Eco Plus" & train$satisfaction=="satisfied"),])[1]) 
total = c(dim(train[which(train$Class=="Business"),])[1],
          dim(train[which(train$Class=="Eco"),])[1],
          dim(train[which(train$Class=="Eco Plus"),])[1])
prop.test(satisfied,total, alternative = "two.sided", conf.level = 1-alpha)


#
#
#
#
#
#
#
#
#
#
#numerical variables analysis
numerical_col = c(4,7:23)
load("mcshapiro.test.RData")
#mcshapiro.test(train[c(1:5000),numerical_col])

train.numeric = train[numerical_col]

x11()
layout(matrix(c(1:18),3,byrow=T))
for (i in 1:18) {
  hist(train.numeric[,i], main='', ylab='Frequency', freq=F, xlab=colnames(train.numeric[i]))
}

