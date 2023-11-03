## JMH Customizable
该模块可根据传入的 SQL 文件为测试用例，动态更新测试内容。

### 构建
进入项目根目录，执行构建命令：
```
./mvnw clean install -DskipTests
```

### 使用
#### 1. 准备 SQL 文件
准备好制品文件后，可以将提前准备好的 SQL 文件 `sysbench.sql` 放入 jmh-customizable.jar 文件同级目录即可，内容可为多条 SQL 语句，例如：
```
use sysbench;
select * from sbtest1 where id = 100;
delete from sbtest1 where id = 100;
```

#### 2. 准备配置文件
以 mysql 为例，在 jmh-customizable.jar 文件同级目录下，创建配置文件 `mysql.properties`，配置文件内容如下：
```
driverClassName=com.mysql.cj.jdbc.Driver
url=jdbc:mysql://127.0.0.1:3306/sysbench?useServerPrepStmts=true&cachePrepStmts=true&prepStmtCacheSize=8192&prepStmtCacheSqlLimit=2048&useLocalSessionState=true&rewriteBatchedStatements=true&cacheResultSetMetadata=false&cacheServerConfiguration=true&elideSetAutoCommits=true&maintainTimeStats=false&netTimeoutForStreamingResults=0&tinyInt1isBit=false&useSSL=false&zeroDateTimeBehavior=round
username=root
password=root
```

以 ShardingSphere 为例，创建 shardingsphere.properties，配置文件内容如下：
```
driverClassName=org.apache.shardingsphere.driver.ShardingSphereDriver
url=jdbc:shardingsphere:classpath:encrypt.yaml
username=root
password=root
```
然后在 classpath 的目录中添加 encrypt.yaml 文件，内容如下：
```
schemaName: benchmarksql
dataSources:
  benchmarksql:
    dataSourceClassName: com.zaxxer.hikari.HikariDataSource
    driverClassName: com.mysql.cj.jdbc.Driver
    jdbcUrl: jdbc:mysql://127.0.0.1:3306/benchmarksql?useServerPrepStmts=true&cachePrepStmts=true&prepStmtCacheSize=8192&prepStmtCacheSqlLimit=2048&useLocalSessionState=true&rewriteBatchedStatements=true&cacheResultSetMetadata=false&cacheServerConfiguration=true&elideSetAutoCommits=true&maintainTimeStats=false&netTimeoutForStreamingResults=0&tinyInt1isBit=false&useSSL=false&zeroDateTimeBehavior=round
    username: root
    password: passw0rd
    idleTimeoutMilliseconds: 60000
    maxLifetimeMilliseconds: 1800000
    maxPoolSize: 30
    minPoolSize: 1

rules:
  - !ENCRYPT
    encryptors:
      md5_encryptor:
        type: MD5
    tables:
      bmsql_config:
        columns:
          cfg_value:
            cipher:
              name: cfg_value_cipher
              encryptorName: md5_encryptor
            plain:
              name: cfg_value
              queryWithPlain: false

  - !SINGLE
    tables:
      - "*.*"
```

#### 3. 执行测试
当前 jmh-customizable.jar 模块支持的参数如下：
script : SQL 文件路径，即上面例子中准备的 sysbench.sql
conf: 配置文件路径，即上面例子中准备的 mysql.properties
countInterval: 每执行一定数量 SQL 后输出一条当前已经执行的总量，默认值为 10000

一条完整的执行命令如下：
```
java -classpath 'dependency/*:jmh-customizable-1.0-SNAPSHOT.jar' -DintervalCount=500 -DintervalCount=2 -Dconf=mysql.properties -Dscript=sysbench.sql org.openjdk.jmh.Main "com.sphereex.jmh.customizable.CustomizableBenchmark"
```
