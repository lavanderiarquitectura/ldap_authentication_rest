FROM javergarav/tomee

COPY env/tomee.xml conf/tomee.xml
COPY env/server.xml conf/server.xml

COPY target/ldap-auth.war webapps/ldap-auth.war

EXPOSE 3001
