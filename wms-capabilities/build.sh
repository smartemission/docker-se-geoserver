#!/usr/bin/env bash
pushd src
javac -cp ../lib/javax.servlet-api-3.0.1.jar nl/pdok/filter/WmsCapabilitiesFilter.java
popd

