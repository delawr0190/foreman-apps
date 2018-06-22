#!/bin/bash

PICKAXE_HOME=$(cd `dirname $0`/..; pwd)

# Configures Java
setup_java() {
    if [ -z "$JAVA_HOME" ]; then
        echo "\$JAVA_HOME must be set!"
        exit 1
    fi

    # JVM options
    JVM_OPTS=$(grep "^-" "$PICKAXE_HOME"/conf/jvm.options | tr '\n' ' ')

    # JVM classpath
    JVM_CLASSPATH="$PICKAXE_HOME"/lib/*

    # JVM parameters
    JVM_PARAMS="-Dlogback.configurationFile=$PICKAXE_HOME/etc/logback.xml"
    JVM_PARAMS+=" -DLOG_LOCATION=$PICKAXE_HOME/logs"

    # JVM command line arguments
    JVM_COMMAND_LINE="-c $PICKAXE_HOME/conf/pickaxe.yml"
}

# Starts the application
start() {
    if pgrep -f "java.*pickaxe" > /dev/null
    then
        echo "Pickaxe is already running..."
    else
        setup_java

        echo -n "Starting pickaxe..."

        exec "$JAVA_HOME"/bin/java \
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
        *)
            echo "Invalid argument provided: $1"
            exit 1
            ;;
    esac
else
    echo "No arguments provided (expected 'start', 'stop', or 'restart')!"
fi