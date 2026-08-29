#!/usr/bin/env sh

##############################################################################
##
##  Gradle start up script for UN*X
##
##############################################################################

# Attempt to set APP_HOME
# Resolve links - $0 may be a softlink
PRG="$0"

while [ -h "$PRG" ]; do
    ls=`ls -ld "$PRG"`
    link=`expr "$ls" : '.*-> \(.*\)$'`
    if expr "$link" : '/.*' > /dev/null; then
        PRG="$link"
    else
        PRG=`dirname "$PRG"`/"$link"
    fi
done

PRGDIR=`dirname "$PRG"`
BASE_DIR=`cd "$PRGDIR" && pwd`

# Define default JVM options
DEFAULT_JVM_OPTS='"-Xmx64m" "-Xms64m"'

# Collect all arguments passed to the script
APP_ARGS="$@"

exec "$BASE_DIR/gradle/wrapper/gradle-wrapper.jar" $DEFAULT_JVM_OPTS $APP_ARGS
