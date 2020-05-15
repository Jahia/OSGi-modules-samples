package org.foo.modules.interceptors;

import org.jahia.services.content.JCRNodeWrapper;
import org.jahia.services.content.JCRStoreService;
import org.jahia.services.content.interceptor.BaseInterceptor;
import org.jahia.services.content.nodetypes.ExtendedPropertyDefinition;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.RepositoryException;
import javax.jcr.Value;
import java.util.Collections;

/**
 * @author dgaillard
 */
@Component(immediate = true)
public class TestInterceptor extends BaseInterceptor {
    private static Logger logger = LoggerFactory.getLogger(TestInterceptor.class);

    private JCRStoreService jcrStoreService;

    @Activate
    public void start() {
        setRequiredTypes(Collections.singleton("String"));
        setSelectors(Collections.singleton("RichText"));
        jcrStoreService.addInterceptor(this);
    }

    @Deactivate
    public void stop() {
        jcrStoreService.removeInterceptor(this);
    }

    @Override
    public Value beforeSetValue(JCRNodeWrapper node, String name, ExtendedPropertyDefinition definition,
                                Value originalValue) throws RepositoryException {
        logger.info("Hello Jahia! you are setting the value {} on the node {}", originalValue.getString(), node.getPath());
        return super.beforeSetValue(node, name, definition, originalValue);
    }

    @Reference
    public void setJcrStoreService(JCRStoreService jcrStoreService) {
        this.jcrStoreService = jcrStoreService;
    }
}
