package org.foo.modules.rules;

import org.jahia.services.content.rules.ModuleGlobalObject;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Simple Background Job declared with OSGi
 *
 * @author dgaillard
 */
@Component(service = ModuleGlobalObject.class)
public class TestRules extends ModuleGlobalObject {

    @Reference
    public void setService1(Service1 service1) {
        getGlobalRulesObject().put("service1", service1);
    }

    public void unsetService1(Service1 service1) {
        getGlobalRulesObject().remove("service1");
    }

    @Reference
    public void setService2(Service2 service2) {
        getGlobalRulesObject().put("service2", service2);
    }

    public void unsetService2(Service2 service2) {
        getGlobalRulesObject().remove("service2");
    }

}
