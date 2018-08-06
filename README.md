# stringaccumlator
This is a java application to demonstrate parsing of string and extract numbers and sum them up

Requirement
------------
- JDK 1.8 or above
- Maven 3

Build
------
- at root dir, cd stringaccumulator 
- mvn clean compile test package

Run
----
- at root dir, exec ./run_batch.sh <inputstr>
- e.g. ./run_batch.sh "1,2,3" and will give you 6
- if you want to pass newline, you should use $'<your args>'
- e.g. ./run_batch.sh $'//:|,\n1:2\n3,4,5" will give you 15

log
----
There is a log folder under root for log output.
