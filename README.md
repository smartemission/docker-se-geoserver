# GeoServer service

The `geoserver` service provides a complete GeoServer instance with a complete configuration (GS data dir) 
specific for SE.

## Hosting

The Docker Image is hosted as: 
[smartemission/se-geoserver at DockerHub](https://hub.docker.com/r/smartemission/se-geoserver).
Versioning via GH tags is done following GeoServer versions like 2.9.4 followed by an SE 
version number, for example 2.9.4-1, 2.9.4-2 etc.

## Environment

The following environment vars need to be set, either via `docker-compose` or
Kubernetes.


|Environment variable|
|---|
|DB_HOSTNAME|
|DB_USERNAME|
|DB_PASSWD|
|GS_USERNAME|
|GS_PASSWD|

## Architecture

The image includes the official [Kartoza GeoServer](https://github.com/kartoza/docker-geoserver) Docker Image 
hosted as [kartoza/geoserver on DockerHub](https://hub.docker.com/r/kartoza/geoserver/)
overlayed with
a complete configuration (GeoServer data dir). Further vars from that config are overruled/set via its 
Container Environment at runtime via the [entry.sh](entry.sh) script.

### WMS Capabilities

As many Layers have a Dimension as Time enabled, the Dimension range (start-end time)
is generated for each Layer and put in the Capabilities document. This results in queries on the Postgres database
that take huge amount of time (about 1 minute per million records), even with the `time` column indexed.
Basically a rowscan is done for all 20-30 Layers...In some cases GeoServer crashes. To overcome this
a semi-static Capabilities document is generated from a template file [wms-capabilities.xml](wms-capabilities/wms-capabilities.xml).
This is handled via a custom Servlet Filter: the [WmsCapabilitiesFilter.java](wms-capabilities/src/nl/pdok/filter/WmsCapabilitiesFilter.java)
that is added to the Tomcat [web.xml](wms-capabilities/web.xml) config file.  

## Dependencies

* PostGIS backend

## Links

* SE Platform doc: http://smartplatform.readthedocs.io/en/latest/
* OGC SensorThings API Standard: http://www.opengeospatial.org/standards/sensorthings
