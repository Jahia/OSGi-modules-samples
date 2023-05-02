package org.foo.modules.samples.config.yaml;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * An example of a component using a YAML configuration. The configuration file is
 * located in src/main/resources/META-INF/configurations/org.foo.modules.samples.config.yaml.example.cfg
 */
@Component(configurationPid="org.foo.modules.samples.config.yaml.example", immediate=true)
public class YamlConfigExample {

    private static final Logger logger = LoggerFactory.getLogger(YamlConfigExample.class);

    @Activate
    public void activate(Map<String, ?> properties) {
        for (String key : properties.keySet()) {
            logger.info("{} : {}", key, properties.get(key));
        }
    }
}
