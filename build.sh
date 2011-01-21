#!/bin/bash

ANDROID_SDK_PATH=~/.apps/android-sdk-linux_86/tools

echo Cleaning out the old build
rm -f *.jar *.apk

pushd ./sqlite-orm
mvn clean
popd

pushd sqlite-orm-androidtest
ant clean
popd

echo Building the sqlite-orm-???.jar file
pushd ./sqlite-orm
mvn package
cp target/sqlite-orm*.jar ../sqlite-orm-androidtest/libs
popd

echo Building test application
pushd ./sqlite-orm-androidtest
ant debug
cp bin/*.apk ..
popd

device=`$ANDROID_SDK_PATH/android list avd | grep Name | awk '{print $2}' | head -1`

echo Starting emulator: $device
$ANDROID_SDK_PATH/emulator -noskin -avd $device &
emulator_process_id=$!
echo Emulator process id: $emulator_process_id

echo Waiting for device to start...
$ANDROID_SDK_PATH/adb wait-for-device

echo Installing test application on device
$ANDROID_SDK_PATH/adb install sqlite-orm-test-debug.apk

echo Device is up, running tests
$ANDROID_SDK_PATH/adb shell am instrument -w net.todd.sqliteorm/android.test.InstrumentationTestRunner

echo Removing test application from device
$ANDROID_SDK_PATH/adb uninstall net.todd.sqliteorm

echo Killing off emulator...
kill $emulator_process_id
