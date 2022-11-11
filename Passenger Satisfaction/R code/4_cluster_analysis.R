# ######cluster analysis
train = read.csv("new_train.csv")

#building the matrix containing all the distances between the vector of every
#variable to rate
euclidean <- function(a, b) sqrt(sum((a - b)^2))

n_rat = 14 #14 different features rated
matr.rate.dist = matrix(0, nrow = n_rat, ncol = n_rat)
for (i in 1:n_rat){
  for (j in 1:n_rat){
    matr.rate.dist[i,j] = sqrt(euclidean(train[,i+7], train[,j+7]))
  }
}
matr.rate.dist
#does not say much



### K-MEANS
help(kmeans)
numerical_col = c(4,8:21) #i'm considering the variables that introduces
                          #"differences" between the customers
train.num = train[,numerical_col]
#scaling the age bc it's too different from {1...5}
train.num[,1] = train.num[1]/10

result.k <- kmeans(train.num, centers=3, iter.max = 20) # Centers: fixed number of clusters
result.k$cluster

#age vs ratings
x11()
r=4
plot(train.num[,c(1,r)], col = result.k$cluster+1, pch=20)


### choose k:
b <- NULL
w <- NULL
for(k in 1:10){
  
  result.k <- kmeans(train.num, k) #calcolo kmean per quel k
  w <- c(w, sum(result.k$wit)) #aggiorno la riga contenente la somma della var nei gruppi
  b <- c(b, result.k$bet)
  
}

x11()
matplot(1:10, w/(w+b), pch='', xlab='clusters', ylab='within/tot', main='Choice of k', ylim=c(0,1))
lines(1:10, w/(w+b), type='b', lwd=2)
#k=3 !


###splitting the dataset using the three groups I found in order to compare the
#different features of the groups
train1 = train[which(result.k$cluster==1),]
train2 = train[which(result.k$cluster==2),]
train3 = train[which(result.k$cluster==3),]

#matrix of the mean in the different datasets
mean.mat = matrix(nrow = 4, ncol = 14)
mean.mat[1,] = sapply(train[,8:21], mean)
mean.mat[2,] = sapply(train1[,8:21], mean)
mean.mat[3,] = sapply(train2[,8:21], mean)
mean.mat[4,] = sapply(train3[,8:21], mean)
mean.mat = as.data.frame(mean.mat)
colnames(mean.mat) = colnames(train)[8:21]
rownames(mean.mat) = c('dataset','group1','group2','group3')
mean.mat = t(mean.mat)
mean.mat
mean.mat[,4]-mean.mat[,1]
# group 1
#   group 1 has smaller rate in almost all the features, in particular for
#   - flight entertainment
#   - food and drink
#   - cleanliness
# 
# group2
#   group 2 gave much higher rates in all the features; very high rates for
#   inflight wifi service and ease of online booking
#   
# group3
#   for group 3 some features are worse (than the mean rate), some other are better;
#   the services that are bad, comparing to the mean of the whole dataset, are
#   - inflight wifi service
#   - ease of online booking
#   - departure arrival time convenient
#   - gate location

#age??
ages = c(mean(train1[,4]), mean(train2[,4]), mean(train3[,4]))
x11()
par(mfrow=c(3,1))
hist(train1[,4], xlab = 'age group 1')
hist(train2[,4], xlab = 'age group 2')
hist(train3[,4], xlab = 'age group 3')
#no particular differences in age among the groups, maybe first group younger



train = train3
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

# group1
#   group 1 equally divided between males and females, as the dataset is;
#   79% of loyal customer (a little lower than the ds, which has 84%);
#   business travel 59% (in the ds it's 69%);
#   business class 32% (against 49%)!! 
#   90% of group1 is dissatisfied !!
#   
# group2
#   equally divided between m and f
#   88% of loyal customer (against 84%)
#   76% business travel (against 69%)
#   58% of business class (against 49%) !!
#   30% only is dissatisfied!! happiest group
# 
# group3
#   equally divided between m and f
#   86% of loyal customer (against 84%)
#   73% business travel (against 69%)
#   58% of business class (against 49%) !!
#   45% dissatisfied
#   almost same cathegorical division of group2, but half of the group is
#   satisfied and the other half is not