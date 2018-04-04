```bash
#!/bin/bash

echo "n
p
1


w
" | fdisk /dev/sdc && mkfs -t /dev/sdc1
##注意：1和w之间是两个空行。
```
