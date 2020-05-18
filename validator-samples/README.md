# Jahia Validator with OSGi

This repository contains samples of Validator declared with OSGi

## How to test it

- Deploy the module on your server
- Activate the module on a site
- On your site create a new `jnt:testContent` and fill its two properties
- Check your server logs, your should see a log similar to this one:
```
2020-05-18 10:24:47,642: INFO  [GraphQLServlet] - Bad POST request: parsing failed
org.jahia.modules.graphql.provider.dxm.DataFetchingException: org.jahia.services.content.CompositeConstraintViolationException: /sites/mySite/home/area-main/testcontent longValue: length should be between 1000 and 2147483647
```
