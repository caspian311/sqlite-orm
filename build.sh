#!/bin/bash

pushd ./sqlite-orm

mvn clean package
cp target/sqlite-orm*.jar ..

popd

 adb shell am instrument -w net.todd.sqliteorm/android.test.InstrumentationTestRunner
