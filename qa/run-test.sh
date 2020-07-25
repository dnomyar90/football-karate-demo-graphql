#!/bin/bash
# For simplified running test
# usage: ./run-test.sh [tags] [env]
# example: ./run-test.sh ~@ignore staging

tags=$1
mvn clean test -Dkarate.options="--tags $tags" -Dtest=TestRunner