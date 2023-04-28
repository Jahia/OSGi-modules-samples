package org.foo.modules.services.internal;

import org.apache.commons.beanutils.BeanUtils;
import org.foo.modules.services.SimpleService;
import org.osgi.service.component.annotations.Component;

import java.util.Map;

@Component(service = SimpleService.class, configurationPid = "org.foo.modules.services.simple")
public class SimpleServiceImpl implements SimpleService {

    private String userName = "World";
    private String userHome = null;
    public void activate(Map<String, ?> props) {
        if (props.containsKey("userName")) {
            userName = (String) props.get("userName");
        }
        if (props.containsKey("userHome")) {
            userHome = (String) props.get("userHome");
        }
    }

    @Override
    public String sayHello() {
        return "Hello "+userName+"!" + (userHome != null ? " Home directory=" + userHome : "");
    }
}
