FROM maprtech/pacc:5.2.2_3.0.1_centos7

# Copy jar to container
RUN mkdir -p /usr/share/mapr-apps/
COPY build/libs/gs-crud-with-vaadin-0.1.0.jar /usr/share/mapr-apps/gs-crud-with-vaadin.jar

#jar -x /usr/share/mapr-apps/gs-crud-with-vaadin.jar

# Run producer application in container
#CMD ["/bin/bash"]
#CMD [ "java", "-jar", "/usr/share/mapr-apps/gs-crud-with-vaadin.jar", "--loader.path=`mapr classpath`"]
CMD [ "java", "-jar", "/usr/share/mapr-apps/gs-crud-with-vaadin.jar"]


