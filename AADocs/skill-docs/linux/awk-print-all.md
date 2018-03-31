
### 3，awk 打印每一个值
```
for host in $(echo "hello,niya" | awk -F ","  '{for(i=1;i<=NF;++i) print $i}');do
  echo $host
done
```
