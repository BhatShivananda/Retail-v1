# Retail-v1

API aggregates product data from multiple sources and return it as JSON to the caller.

## Steps to run this app locally

## 1. cassandra setup (https://github.com/riptano/ccm)
````
brew install ccm
ccm create test -v 3.11.2 -n 1 -s

Connect to cqlsh and execute below:

ccm node1 cqlsh

CREATE KEYSPACE retail WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };

CREATE TABLE IF NOT EXISTS retail.item_price (
    tcin text,
    price text,
    created_at timestamp,
    updated_at timestamp,
    PRIMARY KEY (tcin)
);

INSERT INTO retail.item_price(tcin, price, created_at, updated_at) VALUES ('13860428','123.23',1535383238852,1535383238852);
````
## 2. Build springboot (executable) jar

Run below command to clone git repo
```
git clone git@github.com:BhatShivananda/Retail-v1.git
Retail-v1> sh gradlew clean build
```

## 3. Run below command to bring up Retail-v1 app and access APIs on localhost
````
Retail-v1> sh gradlew bootRun
````

## Swagger - Find API details here
````
API documentation in Swagger 2.0 format is available at http://localhost:8080/retail/v1/swagger-ui.html
````
<img width="1048" alt="screen shot 2018-09-08 at 7 09 09 pm" src="https://user-images.githubusercontent.com/10213560/45259792-d2563280-b39a-11e8-8745-ab851bd4c4ae.png">

## Test coverage
````
jacoco test coverage can be found here - http://localhost:63342/Retail-v1/build/reports/jacoco/test/html/index.html
````
<img width="1036" alt="screen shot 2018-09-08 at 7 12 28 pm" src="https://user-images.githubusercontent.com/10213560/45259810-2d882500-b39b-11e8-83e6-222545099508.png">


## Functional test suite
````
cd Retail-v1-Test/build/libs
java -jar Retail-v1-Test.jar
````

## Log4j2
````
log4j2is used for this implementation and logs are availble under - /Retail-v1/logs
````

## Metrics using dropwizards
````
Example:
/retail/v1/products/{tcin}.GET
             count = 1
         mean rate = 0.01 calls/second
     1-minute rate = 0.01 calls/second
     5-minute rate = 0.00 calls/second
    15-minute rate = 0.00 calls/second
               min = 557.96 milliseconds
               max = 557.96 milliseconds
              mean = 557.96 milliseconds
            stddev = 0.00 milliseconds
            median = 557.96 milliseconds
              75% <= 557.96 milliseconds
              95% <= 557.96 milliseconds
              98% <= 557.96 milliseconds
              99% <= 557.96 milliseconds
            99.9% <= 557.96 milliseconds
````
