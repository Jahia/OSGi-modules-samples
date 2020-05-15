package org.foo.modules.cache;

import org.jahia.services.render.RenderContext;
import org.jahia.services.render.Resource;
import org.jahia.services.render.filter.cache.CacheKeyPartGenerator;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

@Component(service = CacheKeyPartGenerator.class)
public class TestCacheKeyPartGenerator implements CacheKeyPartGenerator {
    private static Logger logger = LoggerFactory.getLogger(TestCacheKeyPartGenerator.class);

    @Override
    public String getKey() {
        logger.info("Hello Jahia from getKey!");
        return null;
    }

    @Override
    public String getValue(Resource resource, RenderContext renderContext, Properties properties) {
        logger.info("Hello Jahia from getValue!");
        return null;
    }

    @Override
    public String replacePlaceholders(RenderContext renderContext, String keyPart) {
        logger.info("Hello Jahia from replacePlaceholders!");
        return null;
    }
}
