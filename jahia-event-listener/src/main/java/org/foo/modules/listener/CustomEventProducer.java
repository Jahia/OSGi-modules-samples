package org.foo.modules.listener;

import org.jahia.params.valves.BaseLoginEvent;
import org.jahia.services.observation.JahiaEventService;
import org.jahia.services.usermanager.JahiaUser;
import org.jahia.services.usermanager.JahiaUserImpl;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A custom event producer class that defines a new custom login event types and produces instances of it every
 * 10 seconds. This serves as an example on how to create custom event types and how to inject them into the
 * event service as well.
 */
@Component(immediate=true)
public class CustomEventProducer {

    private final static Logger logger = LoggerFactory.getLogger(TestEventListener.class);

    private JahiaEventService jahiaEventService;
    private AtomicBoolean active = new AtomicBoolean(false);

    private ScheduledExecutorService executor;

    @Reference
    public void setJahiaEventService(JahiaEventService jahiaEventService) {
        this.jahiaEventService = jahiaEventService;
    }

    public void produceEvents() {
        try {
            while (active.get()) {
                jahiaEventService.publishEvent(new CustomLoginEvent(this, new JahiaUserImpl("testUser", "/users/testUser", new Properties(), "customRealm")));
                Thread.sleep(10000); // 10 seconds
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Activate
    public void activate(BundleContext context) {
        logger.info("Starting custom event producer...");
        executor = Executors.newSingleThreadScheduledExecutor();
        active.set(true);

        Runnable task = this::produceEvents;

        executor.scheduleAtFixedRate(task, 0, 2, TimeUnit.SECONDS);
    }

    @Deactivate
    public void deactivate() {
        logger.info("Shutting down custom event producer...");
        active.set(false);
        executor.shutdown();
        executor = null;
    }

    private class CustomLoginEvent extends BaseLoginEvent {
        public CustomLoginEvent(Object source, JahiaUser jahiaUser) {
            super(source, jahiaUser, null);
        }
    }

}
