package org.sample.modules.sampleservlet;

import org.jahia.exceptions.JahiaException;
import org.jahia.services.securityfilter.PermissionService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.RepositoryException;
import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Example servlet which is accessible for users having the scope sampleApi
 * This scope is automatically applied for user having the admin permission.
 * 
 * <p>Component properties:
 * <ul>
 *   <li><strong>service</strong>: Registers this component as both HttpServlet and Servlet services,
 *       making it available to the OSGi HTTP service.</li>
 *   <li><strong>alias=/sample</strong>: Maps this servlet to the URL path /modules/sample
 *       (the /modules prefix is added by the Jahia framework).</li>
 * </ul>
 *
 * <p>Available endpoints:
 * <ul>
 *   <li>/modules/sample/sayHello - Returns "Hello!" (requires sampleApi.sayHello permission)</li>
 *   <li>/modules/sample/sayHi - Returns "Hi!" (requires sampleApi.sayHi permission)</li>
 *   <li>/modules/sample/sayBye - Returns "Bye!" (requires sampleApi.sayBye permission)</li>
 * </ul>
 */
@Component(service = { javax.servlet.http.HttpServlet.class, javax.servlet.Servlet.class }, property = { "alias=/sample" })
public class SampleServlet extends HttpServlet {

    private static final String SCOPE = "sampleApi";
    private static final Logger LOGGER = LoggerFactory.getLogger(SampleServlet.class);

    private PermissionService permissionService;

    @Reference
    public void setPermissionService(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("Received request");
        String api = request.getPathInfo().substring(1);
        try {
            checkUserAccess(api);
        } catch (RepositoryException e) {
            response.sendError(500, "Error while calling api, check logs for more details");
            LOGGER.error("Error while calling action, check logs for more details", e);
            return;
        } catch (JahiaException e) {
            LOGGER.debug("Access denied to call api {}", api, e);
            response.sendError(404, "Entrypoint not found: " + api);
            return;
        }

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("text/plain; charset=UTF-8");
        switch (api) {
            case "sayHello":
                response.getWriter().write("Hello!");
                break;
            case "sayHi":
                response.getWriter().write("Hi!");
                break;
            case "sayBye":
                response.getWriter().write("Bye!");
                break;
            default:
                response.getWriter().write("Unknown API");
        }

    }

    public SampleServlet() {
        LOGGER.info("Sample servlet started");
    }

    public void checkUserAccess(String api) throws RepositoryException, JahiaException {
        if (!this.permissionService.hasPermission(SCOPE + "." + api)) {
            LOGGER.warn("Access not permitted to call api {}", api);
            throw new JahiaException(SCOPE + "." + api, "Access to api [" + api + "] is secured and restricted",
                    JahiaException.SECURITY_ERROR, JahiaException.WARNING_SEVERITY);
        }
    }
}
