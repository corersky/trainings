# Preparation

service sdc start
http://10.0.0.94:18630/ admin/admin

# Login to node
https://10.0.0.94:8443/
-> Create volume
# Demo

## Demo NFS and Hadoop command to get understanding of
```
cd /mapr/demo.mapr.com/demo
vi hello.txt
"Hello MapR"
hadoop fs -ls /demo

> Found 4 items
> tr--------   - mapr mapr          2 2017-10-05 05:28 /demo/deptmanager
> drwxr-xr-x   - sdc  sdc           2 2017-10-05 09:03 /demo/employees
> -rw-r--r--   1 root root         12 2017-10-19 04:06 /demo/hello.txt
> tr--------   - mapr mapr          2 2017-10-05 12:52 /demo/user_profiles

hadoop fs -cat /demo/hello.txt
hadoop fs -rm /demo/hello.txt

ll
> total 2
> lr-------- 1 mapr mapr 2 Oct  5 05:28 deptmanager -> mapr::table::2073.35.131410
> drwxr-xr-x 4 sdc  sdc  2 Oct  5 09:03 employees
> lr-------- 1 mapr mapr 2 Oct  5 12:52 user_profiles -> mapr::table::2073.250.263144
```

## StreamSets
http://10.0.0.94:18630/ admin/admin

