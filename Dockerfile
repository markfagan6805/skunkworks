FROM tomcat:9.0

LABEL maintainer="mark.fagan6805@gmail.com"

COPY target/untitled.war /usr/local/tomcat/webapps/

EXPOSE 8080:8080

CMD ["catalina.sh", "run"]