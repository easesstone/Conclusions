```bash
# 1，去除server.properties 中包含'#'的内容并且去除空行，最后排序
grep -v '#' server.properties   | grep -v "^$"| sort


```
