package org.foo.modules.actions;

import org.jahia.bin.Action;
import org.jahia.bin.ActionResult;
import org.jahia.data.templates.JahiaTemplatesPackage;
import org.jahia.services.content.JCRSessionWrapper;
import org.jahia.services.render.RenderContext;
import org.jahia.services.render.Resource;
import org.jahia.services.render.URLResolver;
import org.jahia.services.templates.JahiaTemplateManagerService;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This action is using a service from Jahia through OSGi
 */
@Component(service = Action.class, immediate = true)
public class AdvancedAction extends Action {

    private JahiaTemplateManagerService jahiaTemplateManagerService;

    public AdvancedAction() {
        setName("advancedAction");
        setRequireAuthenticatedUser(true);
    }

    @Override
    public ActionResult doExecute(HttpServletRequest req, RenderContext renderContext, Resource resource,
                                  JCRSessionWrapper session, Map<String, List<String>> parameters,
                                  URLResolver urlResolver) throws Exception {
        List <JahiaTemplatesPackage> jahiaTemplatesPackages = jahiaTemplateManagerService.getInstalledModulesForSite(renderContext.getSite().getSiteKey(), false, false, false);
        List<String> modules = new ArrayList<>();
        for (JahiaTemplatesPackage jahiaTemplatesPackage : jahiaTemplatesPackages) {
            modules.add(jahiaTemplatesPackage.getName());
        }

        JSONObject response = new JSONObject();
        response.put("message", "Hello Jahia there are " + modules.size() + " modules on this site");
        response.put("modules", modules);

        return new ActionResult(HttpServletResponse.SC_OK, null, response);
    }

    @Reference
    public void setJahiaTemplateManagerService(JahiaTemplateManagerService jahiaTemplateManagerService) {
        this.jahiaTemplateManagerService = jahiaTemplateManagerService;
    }
}
