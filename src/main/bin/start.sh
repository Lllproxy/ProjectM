#!/bin/sh  
  
moduleName="traffic-signal-dataprocess-0.0.1"  
pidPath="/var/run/$moduleName-tpid"  
  
rm -f $pidPath  
  
nohup java -jar ./$moduleName.jar -server -Xms1024m -Xmx2048m -Xss256k > ./run.log 2>&1 &  
  
echo $! > $pidPath