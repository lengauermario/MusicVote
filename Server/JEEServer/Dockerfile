FROM leonkuchinka/wildfly:latest
USER root
RUN curl -L https://yt-dl.org/downloads/latest/youtube-dl -o /usr/local/bin/youtube-dl
RUN chmod a+rx /usr/local/bin/youtube-dl

ADD ./target/musicvoting.war /opt/jboss/wildfly/standalone/deployments/
