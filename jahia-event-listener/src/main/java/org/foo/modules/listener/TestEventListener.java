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

    @Override
    public void onEvent(EventObject event) {
        logger.info("Received event : "+event);
    }
}