## HTTP to local FS
-> Origin: HTTP Server
--> HTTP (HTTP Listening Port: 8000, Application ID in URL: samplerest, Application ID in Url: true)
--> Data Format (Data Format: JSON - JSON Content: Multiple JSON objects)
--> TLS: no
-> Destination: MapR-FS
--> Hadoop FS (Hadoop FS conf dir: /opt/mapr/hadoop/hadoop-2.7.0/etc/hadoop - Hadoop FS Uri: maprfs:///)
--> Output Files (File Type: Text files - File Prefix: sdc-${sdc:id()} - Directory template: /demo/rest/${YYYY()}-${MM()}-${DD()}-${hh()} - File suffix: json)
--> Data Format (Data Format: JSON - JSON Content: Multiple objects)

# Open Chrome - Restlet-Client: chrome-extension://aejoelaoggembcahagimdiliamlcdmfm/restlet_client.html

Method: POST
Url: http://10.0.0.94:8000/?sdcApplicationId=samplerest
Content-Type: application/json
Body:
```json
{"_id": "chufe", "firstname": "Carsten", "lastname": "Hufe"}
{"_id": "ppan", "firstname": "Peter", "lastname": "Pan"}
{"_id": "jdoe", "firstname": "John", "lastname": "Doe"}
```
# Go to bash
```bash
cd /mapr/demo.mapr.com/demo/rest/2017-10-19-09
cat tmp file
```
```sql
# Goto Razor SQL:
select * from dfs.`/demo/rest/*/*.json`
```

## Oracle to MapR-FS (Streamsets)

1. Connect to Oracle with RazorSQL and show tables and data. "Oracle Employees"
jdbc:oracle:thin:@10.0.0.72:1521:MAPR EMPLOYEES/employees
2. Go to streamsets

### Create Oracle to FS as JSON files
-> Origin: JDBC Multitable
--> JDBC (JDBC connection string: jdbc:oracle:thin:@10.0.0.72:1521:MAPR - Use credentials: true - Per batch strategy: Switch tables - Fetch size: 1000 - Number of threads: 5)
--> Tables (Schema: EMPLOYEES, Table name pattern: %)
--> Credentials: (User: EMPLOYEES - Pass: employees)
--> Legacy Drivers (JDBC Driver class name: oracle.jdbc.driver.OracleDriver)
--> Advanced (Maximum Pool Size: 5)
-> Destination: MapR-FS
--> Hadoop FS (Hadoop FS URI: maprfs:/// - HDFS user: mapr)
--> Output files (File type: Text files - Files prefix: sdc-${sdc:id()} - Files suffix: json - Directory template: /demo/employees/json/${record:attribute('jdbc.tables')}/${YYYY()}-${MM()}-${DD()}-${hh()}
--> Data Format (Data Format: JSON - JSON Content: Multiple JSON objects - Charset: UTF-8)

## Oracle to MapR-DB (Streamsets)

1. Connect to Oracle with RazorSQL and show tables and data. "Oracle Employees"
jdbc:oracle:thin:@10.0.0.72:1521:MAPR EMPLOYEES/employees
2. Create table: https://10.0.0.94:8443/#tables
3. Go to streamsets

### Create Oracle to FS as JSON files
-> Origin: JDBC Multitable
--> JDBC (JDBC connection string: jdbc:oracle:thin:@10.0.0.72:1521:MAPR - Use credentials: true - Per batch strategy: Switch tables - Fetch size: 1000 - Number of threads: 5)
--> Tables (Schema: EMPLOYEES, Table name pattern: EMPLOYEES)
--> Credentials: (User: EMPLOYEES - Pass: employees)
--> Legacy Drivers (JDBC Driver class name: oracle.jdbc.driver.OracleDriver)
--> Advanced (Maximum Pool Size: 5)
-> Destination: MapR-DB JSON
--> MapR DB JSON (Table name: /demo/emp_tab - Row key: /EMP_NO - Insert API: InsertOrReplace)


## Flume REST and MapR-Streams (Flume)

1. Create new oracle table for test

CREATE TABLE new_emp_test
     AS SELECT EMP_NO, FIRST_NAME, LAST_NAME FROM EMPLOYEES;

2. sudo su - mapr

3. maprcli stream create -path /demo/flumerest -produceperm p -consumeperm p -topicperm p

flume-file

https://maprdocs.mapr.com/52/Flume/Flume_IntegrateWithMapRStreams_Example17.html


vi flumerest.properties

```
agent1.sources = source1
agent1.channels = channel1
agent1.sinks = sink1
agent1.sources.source1.channels = channel1
agent1.sinks.sink1.channel = channel1
agent1.sources.source1.type = http
agent1.sources.source1.port = 5140
agent1.sources.source1.channels = channel1
agent1.sources.source1.handler = org.apache.flume.source.http.JSONHandler
agent1.sources.source1.handler.nickname = random props
agent1.channels.channel1.type = memory
agent1.channels.channel1.capacity = 10000
agent1.channels.channel1.transactionCapacity = 1000
#agent1.sinks.sink1.type = logger
agent1.sinks.sink1.type = org.apache.flume.sink.kafka.KafkaSink
agent1.sinks.sink1.kafka.topic = /demo/flumerest:posts
agent1.sinks.sink1.flumeBatchSize = 1
```

/opt/mapr/flume/flume-1.7.0/bin/flume-ng agent -n agent1 -f flumerest.properties

Method: POST
Url: http://10.0.0.94:5140/
Content-Type: application/json
Body:
```json
[{
  "headers" : {
             "timestamp" : "434324343",
             "user" : "Carsten Hufe"
             },
  "body" : "Hello World"
}]

[{
  "headers" : {
             "timestamp" : "434324344",
             "user" : "John Doe"
             },
  "body" : "Demo content"
}]
```

vi flumelog.properties
```
agent1.sources = source1
agent1.channels = channel1
agent1.sinks = sink1
agent1.sources.source1.channels = channel1
agent1.sinks.sink1.channel = channel1
agent1.sources.source1.type = org.apache.flume.source.kafka.KafkaSource
agent1.sources.source1.kafka.topics = /demo/flumerest:posts
agent1.sources.source1.kafka.consumer.group.id = flume
agent1.sources.source1.batchSize = 20
agent1.sources.source1.batchDurationMillis = 1000
agent1.sinks.sink1.type = logger
agent1.channels.channel1.type = memory
agent1.channels.channel1.capacity = 10000
agent1.channels.channel1.transactionCapacity = 1000
```

/opt/mapr/flume/flume-1.7.0/bin/flume-ng agent -n agent1 -f flumelog.properties