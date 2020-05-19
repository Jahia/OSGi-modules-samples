# Jahia Action with OSGi

This repository contains samples of Jahia Action declared with OSGi

## How to test it

- Deploy the module on your server
- Activate the module on a site
- Then call one of the actions

Simple action:
```shell script
response=$(curl -s -X GET http://localhost:8080/yourContextIfOne/en/sites/youSiteKey.simpleAction.do -H 'accept: application/json') && echo $response

## Output >
## {"message":"Hello Jahia!"}
```

Advanced action:
```shell script
response=$(curl -s -u root:root1234 -X GET http://localhost:8080/yourContextIfOne/en/sites/youSiteKey.advancedAction.do -H 'accept: application/json') && echo $response

## Output something like >
## {"message":"Hello Jahia there are X modules on this site","modules":[]}
```
