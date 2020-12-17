package org.foo.modules.jobs;

import org.jahia.services.scheduler.BackgroundJob;
import org.jahia.services.scheduler.SchedulerService;
import org.jahia.settings.SettingsBean;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple Background Job declared with OSGi
 *
 * @author dgaillard
 */
@Component(immediate = true)
public class TestBackgroundJob extends BackgroundJob {
    private static Logger logger = LoggerFactory.getLogger(TestBackgroundJob.class);

    private SchedulerService schedulerService;
    private JobDetail jobDetail;

    @Activate
    public void start() throws Exception {
        jobDetail = BackgroundJob.createJahiaJob("Simple background job made declared with OSGi", TestBackgroundJob.class);
        if (schedulerService.getAllJobs(jobDetail.getGroup()).isEmpty() && SettingsBean.getInstance().isProcessingServer()) {
            Trigger trigger = new SimpleTrigger("testBackgroundJob_trigger", jobDetail.getGroup(), SimpleTrigger.REPEAT_INDEFINITELY, 3000);
            schedulerService.getScheduler().scheduleJob(jobDetail, trigger);
        }
    }

    @Deactivate
    public void stop() throws Exception {
        if (!schedulerService.getAllJobs(jobDetail.getGroup()).isEmpty() && SettingsBean.getInstance().isProcessingServer()) {
            schedulerService.getScheduler().deleteJob(jobDetail.getName(), jobDetail.getGroup());
        }
    }

    @Override
    public void executeJahiaJob(JobExecutionContext jobExecutionContext) {
        logger.info("Hello Jahia!");
    }

    @Reference
    public void setSchedulerService(SchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }
}
