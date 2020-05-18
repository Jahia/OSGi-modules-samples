package org.foo.modules.rules;

import org.jahia.services.content.rules.AddedNodeFact;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.RepositoryException;

@Component(service=Service1.class)
public class Service1 {
    private static Logger logger = LoggerFactory.getLogger(Service1.class);

    public void hello(AddedNodeFact node) throws RepositoryException {
        logger.info("Hello 1 " + node.getName());
    }

}
