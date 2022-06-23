# JMH Test For SURSEN

## 编译
克隆代码后，进入项目根目录，执行如下命令进行编译
```shell
./mvnw clean package
```
将 `jmh-shardingsphere5` 下的 target 复制到某个目录并改名（例如 /opt/sursen/sursen-test），此模块用于压测。

将 `jmh-sursen` 下的 target 复制到某个目录并改名（例如 /opt/sursen/sursen-data），此模块用于数据的构造以及初始化。

将 `jmh-sursen` 下的 src/main/resources/下的 config-encrypt.yaml 与 prepare.sql 复制到某个目录，例如 /opt/sursen/config/

## 插入数据
进入目录 /opt/sursen/sursen-data
执行如下命令灌入测试数据：
```shell
## 此命令会灌入一些随机的测试数据
java -classpath 'dependency/*:jmh-sursen-1.0-SNAPSHOT.jar' -DconfigFile=/opt/sursen/config/config-encrypt.yaml -DtableName=tb_f_user10 -Dcommand=init com.sphereex.jmh.sursen.Application
## 此命令会准备一些用于压测的特殊的测试数据,前提是确保三张表的数据都已经灌好
java -classpath 'dependency/*:jmh-sursen-1.0-SNAPSHOT.jar' -DconfigFile=/opt/sursen/config/config-encrypt.yaml -DprepareSQL=/opt/sursen/config/prepare.sql -Dcommand=prepare com.sphereex.jmh.sursen.Application

# 如果想不用加密算法对 MySQL 灌数
# 将 datasource.properties 拷贝到 /opt/sursen/config/
java -classpath 'dependency/*:jmh-sursen-1.0-SNAPSHOT.jar' -DconfigFile=/opt/sursen/config/datasource.properties -DdatasourceType=mysql -DtableName=tb_f_user10 -Dcommand=init com.sphereex.jmh.sursen.Application

# 准备 MySQL 用非加密测试数据
java -classpath 'dependency/*:jmh-sursen-1.0-SNAPSHOT.jar' -DconfigFile=/opt/sursen/config/datasource.properties -DdatasourceType=mysql -DprepareSQL=/opt/sursen/config/prepare.sql -Dcommand=prepare com.sphereex.jmh.sursen.Application
```
此命令会根据 `config-encrypt.yaml` 下的 DataSource 在指定的数据库上删除并创建表 `tb_f_user10`, 而且会灌入 10w 条测试数据。
换成其他测试用的表名，也会删除、创建对应的表，灌入对应数据量的数据。
> 这里的表名皆为 PoC 文档中指定的表名

## 测试数据
进入目录 /opt/sursen/sursen-test
此命令会根据 `config-encrypt.yaml` 以及传入的参数进行压测
```shell
java -classpath 'dependency/*:jmh-shardingsphere5-1.0-SNAPSHOT.jar'  -Dshardingsphere.configurationFile=/opt/sursen/config/config-encrypt.yaml -DtableSize=10 org.openjdk.jmh.Main "com.sphereex.jmh.shardingsphere5.SurSenSelectUserBenchmark" -f 1 -i 5 -r 30 -t 100  -wi 1 -w 5

# 如果想不用加密算法对 MySQL 压测
# 将 datasource.properties 拷贝到 /opt/sursen/config/
java -classpath 'dependency/*:jmh-shardingsphere5-1.0-SNAPSHOT.jar'  -Dshardingsphere.configurationFile=/opt/sursen/config/datasource.properties -DdatasourceType=mysql -DtableSize=10 org.openjdk.jmh.Main "com.sphereex.jmh.shardingsphere5.SurSenSelectUserBenchmark" -f 1 -i 5 -r 30 -t 100  -wi 1 -w 5
```
```shell
# 可以通过 -h 的命令来查看 JMH 的测试参数，例如 -w 为 warmup 的时间
java -classpath 'dependency/*:jmh-shardingsphere5-1.0-SNAPSHOT.jar'  org.openjdk.jmh.Main -h

## SurSenSelectUnionBenchmark 对应如下 SQL
select a.name,  from tb_f_user10 a, tb_f_user_cert10 b, tb_f_user_contact10 c where a.id = b.user_id and a.id = c.user_id and b.cert_no = ? and a.name = ? and c.phone = ?;

## SurSenSelectUserBenchmark 对应如下 SQL
select * from tb_f_user10 where name = ?;

## SurSenSelectUserCertBenchmark 对应如下 SQL
select * from tb_f_user_cert10 where expire_date < '2033-12-30' ;

## SurSenSelectUserContactBenchmark 对应如下 SQL
select * from tb_f_user_contact10 where phone like '156%' order by phone limit 10;

## SurSenUpdateUserBenchmark 对应如下 SQL
update tb_f_user10 set name = 'xxx' where name = ?;

## SurSenUpdateUserCertBenchmark 对应如下 SQL
update tb_f_user_cert10 set cert_type = 'IDENTITY_CARD' where expire_date < '2033-12-30' ;

## SurSenUpdateUserContactBenchmark 对应如下 SQL
update tb_f_user_contact10 set area_code = '86' where phone like '156%' ;
```
