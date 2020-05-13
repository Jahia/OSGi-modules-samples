# Jahia OSGi Service Samples

This repository contains samples of OSGi service

## How to test it

- Deploy the 2 modules on your server, one of them declare an OSGi service the other one consume it
- Activate the module `service-consumer-samples` on a site
- Then call the action
```shell script
response=$(curl -s -X GET http://localhost:8080/yourContextIfOne/en/sites/youSiteKey.consumeOSGiService.do -H 'accept: application/json') && echo $response

## Output >
## {"message":"Hello Jahia!"}
```
