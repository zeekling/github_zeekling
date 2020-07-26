#!/usr/bin/env bash

rm -rf ./build

if [ ! -d "./build" ]
then
	mkdir build
fi

mvn clean
mvn install -Dmaven.test.skip=true

cp -r ./target/github_zeekling-1.0.jar ./build/
cp -r ./start.sh ./build/
cp -r ./src/main/resources/blog.properties ./build/
cp -r ./src/main/resources/log4j.properties ./build/
cp -r ./target/lib ./build/

