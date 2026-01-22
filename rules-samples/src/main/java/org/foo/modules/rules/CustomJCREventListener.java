package org.foo.modules.rules;

import org.jahia.services.content.DefaultEventListener;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.RepositoryException;
import javax.jcr.observation.Event;
import javax.jcr.observation.EventIterator;
import javax.jcr.observation.EventListener;

@Component(service = DefaultEventListener.class, immediate = true)
public class CustomJCREventListener extends DefaultEventListener {

    Logger logger = LoggerFactory.getLogger(CustomJCREventListener.class);

    String[] nodetypes = new String[]{"jnt:bigText", "jnt:news"};

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

