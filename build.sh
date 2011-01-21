#!/bin/bash

pushd ./sqlite-orm

mvn clean package
cp target/sqlite-orm*.jar ..

popd
