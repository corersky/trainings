FROM maprtech/pacc:5.2.2_3.0.1_centos7

# Copy jar to container
RUN mkdir -p /usr/share/mapr-apps/
COPY build/libs/spring-crud-maprdb-0.1.0.jar /usr/share/mapr-apps/spring-crud-maprdb.jar


# Run producer application in container
#CMD ["/bin/bash"]
CMD [ "java", "-jar", "/usr/share/mapr-apps/spring-crud-maprdb.jar"]


