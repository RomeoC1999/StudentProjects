#dataset
setwd("C:/Users/carra/OneDrive/Documenti/Lavoro/Projects/Passenger Satisfaction")
train = read.csv("Data/train.csv")

#remove first column (row number)
train = train[,2:25]


#replace NA with the median
train2 = train[which(!is.na(train$Arrival.Delay.in.Minutes)),]
tmp <- median(train2$Arrival.Delay.in.Minutes)
train[which(is.na(train$Arrival.Delay.in.Minutes)),23]=tmp
#set all numeric data as integer
train[23] = lapply(train[23] , as.integer)


#delete the rows with satisfaction level equal to zero (that means they are N/A)
train2 = train[which(train$Inflight.wifi.service != 0),]
train2 = train2[which(train2$Departure.Arrival.time.convenient != 0),]
train2 = train2[which(train2$Ease.of.Online.booking != 0),]
train2 = train2[which(train2$Gate.location != 0),]
train2 = train2[which(train2$Food.and.drink != 0),]
train2 = train2[which(train2$Online.boarding != 0),]
train2 = train2[which(train2$Seat.comfort != 0),]
train2 = train2[which(train2$Inflight.entertainment != 0),]
train2 = train2[which(train2$On.board.service != 0),]
train2 = train2[which(train2$Leg.room.service != 0),]
train2 = train2[which(train2$Baggage.handling != 0),]
train2 = train2[which(train2$Checkin.service != 0),]
train2 = train2[which(train2$Inflight.service != 0),]
train2 = train2[which(train2$Cleanliness != 0),]

#save the dataset without N/A values
write.csv(train2, file = "Data/new_train.csv", row.names = F)



#Do the same for the test dataset


test = read.csv("Data/test.csv")

#remove first column (row number)
test = test[,2:25]


#replace NA with the median
test2 = test[which(!is.na(test$Arrival.Delay.in.Minutes)),]
tmp <- median(test2$Arrival.Delay.in.Minutes)
test[which(is.na(test$Arrival.Delay.in.Minutes)),23]=tmp
#set all numeric data as integer
test[23] = lapply(test[23] , as.integer)


#delete the rows with satisfaction level equal to zero (that means they are N/A)
test2 = test[which(test$Inflight.wifi.service != 0),]
test2 = test2[which(test2$Departure.Arrival.time.convenient != 0),]
test2 = test2[which(test2$Ease.of.Online.booking != 0),]
test2 = test2[which(test2$Gate.location != 0),]
test2 = test2[which(test2$Food.and.drink != 0),]
test2 = test2[which(test2$Online.boarding != 0),]
test2 = test2[which(test2$Seat.comfort != 0),]
test2 = test2[which(test2$Inflight.entertainment != 0),]
test2 = test2[which(test2$On.board.service != 0),]
test2 = test2[which(test2$Leg.room.service != 0),]
test2 = test2[which(test2$Baggage.handling != 0),]
test2 = test2[which(test2$Checkin.service != 0),]
test2 = test2[which(test2$Inflight.service != 0),]
test2 = test2[which(test2$Cleanliness != 0),]

#save the dataset without N/A values
write.csv(test2, file = "Data/new_test.csv", row.names = F)

