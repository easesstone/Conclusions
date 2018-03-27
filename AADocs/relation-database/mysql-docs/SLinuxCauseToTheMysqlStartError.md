错误日记：
解决方式：关闭并且禁用SLinux
```
2017-12-19T03:38:07.425026Z 0 [Warning] TIMESTAMP with implicit DEFAULT value is deprecated. Please use --explicit_defaults_for_timestamp server option (see documentation for more details).
2017-12-19T03:38:07.428552Z 0 [Note] /usr/sbin/mysqld (mysqld 5.7.19) starting as process 10503 ...
2017-12-19T03:38:07.436041Z 0 [Note] InnoDB: PUNCH HOLE support available
2017-12-19T03:38:07.436082Z 0 [Note] InnoDB: Mutexes and rw_locks use GCC atomic builtins
2017-12-19T03:38:07.436095Z 0 [Note] InnoDB: Uses event mutexes
2017-12-19T03:38:07.436107Z 0 [Note] InnoDB: GCC builtin __sync_synchronize() is used for memory barrier
2017-12-19T03:38:07.436116Z 0 [Note] InnoDB: Compressed tables use zlib 1.2.3
2017-12-19T03:38:07.436125Z 0 [Note] InnoDB: Using Linux native AIO
2017-12-19T03:38:07.436614Z 0 [Note] InnoDB: Number of pools: 1
2017-12-19T03:38:07.436813Z 0 [Note] InnoDB: Using CPU crc32 instructions
2017-12-19T03:38:07.440038Z 0 [Note] InnoDB: Initializing buffer pool, total size = 128M, instances = 1, chunk size = 128M
2017-12-19T03:38:07.454972Z 0 [Note] InnoDB: Completed initialization of buffer pool
2017-12-19T03:38:07.458597Z 0 [Note] InnoDB: If the mysqld execution user is authorized, page cleaner thread priority can be changed. See the man page of setpriority().
2017-12-19T03:38:07.468673Z 0 [ERROR] InnoDB: The innodb_system data file 'ibdata1' must be writable
2017-12-19T03:38:07.468710Z 0 [ERROR] InnoDB: The innodb_system data file 'ibdata1' must be writable
2017-12-19T03:38:07.468722Z 0 [ERROR] InnoDB: Plugin initialization aborted with error Generic error
2017-12-19T03:38:08.069643Z 0 [ERROR] Plugin 'InnoDB' init function returned error.
2017-12-19T03:38:08.069678Z 0 [ERROR] Plugin 'InnoDB' registration as a STORAGE ENGINE failed.
2017-12-19T03:38:08.069691Z 0 [ERROR] Failed to initialize plugins.
2017-12-19T03:38:08.069704Z 0 [ERROR] Aborting

```