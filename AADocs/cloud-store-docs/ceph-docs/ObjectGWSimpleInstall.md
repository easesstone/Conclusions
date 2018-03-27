# 对象存储配置
## 对象网关安装配置（启动着的客户端接口实例，类似于Spark JDBC 中的thriftserver 实例，实现了JDBC 方式进行访问数据）
### 1，安装对象网关（可以理解为客户端实例）
（一共有4个节点： s110， s11, s112, s113, 其中s110 安装了ceph-deploy）  
在s110上执行： ceph-deploy  install --rgw s111 s112 s113  
ceph-deploy --overwrite rgw create s157 s158 s159  
```
data@s157:~/my-cluster$ ceph-deploy --overwrite rgw create s157 s158 s159  
[ceph_deploy.conf][DEBUG ] found configuration file at: /home/data/.cephdeploy.conf
[ceph_deploy.cli][INFO  ] Invoked (1.5.39): /usr/bin/ceph-deploy --overwrite rgw create s157 s158 s159
[ceph_deploy.cli][INFO  ] ceph-deploy options:
[ceph_deploy.cli][INFO  ]  username                      : None
[ceph_deploy.cli][INFO  ]  verbose                       : False
[ceph_deploy.cli][INFO  ]  rgw                           : [('s157', 'rgw.s157'), ('s158', 'rgw.s158'), ('s159', 'rgw.s159')]
[ceph_deploy.cli][INFO  ]  overwrite_conf                : True
[ceph_deploy.cli][INFO  ]  subcommand                    : create
[ceph_deploy.cli][INFO  ]  quiet                         : False
[ceph_deploy.cli][INFO  ]  cd_conf                       : <ceph_deploy.conf.cephdeploy.Conf instance at 0x7f3250d13680>
[ceph_deploy.cli][INFO  ]  cluster                       : ceph
[ceph_deploy.cli][INFO  ]  func                          : <function rgw at 0x7f32515e20c8>
[ceph_deploy.cli][INFO  ]  ceph_conf                     : None
[ceph_deploy.cli][INFO  ]  default_release               : False
[ceph_deploy.rgw][DEBUG ] Deploying rgw, cluster ceph hosts s157:rgw.s157 s158:rgw.s158 s159:rgw.s159
[s157][DEBUG ] connection detected need for sudo
[s157][DEBUG ] connected to host: s157 
[s157][DEBUG ] detect platform information from remote host
[s157][DEBUG ] detect machine type
[ceph_deploy.rgw][INFO  ] Distro info: Ubuntu 16.04 xenial
[ceph_deploy.rgw][DEBUG ] remote host will use systemd
[ceph_deploy.rgw][DEBUG ] deploying rgw bootstrap to s157
[s157][DEBUG ] write cluster configuration to /etc/ceph/{cluster}.conf
[s157][DEBUG ] create path recursively if it doesn't exist
[s157][INFO  ] Running command: sudo ceph --cluster ceph --name client.bootstrap-rgw --keyring /var/lib/ceph/bootstrap-rgw/ceph.keyring auth get-or-create client.rgw.s157 osd allow rwx mon allow rw -o /var/lib/ceph/radosgw/ceph-rgw.s157/keyring
[s157][INFO  ] Running command: sudo systemctl enable ceph-radosgw@rgw.s157
[s157][INFO  ] Running command: sudo systemctl start ceph-radosgw@rgw.s157
[s157][INFO  ] Running command: sudo systemctl enable ceph.target
[ceph_deploy.rgw][INFO  ] The Ceph Object Gateway (RGW) is now running on host s157 and default port 7480
[s158][DEBUG ] connection detected need for sudo
[s158][DEBUG ] connected to host: s158 
[s158][DEBUG ] detect platform information from remote host
[s158][DEBUG ] detect machine type
[ceph_deploy.rgw][INFO  ] Distro info: Ubuntu 16.04 xenial
[ceph_deploy.rgw][DEBUG ] remote host will use systemd
[ceph_deploy.rgw][DEBUG ] deploying rgw bootstrap to s158
[s158][DEBUG ] write cluster configuration to /etc/ceph/{cluster}.conf
[s158][DEBUG ] create path recursively if it doesn't exist
[s158][INFO  ] Running command: sudo ceph --cluster ceph --name client.bootstrap-rgw --keyring /var/lib/ceph/bootstrap-rgw/ceph.keyring auth get-or-create client.rgw.s158 osd allow rwx mon allow rw -o /var/lib/ceph/radosgw/ceph-rgw.s158/keyring
[s158][INFO  ] Running command: sudo systemctl enable ceph-radosgw@rgw.s158
[s158][INFO  ] Running command: sudo systemctl start ceph-radosgw@rgw.s158
[s158][INFO  ] Running command: sudo systemctl enable ceph.target
[ceph_deploy.rgw][INFO  ] The Ceph Object Gateway (RGW) is now running on host s158 and default port 7480
[s159][DEBUG ] connection detected need for sudo
[s159][DEBUG ] connected to host: s159 
[s159][DEBUG ] detect platform information from remote host
[s159][DEBUG ] detect machine type
[ceph_deploy.rgw][INFO  ] Distro info: Ubuntu 16.04 xenial
[ceph_deploy.rgw][DEBUG ] remote host will use systemd
[ceph_deploy.rgw][DEBUG ] deploying rgw bootstrap to s159
[s159][DEBUG ] write cluster configuration to /etc/ceph/{cluster}.conf
[s159][DEBUG ] create path recursively if it doesn't exist
[s159][INFO  ] Running command: sudo ceph --cluster ceph --name client.bootstrap-rgw 
--keyring /var/lib/ceph/bootstrap-rgw/ceph.keyring auth get-or-create 
client.rgw.s159 osd allow rwx mon allow rw -o /var/lib/ceph/radosgw/ceph-rgw.s159/keyring
Exception KeyError: (7,) in <function remove at 0x7f32501b0c08> ignored
[s159][INFO  ] Running command: sudo systemctl enable ceph-radosgw@rgw.s159
[s159][INFO  ] Running command: sudo systemctl start ceph-radosgw@rgw.s159
[s159][INFO  ] Running command: sudo systemctl enable ceph.target
[ceph_deploy.rgw][INFO  ] The Ceph Object Gateway (RGW) is now running on host s159 and default port 7480

```
### 2，启动其中一个对象网关实例（比如启动s111的对象网关实例）
ceph-deploy rgw create  s111
### 3,在浏览器打开，可以看到类似如下的显示。
地址类似如此： http://client-node:7480
```
<?xml version="1.0" encoding="UTF-8"?>
<ListAllMyBucketsResult xmlns="http://s3.amazonaws.com/doc/2006-03-01/">
       <Owner>
       <ID>anonymous</ID>
       <DisplayName></DisplayName>
   </Owner>
   <Buckets>
   </Buckets>
</ListAllMyBucketsResult>
```

