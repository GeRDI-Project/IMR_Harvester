# GeRDI Harvester Image for 'IMR'

FROM jetty:9.4.7-alpine

COPY \/target\/*.war $JETTY_BASE\/webapps\/imr.war

EXPOSE 8080