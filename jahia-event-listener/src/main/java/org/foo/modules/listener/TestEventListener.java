package org.foo.modules.listener;

import org.jahia.params.valves.BaseLoginEvent;
import org.jahia.services.observation.JahiaEventListener;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.EventObject;

@Component(service = JahiaEventListener.class, immediate = true)
public class TestEventListener implements JahiaEventListener<BaseLoginEvent> {
    private final static Logger logger = LoggerFactory.getLogger(TestEventListener.class);

    private static final Class<BaseLoginEvent>[] ALLOWED_EVENT_TYPES = new Class[]{BaseLoginEvent.class};

    @Override
    public void onEvent(EventObject event) {
        // We can perform this cast because we said we only wanted to receive BaseLoginEvent and subclasses of it
        BaseLoginEvent baseLoginEvent = (BaseLoginEvent) event;
        logger.info("Received login event : class={} user={}", event.getClass().getName(), baseLoginEvent.getJahiaUser().getUserKey());
    }

    @Override
    public Class<BaseLoginEvent>[] getEventTypes() {
        return ALLOWED_EVENT_TYPES;
    }
}
