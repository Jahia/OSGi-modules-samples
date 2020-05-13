package org.foo.modules.actions;

import org.foo.modules.services.SimpleService;
import org.jahia.bin.Action;
import org.jahia.bin.ActionResult;
import org.jahia.services.content.JCRSessionWrapper;
import org.jahia.services.render.RenderContext;
import org.jahia.services.render.Resource;
import org.jahia.services.render.URLResolver;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * This action is using an OSGi service from the module service-declaration-samples
 */
@Component(service = Action.class, immediate = true)
public class ConsumeOSGiServiceAction extends Action {

    private SimpleService simpleService;

    public ConsumeOSGiServiceAction() {
        setName("consumeOSGiService");
        setRequireAuthenticatedUser(false);
    }

    @Override
    public ActionResult doExecute(HttpServletRequest req, RenderContext renderContext, Resource resource,
                                  JCRSessionWrapper session, Map<String, List<String>> parameters,
                                  URLResolver urlResolver) throws Exception {

        JSONObject response = new JSONObject();
        response.put("message", simpleService.sayHello());

        return new ActionResult(HttpServletResponse.SC_OK, null, response);
    }

    @Reference
    public void setSimpleService(SimpleService simpleService) {
        this.simpleService = simpleService;
    }
}
