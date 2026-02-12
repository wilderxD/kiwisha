#!/bin/sh
# Simplified mvnw for shell
set -e
basedir=$(dirname "$0")
case "$(uname)" in
  *CYGWIN*|*MINGW*|*MSYS*) basedir=$(cygpath -w "$basedir");;
esac
WRAPPER_JAR="$basedir/.mvn/wrapper/maven-wrapper.jar"
WRAPPER_LAUNCHER="org.apache.maven.wrapper.MavenWrapperMain"
if [ ! -f "$WRAPPER_JAR" ]; then
    echo "Downloading Maven Wrapper..."
    # Simplified download logic
    curl -o "$WRAPPER_JAR" "https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.2.0/maven-wrapper-3.2.0.jar"
fi
exec java -Dmaven.multiModuleProjectDirectory="$basedir" -classpath "$WRAPPER_JAR" "$WRAPPER_LAUNCHER" "$@"
