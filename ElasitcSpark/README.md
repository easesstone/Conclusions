## 说明
### 提交方式
最终打包后，在target目录下会有一个lib 目录
jars=""
cd ${BASE_DIR}/lib
for jar in $(ls | grep -v scala | grep -v hadoop);do  jars="${jars}/home/ldl/sparkdemo/0912/ElasitcSparkDemo/lib/$jar "; done
spark-submit --class com.sydney.dream.elasticspark.Elastic2Demo --master yarn --deploy-mode client --executor-memory 1G --num-executors 2  /home/ldl/sparkdemo/0912/ElasitcSparkDemo/ElasitcSparkDemo.jar  --jars $jars
