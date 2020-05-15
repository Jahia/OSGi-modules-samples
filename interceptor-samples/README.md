# Jahia Interceptor with OSGi

This repository contains samples of Interceptor declared with OSGi

## How to test it

- Deploy the module on your server
- Activate the module on a site
- On your site create a new `Rich Text` and add some text to it
- Check your server logs, your should see a log similar to this one:
```
2020-05-15 10:15:34,225: INFO  [TestInterceptor] - Hello Jahia! you are setting the value <p>Hello World!eeeeeeeeerrr</p>
 on the node /sites/mySite/home/area-main/rich-text
```
