# Jahia OSGi Workflow sample

This repository contains samples of workflow registration

## How to test it

- Deploy the module on your server
- Change the publish workflow of a node to "sample workflow"
- Start publication request for the node

## What's inside

This module contains :
- A workflow definition, as BPMN2 file
- The associated registration in a json file
- A mail template
- A custom WorkItem handler, exposed as an OSGi service
- A custom Assignation valve, exposed as an OSGi service