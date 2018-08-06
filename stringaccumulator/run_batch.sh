#!/bin/bash

args="$1"
echo "args--$args"
jar="target/stringaccumulator-jar-with-dependencies.jar"
java -cp $jar com.richard.StringAccumulator "$args"
