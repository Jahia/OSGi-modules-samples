# Jahia OSGi Service Samples

This repository contains samples of OSGi service

## How to test it

- Deploy the 2 modules on your server, one of them declare an OSGi service the other one consume it
- Activate the module `service-consumer-samples` on a site
- Then call the action
```shell script
response=$(curl -s -X POST http://localhost:8080/en/sites/SITEKEY.consumeOSGiService.do -H 'accept: application/json') && echo $response
```
Output should be something like : 

```shell script
{"message":"Hello Jahia! Home directory=/home/tomcat"}
```
