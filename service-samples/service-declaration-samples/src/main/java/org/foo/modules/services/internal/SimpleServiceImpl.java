package org.foo.modules.services.internal;

import org.foo.modules.services.SimpleService;
import org.osgi.service.component.annotations.Component;

@Component(service = SimpleService.class)
public class SimpleServiceImpl implements SimpleService {

    @Override
    public String sayHello() {
        return "Hello Jahia!";
    }
}
