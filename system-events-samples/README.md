# Jahia System event handlers with OSGi

This repository contains samples of System Event handlers declared with OSGi

## How to test it

- Deploy the module on your server
- Check your server logs, you should see logs for the various system events such as login/logout, etc... Looks something like this:

        2021-01-15 11:54:39,820: INFO  [OsgiSystemEventHandler] - Received OSGi event: org.osgi.service.event.Event [topic=org/jahia/usersgroups/login/LOGIN] {authContext=org.jahia.params.valves.AuthValveContext@195ee12e, source=org.jahia.params.valves.LoginEngineAuthValveImpl@9738ba1d, user=org.jahia.services.usermanager.JahiaUserImpl@e7b199f8, timestamp=1610708079820}
