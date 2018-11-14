#!/bin/bash

PICKAXE_HOME=$(cd `dirname $0`/..; pwd)

# Configures Java
setup_java() {
    JAVA=$(command -v java)
    if [ -z "$JAVA" ]; then
        if [ -x "$JAVA_HOME/bin/java" ]; then
            JAVA="$JAVA_HOME/bin/java"
        fi

        if [ ! -x "$JAVA" ]; then
            echo "Couldn't find java - set JAVA_HOME or add java to the path"
            exit 1
        fi
    fi

    # JVM options
    JVM_OPTS=$(grep "^-" "$PICKAXE_HOME"/conf/jvm.options | tr '\n' ' ')

    # JVM classpath
    JVM_CLASSPATH="$PICKAXE_HOME"/lib/*

    # JVM parameters
    JVM_PARAMS="-Dlogback.configurationFile=$PICKAXE_HOME/etc/logback.xml"
    JVM_PARAMS+=" -DLOG_LOCATION=$PICKAXE_HOME/logs"
    JVM_PARAMS+=" -Dio.netty.tryReflectionSetAccessible=false"

    # JVM command line arguments
    JVM_COMMAND_LINE="-c $PICKAXE_HOME/conf/pickaxe.yml"
}

# Application status
status() {
    if pgrep -f "java.*pickaxe" > /dev/null
    then
        echo "Pickaxe is running..."
    else
        echo "Pickaxe not running..."
    fi
}

# Starts the application
start() {
    if pgrep -f "java.*pickaxe" > /dev/null
    then
        echo "Pickaxe is already running..."
    else
        setup_java

        echo -n "Starting pickaxe..."

        exec $JAVA \
            $JVM_OPTS \
            $JVM_PARAMS \
            -cp "$JVM_CLASSPATH" \
            mn.foreman.pickaxe.Main ${JVM_COMMAND_LINE} &

        echo "started(`pgrep -f 'java.*pickaxe'`)"
    fi
}

# Stops the application
stop() {
    echo "Stopping pickaxe..."
    pkill -f 'java.*pickaxe.Main'
}

if [ ! -z $1 ]; then
    case $1 in
        "start")
            start
            ;;
        "stop")
            stop
            ;;
        "restart")
            stop
            sleep 1
            start
            ;;
        "status")
            status
            ;;
        *)
            echo "Invalid argument provided: $1"
            exit 1
            ;;
    esac
else
    echo "No arguments provided (expected 'start', 'stop', or 'restart')!"
fi
