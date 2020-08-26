#!/bin/bash
echo "Service ERP"
#export JAVA_OPTS="-Xmx1524m -XX:MaxPermSize=256m -Xss2048k"
export  RUN_PATH="./build/distributions/uberJar/"
export  RUN_APP="app.jar"
java -jar ${RUN_PATH}${RUN_APP}  ##--server.port=5555 ${JAVA_OPTS}
exit 0

