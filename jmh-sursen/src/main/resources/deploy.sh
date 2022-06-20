cd /Users/nianjun/Work/Workspace/database-jmh || exit
mvn clean package
scp -r jmh-shardingsphere5/target root@117.48.121.22:/root/jmh-test/shardingsphere5/
scp -r jmh-sursen/target root@117.48.121.22:/root/jmh-test/sursen/