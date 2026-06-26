package org.foo.modules.jobs;

import org.jahia.services.scheduler.BackgroundJob;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple Background Job registered via {@link TestBackgroundJobRegistration}.
 * <b>NB:</b> Note that Quartz creates instances directly, it is not possible to inject OSGi components in this job. {@link org.jahia.osgi.BundleUtils#getOsgiService(Class, String)} can be used to access OSGi components during the job execution.
 *
 * @see TestBackgroundJobRegistration
 */
public class TestBackgroundJob extends BackgroundJob {
    private static final Logger logger = LoggerFactory.getLogger(TestBackgroundJob.class);

    @Override
    public void executeJahiaJob(JobExecutionContext jobExecutionContext) {
        logger.info("Hello Jahia!");
        // use BundleUtils.getOsgiService(...) to access OSGi components from within this job
    }

}
