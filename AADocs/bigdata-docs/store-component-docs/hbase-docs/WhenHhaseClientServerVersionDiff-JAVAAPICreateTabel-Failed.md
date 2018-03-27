Admin admin = HBaseHelper.getHBaseConnection().getAdmin();
admin.createTable(tableDescriptor);

As above:
when the hbase java api's hbase-version ,
and the cluster hhbase version is different,
it will stuct at admin.createTabel(tableDEscriptor)


