logbook-jdbc-sink
=

[zalando-logbook](https://github.com/zalando/logbook) JDBC sink. 

See [JdbcSink](https://github.com/silviuilie/logbook-jdbc-sink/blob/master/src/main/java/org/pm/zalando/logbook/jdbc/JdbcSink.java) or [JdbcBatchSink](https://github.com/silviuilie/logbook-jdbc-sink/blob/master/src/main/java/org/pm/zalando/logbook/jdbc/batch/JdbcBatchSink.java).



 [![java/maven build](https://github.com/silviuilie/logbook-jdbc-sink/actions/workflows/maven.yml/badge.svg)](https://github.com/silviuilie/logbook-jdbc-sink/actions/workflows/maven.yml) ![Coverage Status](./doc/badges/coverage.svg)

TODO: benchmarks

 http requests timing in seconds :
=

postgres (test container postgres:14.2-alpine) default (LOGGED) http requests table

|sink type| total time | db time<sup>*</sup> | http requests/rowcount  
|-|------------|---------------------|----------| 
|DefaultSink|  138   |        18.760441              | 1.000      |
|JdbcBatchSink|  105     |   0.021894                | 1.000     | 
|DefaultSink|       1354     |   34.27333                  | 10.000    | 
|JdbcBatchSink|     1042     |  36.878302             | 10.000    | 

 <sup>*</sup> - db time(max-min sysdate row); for batch sink batch size of 1.000 satements
 
## TODOs 
 - oracle 19c
 - add jmh benchmarks
 - 
 <!--

 complete commit-jacoco-total-to-readme 

 
 [![Coverage Status](./doc/badges/coverage.svg)](https://github.com/silviuilie/logbook-jdbc-sink/actions/workflows/maven.yml) 
 
 -->

 
