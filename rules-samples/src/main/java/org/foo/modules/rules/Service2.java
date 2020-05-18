package org.foo.modules.rules;

import org.jahia.services.content.rules.AddedNodeFact;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.osgi.service.component.annotations.Component;

import javax.jcr.RepositoryException;

@Component(service=Service2.class)
public class Service2 {
    private static Logger logger = LoggerFactory.getLogger(Service2.class);

    public void hello(AddedNodeFact node) throws RepositoryException {
        logger.info("Hello 2 " + node.getName());
    }

}
