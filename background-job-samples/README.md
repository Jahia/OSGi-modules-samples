# Jahia Background Job with OSGi

This module contains an example of a `BackgroundJob` registered with OSGi in Jahia.

## What this background job does

Background jobs are scheduled tasks executed by Jahia's scheduler outside HTTP requests.

In this sample module:

- The job is registered when the OSGi component is activated.
- It runs every 3 seconds using a `SimpleTrigger`.
- It only runs on processing nodes (`SettingsBean.getInstance().isProcessingServer()`).
- It writes a message to the server logs on each execution.

## Included classes

- `TestBackgroundJob`: the job itself. Quartz instantiates it directly, so OSGi components cannot be injected — use `BundleUtils.getOsgiService(...)` to access them during execution.
- `TestBackgroundJobRegistration`: the OSGi component that schedules the job on activation and unschedules it on deactivation, using a fixed job name (`namedJob`) so that a stale job can be found and deleted after a restart.

The registration includes a shutdown guard to avoid errors when Jahia stops the scheduler before OSGi component deactivation.

From Jahia `8.2.4.0` onwards, the registration can be simplified with `BackgroundJob.createJahiaJob(...)` and `schedulerService.deleteJob(jobDetail)`; both alternatives are shown as comments in `TestBackgroundJobRegistration`.

## How to test it

- Deploy the module on your server
- Check your server logs, you should see the message `Hello Jahia!` every 3 seconds
