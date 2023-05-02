package org.foo.modules.samples.config.simple;

import org.foo.modules.samples.config.yaml.YamlConfigExample;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * A very basic example of a component using a configuration. The configuration file is
 * located in src/main/resources/META-INF/configurations/org.foo.modules.samples.config.simple.example.cfg
 */
@Component(configurationPid="org.foo.modules.samples.config.simple.example", immediate=true)
public class SimpleConfigExample {
    private static final Logger logger = LoggerFactory.getLogger(SimpleConfigExample.class);

    @Activate
    public void activate(Map<String, ?> properties) {
        for (String key : properties.keySet()) {
            logger.info("{} : {}", key, properties.get(key));
        }
    }
}
