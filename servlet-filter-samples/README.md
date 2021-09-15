# Jahia Filter with OSGi

This repository contains samples of Jahia Filter declared with OSGi

## How to test it

- Deploy the module on your server
- You should see servlet filter activate successfully from the logs
- Go to any page on jahia and check response headers
- You should be able to see header value injected ("x-sample-filter-header": "some-test-value")
