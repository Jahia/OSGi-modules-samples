package org.foo.modules.rules;

import org.jahia.api.Constants;
import org.jahia.api.templates.JahiaTemplateManagerService;
import org.jahia.services.content.DefaultEventListener;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.RepositoryException;
import javax.jcr.observation.Event;
import javax.jcr.observation.EventIterator;
import javax.jcr.observation.EventListener;

@Component(service = EventListener.class, immediate = true)
public class CustomJCREventListener extends DefaultEventListener {

    Logger logger = LoggerFactory.getLogger(CustomJCREventListener.class);
    private JahiaTemplateManagerService templateManagerService;

    String[] nodetypes = new String[]{"jnt:bigText", "jnt:news"};

    @Reference(service = JahiaTemplateManagerService.class)
    public void setTemplateManagerService(JahiaTemplateManagerService templateManagerService) {
        this.templateManagerService = templateManagerService;
    }

    @Activate
    public void start() {
        logger.info("Registering CustomJCREventListener in {}", templateManagerService);
        setWorkspace(Constants.EDIT_WORKSPACE);
        templateManagerService.getTemplatePackageRegistry().handleJCREventListener(this, true);
    }

    @Deactivate
    public void stop() {
        logger.info("Unregistering CustomJCREventListener from {}", templateManagerService);
        templateManagerService.getTemplatePackageRegistry().handleJCREventListener(this, false);
    }

    @Override
    public int getEventTypes() {
        return Event.PROPERTY_ADDED + Event.PROPERTY_CHANGED;
    }

    @Override
    public String[] getNodeTypes() {
        return nodetypes;
    }

    @Override
    public void onEvent(EventIterator eventIterator) {
        while (eventIterator.hasNext()) {
            Event event = eventIterator.nextEvent();
            try {
                logger.info("Received an event for {}", event.getPath());
            } catch (RepositoryException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

