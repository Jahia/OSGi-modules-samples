# Jahia Background Job with OSGi

This module contains examples of `BackgroundJob` components declared with OSGi in Jahia.

## What these background jobs do

Background jobs are scheduled tasks executed by Jahia's scheduler outside HTTP requests.

In this sample module:

- Jobs are registered when the OSGi component is activated.
- Jobs run every 3 seconds using a `SimpleTrigger`.
- Jobs only run on processing nodes (`SettingsBean.getInstance().isProcessingServer()`).
- Jobs write a message to server logs on each execution.

## Included classes

- `TestBackgroundJob`: sample using a fixed job name (`namedJob`) and Quartz `JobDetail` construction.
- `TestBackgroundJob8240`: sample intended for Jahia `>= 8.2.4.0`, using `BackgroundJob.createJahiaJob(...)` and `schedulerService.deleteJob(jobDetail)`.

Both samples include shutdown guards to avoid errors when Jahia stops the scheduler before OSGi component deactivation.

## How to test it

- Deploy the module on your server
- Check server logs and confirm periodic messages from the jobs:
  - `Hello Jahia!`
