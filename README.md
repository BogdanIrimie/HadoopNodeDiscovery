# HadoopNodeDiscovery
Discover Hadoop NameNodes and DataNodes for a cluster you have no prior information about.

#Buid
## Requirements
- install [Maven](https://maven.apache.org/download.cgi)
- install [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

##Build process
- run 'mvn package' in the root directory of the project.

#Running
##Requirements
- install [JRE 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html)
- install [Nmap 6.40 or newer](https://nmap.org/download.html)

##Running
java -jar <path/jarName.jar> <ips or ip ranges>

ip's can be specified as a list of ip or ip ranges. 

#Examples
java -jar HdfsDiscovery 192.168.56.102

java -jar HdfsDiscovery 192.168.56.0-255
