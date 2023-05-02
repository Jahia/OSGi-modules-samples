package org.foo.modules.samples.config.factory.site.internal;

import org.foo.modules.samples.config.factory.site.SiteConfig;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.*;

/**
 * An example of a component using a configuration for component factories also using metatypes. The configuration files are
 * located in src/main/resources/META-INF/configurations/org.foo.modules.samples.config.factory.site-*.cfg
 */
@Component(service = {SiteConfig.class}, configurationPid = "org.foo.modules.samples.config.factory.site")
@Designate(ocd = SiteConfigImpl.Config.class)
public class SiteConfigImpl implements SiteConfig {

    private Config config;

    @ObjectClassDefinition(name = "Site configuration", description = "A configuration for a site")
    public @interface Config {

        @AttributeDefinition(name = "Site key", defaultValue = "siteKey", description = "The identifier for the site")
        String key() default "siteKey";

        // Dropdown
        @AttributeDefinition(name = "Site type", options = { @Option(label = "Landing page", value = "landing"),
                @Option(label = "Marketing", value = "marketing"), @Option(label = "Support", value = "support") })
        String type() default "marketing";
    }

    @Activate
    public void activate(final Config config) {
        this.config = config;
    }

    @Override
    public String getKey() {
        return config.key();
    }

    @Override
    public String getType() {
        return config.type();
    }
}
