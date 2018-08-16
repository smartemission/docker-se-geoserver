FROM kartoza/geoserver:2.12.1
LABEL maintainer="Gerwin Hulsteijn, Just van den Broecke"

RUN \
  apt-get update \
  && apt-get -y install gettext-base \
  && apt-get clean \
  && rm -rf /var/lib/apt/lists/*

ENV TZ Europe/Amsterdam

# OVERRULE, see https://github.com/kartoza/docker-geoserver/blob/master/Dockerfile
# Original:
#ENV GEOSERVER_OPTS "-Djava.awt.headless=true -server -Xms2G -Xmx4G -Xrs -XX:PerfDataSamplingInterval=500 \
# -Dorg.geotools.referencing.forceXY=true -XX:SoftRefLRUPolicyMSPerMB=36000 -XX:+UseParallelGC -XX:NewRatio=2 \
# -XX:+CMSClassUnloadingEnabled"

ENV GEOSERVER_OPTS "-Djava.awt.headless=true -server -Xrs -XX:PerfDataSamplingInterval=500 \
 -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap \
 -Dorg.geotools.referencing.forceXY=true -XX:SoftRefLRUPolicyMSPerMB=36000 -XX:NewRatio=2 \
 -XX:+CMSClassUnloadingEnabled"

ENV JAVA_OPTS "$JAVA_OPTS $GEOSERVER_OPTS"

COPY geoserver-data/templates /opt/geoserver/templates
COPY geoserver-data/data /opt/geoserver/data_dir

# This is needed to intercept WMS GetCapabilities Requests
ENV TC_WEB_INF "/usr/local/tomcat/webapps/geoserver/WEB-INF"
COPY wms-capabilities/web.xml $TC_WEB_INF/web.xml
COPY wms-capabilities/wms-capabilities.xml $TC_WEB_INF/classes
RUN mkdir -p $TC_WEB_INF/classes/nl/pdok/filter
COPY wms-capabilities/src/nl/pdok/filter/WmsCapabilitiesFilter.class $TC_WEB_INF/classes/nl/pdok/filter

# Add a custom entry script to intercept and configure
COPY entry.sh /entry.sh
RUN chmod +x /entry.sh

CMD "/entry.sh"
