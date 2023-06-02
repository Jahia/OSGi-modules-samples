package org.foo.modules.samples.config.metatype;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.osgi.service.metatype.annotations.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An example of a component using a metatype configuration. The configuration file is
 * located in src/main/resources/META-INF/configurations/org.foo.modules.samples.config.metatype.example.cfg
 */
@Component(configurationPid = "org.foo.modules.samples.config.metatype.example", immediate = true)
@Designate(ocd = MetatypeConfigExample.Config.class)
public class MetatypeConfigExample {

    private static final Logger logger = LoggerFactory.getLogger(MetatypeConfigExample.class);

    @ObjectClassDefinition(name = "%metatypeConfiguration", description = "%metatypeConfigurationDescription", localization = "OSGI-INF/l10n/metatypeExample")
    public @interface Config {
        @AttributeDefinition(name = "%simpleKey", defaultValue = "nokey", description = "%simpleKeyDescription")
        String key() default "nokey";

        // Dropdown
        @AttributeDefinition(name = "%dropdown", options = { @Option(label = "%dropdown.option1", value = "option1"),
                @Option(label = "%dropdown.option2", value = "option 2"), @Option(label = "%dropdown.option3", value = "option3") })
        String dropdown() default "option1";

        @AttributeDefinition(name = "%values.test1", defaultValue = "none", description="%values.test1.description")
        String values_test1() default "none";
    }

    @Activate
    public void activate(Config config) {
        logger.info("key={}", config.key());
        logger.info("dropdown={}", config.dropdown());
        logger.info("values.test1={}", config.values_test1());
    }
}
