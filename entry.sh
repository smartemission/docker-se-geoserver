#!/usr/bin/env bash

cd /opt/geoserver/templates
for f in $(find ./ -regex '.*\.xml'); do envsubst < $f > "/opt/geoserver/data_dir/$f"; done

# Need to propagate ENV vars so execute in this env
. ${CATALINA_HOME}/bin/catalina.sh run
