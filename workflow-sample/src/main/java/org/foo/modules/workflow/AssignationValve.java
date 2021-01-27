package org.foo.modules.workflow;

import org.jahia.params.valves.BaseAuthValve;
import org.jahia.pipelines.Pipeline;
import org.jahia.pipelines.PipelineException;
import org.jahia.pipelines.valves.ValveContext;
import org.jahia.services.usermanager.JahiaUser;
import org.jahia.services.usermanager.JahiaUserManagerService;
import org.jahia.services.workflow.jbpm.custom.JahiaLocalHTWorkItemHandler;
import org.jbpm.services.task.impl.model.UserImpl;
import org.kie.api.task.model.Task;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Assignation valves allows to define who will be able to take and complete a task. It's called when the task is created.
 *
 * By default, the AclPeopleAssignmentValve will check for the user permissions for each task, testing if they
 * match the permissions in json workflow declaration. You can bypass this behaviour by skipping the invokeNext() call.
 *
 * This class is called system-wide, for all workflows. You need to check the process and task name if you want to apply
 * only on some workflows.
 */
@Component(immediate=true)
public class AssignationValve extends BaseAuthValve {
    private static final Logger logger = LoggerFactory.getLogger(AssignationValve.class);

    private Pipeline pipeline;

    private JahiaUserManagerService jahiaUserManagerService;

    @Reference(service = Pipeline.class, target="(type=peopleAssignmentPipeline)")
    public void setPipeline(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    @Reference
    public void setJahiaUserManagerService(JahiaUserManagerService jahiaUserManagerService) {
        this.jahiaUserManagerService = jahiaUserManagerService;
    }

    @Activate
    public void activate() {
        setId("customAssignation");
        removeValve(pipeline);
        addValve(pipeline, 0, null, null);
    }

    @Deactivate
    public void deactivate(BundleContext bundleContext) {
        removeValve(pipeline);
    }

    @Override
    public void invoke(Object context, ValveContext valveContext) throws PipelineException {
        JahiaLocalHTWorkItemHandler.PeopleAssignmentContext peopleAssignmentContext = (JahiaLocalHTWorkItemHandler.PeopleAssignmentContext) context;
        Task task = peopleAssignmentContext.getTask();

        String taskName = task.getNames().get(0).getText();
        if (taskName.equals("review1")) {
            logger.info("Custom assignation called for task {}", task.getId());
            // You can add potential owners for a task here. This user will be able to take the task.
            JahiaUser jahiaUser = jahiaUserManagerService.lookupUser("root").getJahiaUser();

            task.getPeopleAssignments().getPotentialOwners().add(new UserImpl(jahiaUser.getLocalPath()));
        }

        valveContext.invokeNext(context);
    }
}
