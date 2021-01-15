package org.foo.modules.events;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service=EventHandler.class,immediate = true, property= EventConstants.EVENT_TOPIC+"=org/jahia/*")
public class OsgiSystemEventHandler implements EventHandler {

    private static Logger logger = LoggerFactory.getLogger(OsgiSystemEventHandler.class);

    @Override
    public void handleEvent(Event event) {
        logger.info("Received OSGi event: {}", event);
    }
}
