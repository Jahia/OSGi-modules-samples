# Jahia Action with OSGi

This repository contains samples of Jahia Action declared with OSGi

To activate the OSGi annotations do not forget to add this instruction in the configuration of the maven-bundle-plugin of your pom.xml: 
```xml                    
<instructions>
  <_dsannotations>*</_dsannotations>
</instructions>
```

## How to test it

- Deploy the module on your server
- Activate the module on a site
- Then call one of the actions

Simple action:
```shell script
response=$(curl -s -X POST http://localhost:8080/en/sites/SITE_KEY.simpleAction.do -H 'accept: application/json') && echo $response

## Output >
## {"message":"Hello Jahia!"}
```

Advanced action:
```shell script
response=$(curl -s -u root:root1234 -X POST http://localhost:8080/en/sites/SITE_KEY.advancedAction.do -H 'accept: application/json') && echo $response

## Output something like >
## {"message":"Hello Jahia there are X modules on this site","modules":[]}
```
