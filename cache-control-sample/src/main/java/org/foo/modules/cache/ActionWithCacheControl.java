package org.foo.modules.cache;

import org.jahia.bin.Action;
import org.jahia.bin.ActionResult;
import org.jahia.services.content.JCRSessionWrapper;
import org.jahia.services.render.RenderContext;
import org.jahia.services.render.Resource;
import org.jahia.services.render.URLResolver;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * This is the minimum required setup to declare a Jahia Action using OSGi
 */
@Component(service = Action.class)
public class ActionWithCacheControl extends Action {

    @Activate
    public void activate() {
        setName("actionCacheControl");
        setRequireAuthenticatedUser(false);
        setRequiredMethods("GET");
    }

    @Override
    public ActionResult doExecute(HttpServletRequest req, RenderContext renderContext, Resource resource,
                                  JCRSessionWrapper session, Map<String, List<String>> parameters,
                                  URLResolver urlResolver) throws Exception {
        JSONObject response = new JSONObject();
        response.put("message", "Hello, this is a custom action to setup header!");
        //If strict mode is activated, setting this header should not have any effect:
        renderContext.getResponse().setHeader("Cache-Control", "private,no-cache,no-store,max-age=-99");
        return new ActionResult(HttpServletResponse.SC_OK, null, response);
    }
}