## 对象网关的使用
### 1，为S3创建RADOSGW用户：
```
root@s111:/home/data# sudo radosgw-admin user create --uid="testuser" --display-name="First User"
{
    "user_id": "testuser",
    "display_name": "First User",
    "email": "",
    "suspended": 0,
    "max_buckets": 1000,
    "auid": 0,
    "subusers": [],
    "keys": [
        {
            "user": "testuser",
            "access_key": "N7VFXQKU9HIZXN8L0D2P",
            "secret_key": "2jxi8uwcJqbJANQz91GgLanh8E06Bu1DLllirsWe"
        }
    ],
    "swift_keys": [],
    "caps": [],
    "op_mask": "read, write, delete",
    "default_placement": "",
    "placement_tags": [],
    "bucket_quota": {
        "enabled": false,
        "check_on_raw": false,
        "max_size": -1,
        "max_size_kb": 0,
        "max_objects": -1
    },
    "user_quota": {
        "enabled": false,
        "check_on_raw": false,
        "max_size": -1,
        "max_size_kb": 0,
        "max_objects": -1
    },
    "temp_url_keys": [],
    "type": "rgw"
}



```
### 2,创建SWIFT 用户
```
1,创建子用户，
root@s111:/home/data# sudo radosgw-admin subuser create --uid=testuser --subuser=testuser:swift --access=full
{
    "user_id": "testuser",
    "display_name": "First User",
    "email": "",
    "suspended": 0,
    "max_buckets": 1000,
    "auid": 0,
    "subusers": [
        {
            "id": "testuser:swift",
            "permissions": "full-control"
        }
    ],
    "keys": [
        {
            "user": "testuser",
            "access_key": "N7VFXQKU9HIZXN8L0D2P",
            "secret_key": "2jxi8uwcJqbJANQz91GgLanh8E06Bu1DLllirsWe"
        }
    ],
    "swift_keys": [
        {
            "user": "testuser:swift",
            "secret_key": "9t8QMaTICPbTiI5L4qalv1CPGOPG0j7cWroLt1VE"
        }
    ],
    "caps": [],
    "op_mask": "read, write, delete",
    "default_placement": "",
    "placement_tags": [],
    "bucket_quota": {
        "enabled": false,
        "check_on_raw": false,
        "max_size": -1,
        "max_size_kb": 0,
        "max_objects": -1
    },
    "user_quota": {
        "enabled": false,
        "check_on_raw": false,
        "max_size": -1,
        "max_size_kb": 0,
        "max_objects": -1
    },
    "temp_url_keys": [],
    "type": "rgw"
}

2，创建secret key。

root@s111:/home/data# sudo radosgw-admin key create --subuser=testuser:swift --key-type=swift --gen-secret
{
    "user_id": "testuser",
    "display_name": "First User",
    "email": "",
    "suspended": 0,
    "max_buckets": 1000,
    "auid": 0,
    "subusers": [
        {
            "id": "testuser:swift",
            "permissions": "full-control"
        }
    ],
    "keys": [
        {
            "user": "testuser",
            "access_key": "N7VFXQKU9HIZXN8L0D2P",
            "secret_key": "2jxi8uwcJqbJANQz91GgLanh8E06Bu1DLllirsWe"
        }
    ],
    "swift_keys": [
        {
            "user": "testuser:swift",
            "secret_key": "TRDdEgdr7ztnerWZ3prywY5wYD1foHnYNyDNzQWC"
        }
    ],
    "caps": [],
    "op_mask": "read, write, delete",
    "default_placement": "",
    "placement_tags": [],
    "bucket_quota": {
        "enabled": false,
        "check_on_raw": false,
        "max_size": -1,
        "max_size_kb": 0,
        "max_objects": -1
    },
    "user_quota": {
        "enabled": false,
        "check_on_raw": false,
        "max_size": -1,
        "max_size_kb": 0,
        "max_objects": -1
    },
    "temp_url_keys": [],
    "type": "rgw"
}

```

