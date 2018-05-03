#!/bin/bash
#
# run.sh: helps executing the examples by setting up the Java CLASSPATH.
#

# get the current directory
# set -x
bin=`dirname "$0"`
bin=`cd "$bin" > /dev/null; pwd`

if [[ $# == 0 ]]; then
    echo "usage: $(basename $0) <example-name>"
    exit 1
fi 

MVN="mvn"
if [ "$MAVEN_HOME" != "" ]; then
    MVN=${MAVEN_HOME}/bin/mvn
fi

# add classes first, triggers log4j.properties priority
echo "Compiling, please wait..."
${MVN} -f ${bin}/../pom.xml clean compile  

${MVN} -f "${bin}/../pom.xml" dependency:build-classpath -Dmdep.outputFile="${bin}/deps.jars"

MAVEN_JARS=`cat ${bin}/deps.jars`
rm -rf ${bin}/deps.jars
CLASSPATH=${CLASSPATH}:${MAVEN_JARS}
if [ "${CLASSPATH}" != "" ];then
    CLASSPATH=.:${CLASSPATH}:${bin}/../target/classes
else
    CLASSPATH=${bin}/../target/classes
fi

JAVA=$JAVA_HOME/bin/java
JAVA_HEAP_MAX=-Xmx512m
echo "=========================================================="
echo "=========================================================="
echo "=====================开始执行程序 $@======================"
echo "=========================================================="
echo "=========================================================="
"$JAVA" $JAVA_HEAP_MAX -classpath "$CLASSPATH" "$@"


#set +x


