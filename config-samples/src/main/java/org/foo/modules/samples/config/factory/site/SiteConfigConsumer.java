package org.foo.modules.samples.config.factory.site;

import org.osgi.service.component.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * An example of a consumer for the site configuration service example.
 */
@Component
public class SiteConfigConsumer {

    private static final Logger logger = LoggerFactory.getLogger(SiteConfigConsumer.class);

    private List<SiteConfig> siteConfigs = new ArrayList<>();

    // The GREEDY policy makes sure that ALL the instances of the configuration are instantiated, otherwise only one would be created.
    // Making it DYNAMIC also avoids the component to activate/deactivate for each config binding.
    @Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC, policyOption = ReferencePolicyOption.GREEDY)
    public void addSiteConfig(SiteConfig siteConfig) {
        this.siteConfigs.add(siteConfig);
        logger.info("Adding site key={} type={}", siteConfig.getKey(), siteConfig.getType());
    }

    public void removeSiteConfig(SiteConfig siteConfig) {
        this.siteConfigs.remove(siteConfig);
        logger.info("Removing site key={}", siteConfig.getKey());
    }

}