## 验证s3访问
```
创建s3 测试的python 代码：s3test.py
（代码严格执行python 对齐语法， 需要进行安装： sudo yum install python-boto）
import boto
import boto.s3.connection

access_key = 'N7VFXQKU9HIZXN8L0D2P'
secret_key = '2jxi8uwcJqbJANQz91GgLanh8E06Bu1DLllirsWe'
conn = boto.connect_s3(
    aws_access_key_id = access_key,
    aws_secret_access_key = secret_key,
    host = 's111', port = 7480,
    is_secure=False, calling_format = boto.s3.connection.OrdinaryCallingFormat(),
    )

bucket = conn.create_bucket('my-new-bucket')
for bucket in conn.get_all_buckets():
    print "{name}".format(
        name = bucket.name,
        created = bucket.creation_date,
    )

执行python s3test.py
data@s111:~/data-cluster$ python s3test.py 
my-new-bucket


```

## 验证swift 访问
```
1,安装相关python 依赖：
sudo apt-get install python-setuptools  
sudo apt-get install python-pip
sudo pip install --upgrade setuptools
sudo pip install --upgrade python-swiftclient

2, 验证用户：
swift -A http://{IP ADDRESS}:{port}/auth/1.0 -U testuser:swift -K '{swift_secret_key}' list
例子：
data@s111:~/data-cluster$ swift -A http://172.18.18.148:7480/auth/1.0 -U testuser:swift -K 'TRDdEgdr7ztnerWZ3prywY5wYD1foHnYNyDNzQWC' list
my-new-bucket

```