package org.foo.modules.jobs;

import org.jahia.services.scheduler.BackgroundJob;
import org.jahia.services.scheduler.SchedulerService;
import org.jahia.settings.SettingsBean;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Registration of a simple background {@link TestBackgroundJob} via OSGi.<br/>
 * <b>NB:</b> Note that only the registration of the job OSGi components, the execution itself does not have access to OSGi dependency injection.
 *
 * @see TestBackgroundJob
 *
 */
@Component(immediate = true)
public class TestBackgroundJobRegistration {
    private static final Logger logger = LoggerFactory.getLogger(TestBackgroundJobRegistration.class);
    private static final String GROUP_NAME = BackgroundJob.getGroupName(TestBackgroundJob.class);
    private static final String JOB_NAME = "namedJob";

    private SchedulerService schedulerService;
    private JobDetail jobDetail;

    @Activate
    public void start() throws Exception {
        // Fixed name -> can be retrieved after restart
        jobDetail = new JobDetail(JOB_NAME, GROUP_NAME, TestBackgroundJob.class, false, true, false);
        jobDetail.setDescription("Simple background job registered with OSGi");
        if (SettingsBean.getInstance().isProcessingServer()) {
            // Delete the old job at startup if exists
            schedulerService.getScheduler().deleteJob(JOB_NAME, GROUP_NAME);
            Trigger trigger = new SimpleTrigger("testBackgroundJob_trigger", jobDetail.getGroup(), SimpleTrigger.REPEAT_INDEFINITELY, 3000);
            schedulerService.getScheduler().scheduleJob(jobDetail, trigger);
            logger.info("Simple background {} job registered", jobDetail.getName());
        }
    }

    @Deactivate
    public void stop() throws Exception {
        // If Jahia is shutting down, the scheduler is already stopped -> we do nothing.
        // The job will be cleaned at the next startup. It could be found with the fixed name
        if (schedulerService.getScheduler() == null) {
            return;
        }
        if (!schedulerService.getAllJobs(jobDetail.getGroup()).isEmpty() && SettingsBean.getInstance().isProcessingServer()) {
            schedulerService.getScheduler().deleteJob(jobDetail.getName(), jobDetail.getGroup());
            logger.info("Simple background {} job unregistered", jobDetail.getName());
        }
    }

    @Reference
    public void setSchedulerService(SchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }
}
