# CUBA Platform

[![license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0)
[![Build Status](https://travis-ci.org/cuba-platform/cuba.svg?branch=master)](https://travis-ci.org/cuba-platform/cuba)
[![Join the chat at https://gitter.im/cuba-platform/cuba](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/cuba-platform/cuba)

[CUBA Platform](https://www.cuba-platform.com) is a high level framework for rapid development of enterprise applications with rich web interface.

The simplest way to start using the platform is to [download](https://www.cuba-platform.com/download) CUBA Studio and create a new project in it. A released version of the platform will be downloaded automatically from the artifact repository.

You can also build a snapshot version of the platform from the source code and use it in your project.

## Building from Source

In order to build the platform from source, you need to install the following:
* Java 8 Development Kit (JDK)
* [Gradle](https://gradle.org) (tested on 3.1, but newer versions may also work)
* [CUBA Gradle Plugin](https://github.com/cuba-platform/cuba-gradle-plugin)

Let's assume that you have cloned CUBA Gradle Plugin and CUBA into the following directories:
```
work/
    cuba/
    cuba-gradle-plugin/
```

Open terminal in the `work` directory and run the following command to build and install the plugin into your local Maven repository (`~/.m2`):
```
cd cuba-gradle-plugin
gradlew install
```

After that, go to the CUBA directory and build and install it with the same command:
```
cd ../cuba
gradlew install
```

## Using Snapshot Version

Edit the `build.gradle` file of your project. Change the `ext.cubaVersion` property and add `mavenLocal()` to the `repositories` section, for example:
```
buildscript {
    ext.cubaVersion = '6.7-SNAPSHOT'
    repositories {
        mavenLocal()
        maven { ...
```
That's all. Now you can generate IDE project files and build and deploy your application based on the snapshot version of the platform from your local repository:
 ```
 gradlew idea
 gradlew deploy
 ```

## Third-party dependencies

The platform uses a number of forked third-party libraries. They can be found in the following source code repositories:

* [eclipselink](https://github.com/cuba-platform/eclipselink)
* [vaadin](https://github.com/cuba-platform/vaadin)
* [vaadin-dragdroplayouts](https://github.com/cuba-platform/vaadin-dragdroplayouts)
* [vaadin-aceeditor](https://github.com/cuba-platform/vaadin-aceeditor)
* [swingx-core](https://github.com/cuba-platform/swingx-core)

All dependencies are also located in our artifacts repository, so you don't have to build them from sources in order to build and use the platform.
