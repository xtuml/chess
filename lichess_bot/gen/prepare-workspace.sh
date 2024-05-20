#!/bin/bash
DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
TARGET=${DIR}/../..
MVN_HOME=${HOME}/.m2/repository
CIERA_VERSION=2.6.4-SNAPSHOT
TMP_LOC=$(mktemp -d)
unzip -q -d ${TMP_LOC} ${MVN_HOME}/io/ciera/runtime/${CIERA_VERSION}/runtime-${CIERA_VERSION}.jar
${BPHOME}/bridgepoint -nosplash -data ${WORKSPACE} -application org.eclipse.cdt.managedbuilder.core.headlessbuild -importAll ${TMP_LOC}
${BPHOME}/bridgepoint -nosplash -data ${WORKSPACE} -application org.eclipse.cdt.managedbuilder.core.headlessbuild -importAll ${TARGET}
