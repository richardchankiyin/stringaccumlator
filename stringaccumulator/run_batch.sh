#!/bin/bash

args="$1"
echo "args--$args"
jar="target/stringaccumulator-jar-with-dependencies.jar"
jvmargs="-Xloggc:log/`date +%F_%H-%M-%S`-gc.log -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintTenuringDistribution -XX:+PrintGCCause
-XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=10 -XX:GCLogFileSize=5M"

if [ ! -d "log" ]; then mkdir log; fi

java -cp $jar $jvmargs com.richard.StringAccumulator "$args"
