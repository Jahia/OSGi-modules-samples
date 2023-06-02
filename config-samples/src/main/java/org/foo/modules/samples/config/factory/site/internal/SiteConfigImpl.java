package org.foo.modules.samples.config.factory.site.internal;

import org.foo.modules.samples.config.factory.site.SiteConfig;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.metatype.annotations.*;

/**
 * An example of a component using a configuration for component factories also using metatypes. The configuration files are
 * located in src/main/resources/META-INF/configurations/org.foo.modules.samples.config.factory.site-*.cfg
 */
@Component(service = {SiteConfig.class}, configurationPid = "org.foo.modules.samples.config.factory.site", configurationPolicy = ConfigurationPolicy.REQUIRE)
@Designate(ocd = SiteConfigImpl.Config.class, factory = true)
public class SiteConfigImpl implements SiteConfig {

    private Config config;

    @ObjectClassDefinition(name = "%siteConfiguration", description = "%siteConfigurationDescription", localization = "OSGI-INF/l10n/siteconfig")
    public @interface Config {

        @AttributeDefinition(name = "%siteKey", defaultValue = "siteKey", description = "%siteKeyDescription")
        String key() default "siteKey";

        // Dropdown
        @AttributeDefinition(name = "%siteType", options = { @Option(label = "%siteType.landingPage", value = "landing"),
                @Option(label = "%siteType.marketing", value = "marketing"), @Option(label = "%siteType.support", value = "support") })
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
