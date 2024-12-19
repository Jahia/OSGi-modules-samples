# Jahia HttpClient Mutual TLS (POC)

This repository contains samples of using HttpClient declared with OSGi using Mutual TLS

## How to test it

- Deploy the module on your server
- Deactivate Csrf
- Activate module on a site
- Try an action call (from commande line)

```bash 
response=$(curl -s -X POST http://localhost:8080/en/sites/<siteid>.testHttpClientWithMutualTLS.do -H 'accept: application/json') && echo $response
```
