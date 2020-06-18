#!/bin/bash
# For simplified running test
# usage: ./run-test.sh [tags] [env]
# example: ./run-test.sh ~@ignore staging

tags=$1
mvn clean test -Dcucumber.options="--tags $tags" -Dtest=TestRunner