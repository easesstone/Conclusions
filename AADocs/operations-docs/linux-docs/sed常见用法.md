 sed -i "s#ha_zookeeper_quorum#${ZK_LISTS}#g"  yarn-site.xml  （这种情况，比较好一点）
 sed -i "s/Ha_zookeeper_quorum/${ZK_LISTS}/g"  yarn-site.xml(这种情况下，可能会报错如果zkList里面含有/)
 sed -n '2p' filename 获取文件中的第二行。 
