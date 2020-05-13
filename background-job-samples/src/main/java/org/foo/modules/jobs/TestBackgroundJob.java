package org.foo.modules.jobs;

import org.jahia.services.scheduler.BackgroundJob;
import org.jahia.services.scheduler.SchedulerService;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple Background Job declared with OSGi
 *
 * @author dgaillard
 */
@Component(service = BackgroundJob.class, immediate = true)
public class TestBackgroundJob extends BackgroundJob {
    private static Logger logger = LoggerFactory.getLogger(TestBackgroundJob.class);

    private SchedulerService schedulerService;
    private JobDetail jobDetail;

    @Activate
    public void start() throws Exception {
        jobDetail = BackgroundJob.createJahiaJob("Simple background job made declared with OSGi", TestBackgroundJob.class);
        Trigger trigger = new SimpleTrigger("testBackgroundJob_trigger", jobDetail.getGroup(), SimpleTrigger.REPEAT_INDEFINITELY, 3000);
        schedulerService.getScheduler().scheduleJob(jobDetail, trigger);
    }

    @Deactivate
    public void stop() throws Exception {
        schedulerService.getScheduler().deleteJob(jobDetail.getName(), jobDetail.getGroup());
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
