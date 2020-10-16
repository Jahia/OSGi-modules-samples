package org.foo.modules.valve;

import org.jahia.api.Constants;
import org.jahia.api.usermanager.JahiaUserManagerService;
import org.jahia.params.valves.AuthValveContext;
import org.jahia.params.valves.BaseAuthValve;
import org.jahia.params.valves.LoginUrlProvider;
import org.jahia.params.valves.LogoutUrlProvider;
import org.jahia.pipelines.Pipeline;
import org.jahia.pipelines.PipelineException;
import org.jahia.pipelines.valves.Valve;
import org.jahia.pipelines.valves.ValveContext;
import org.jahia.services.content.decorator.JCRUserNode;
import org.jahia.services.usermanager.JahiaUser;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.jahia.params.valves.LoginEngineAuthValveImpl.OK;
import static org.jahia.params.valves.LoginEngineAuthValveImpl.VALVE_RESULT;

@Component(service = {Valve.class, LoginUrlProvider.class, LogoutUrlProvider.class}, immediate = true)
public class MyValve extends BaseAuthValve implements LoginUrlProvider, LogoutUrlProvider{
    private Pipeline authPipeline;
    private JahiaUserManagerService userManagerService;


    @Reference(service = Pipeline.class, target = "(type=authentication)")
    public void setAuthPipeline(Pipeline authPipeline) {
        this.authPipeline = authPipeline;
    }

    @Reference
    public void setUserManagerService(JahiaUserManagerService userManagerService) {
        this.userManagerService = userManagerService;
    }

    @Activate
    public void activate(BundleContext bundleContext) {
        setId("testValve");
        removeValve(authPipeline);
        addValve(authPipeline, 0, null, null);
    }

    @Override
    public void invoke(Object context, ValveContext valveContext) throws PipelineException {
        AuthValveContext authContext = (AuthValveContext) context;

        HttpServletRequest servletRequest = authContext.getRequest();
        if (servletRequest.getParameter("user") != null) {
            JCRUserNode user = userManagerService.lookupUser(servletRequest.getParameter("user"));
            JahiaUser jahiaUser = user.getJahiaUser();

            HttpSession session = servletRequest.getSession();
            if (session != null) {
                servletRequest.getSession().invalidate();
                // user has been successfully authenticated, note this in the current session.
                servletRequest.getSession().setAttribute(Constants.SESSION_USER, jahiaUser);
            }
            // eventually set the Jahia user
            authContext.getSessionFactory().setCurrentUser(jahiaUser);
            servletRequest.setAttribute(VALVE_RESULT, OK);

            return;
        }
        valveContext.invokeNext(context);
    }

    @Override
    public String getLoginUrl(HttpServletRequest httpServletRequest) {
        return "/modules/auth-valve/html/login.html";
    }

    @Override
    public boolean hasCustomLoginUrl() {
        return true;
    }

    @Override
    public String getLogoutUrl(HttpServletRequest httpServletRequest) {
        return "/modules/auth-valve/html/logout.html";
    }

    @Override
    public boolean hasCustomLogoutUrl() {
        return true;
    }
}
