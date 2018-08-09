FROM kartoza/geoserver:2.12.1
MAINTAINER="Gerwin Hulsteijn, Just van den Broecke"

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

RUN \
  apt-get update \
  && apt-get -y install gettext-base \
  && apt-get clean \
  && rm -rf /var/lib/apt/lists/*

COPY geoserver-data/templates /opt/geoserver/templates
COPY geoserver-data/data /opt/geoserver/data_dir

COPY entry.sh /entry.sh
RUN chmod +x /entry.sh

CMD "/entry.sh"
