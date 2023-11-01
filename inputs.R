library(combinat)
library(xlsx)
library(conbinat)
library(prob)
library(gifski)


valor <- c('W', 'Y', 'P', 'T', 'Z', 'L')

dataframe <- t((data.frame(permn(valor))))

matrix <- matrix((permn(valor)))

matrix2 <- data.frame(matrix)



dataframe2 <- cbind.data.frame(dataframe, row.names = F)

dataframe3 <- data.frame(dataframe2$`1`,
                        dataframe2$`2`,
                        dataframe2$`3`,
                        dataframe2$`4`,
                        dataframe2$`5`,
                        row.names = F)

write.csv(x = dataframe, file = "inputs.csv")
write.csv(x = dataframe3, file = "inputs2.csv")










