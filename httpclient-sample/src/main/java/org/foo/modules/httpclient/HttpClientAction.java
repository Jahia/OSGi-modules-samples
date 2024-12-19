/*
 * ==========================================================================================
 * =                            JAHIA'S ENTERPRISE DISTRIBUTION                             =
 * ==========================================================================================
 *
 *                                  http://www.jahia.com
 *
 * JAHIA'S ENTERPRISE DISTRIBUTIONS LICENSING - IMPORTANT INFORMATION
 * ==========================================================================================
 *
 *     Copyright (C) 2002-2024 Jahia Solutions Group. All rights reserved.
 *
 *     This file is part of a Jahia's Enterprise Distribution.
 *
 *     Jahia's Enterprise Distributions must be used in accordance with the terms
 *     contained in the Jahia Solutions Group Terms &amp; Conditions as well as
 *     the Jahia Sustainable Enterprise License (JSEL).
 *
 *     For questions regarding licensing, support, production usage...
 *     please contact our team at sales@jahia.com or go to http://www.jahia.com/license.
 *
 * ==========================================================================================
 */
package org.foo.modules.httpclient;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.jahia.bin.Action;
import org.jahia.bin.ActionResult;
import org.jahia.services.content.JCRSessionWrapper;
import org.jahia.services.render.RenderContext;
import org.jahia.services.render.Resource;
import org.jahia.services.render.URLResolver;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

/**
 * @author Jerome Blanchard
 */
@Component(service = Action.class)
public class HttpClientAction extends Action {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientAction.class);
    private HttpClientProvider httpClientProvider;
    private static final String ENDPOINT = "https://client.badssl.com/";

    @Activate
    public void activate() {
        setName("testHttpClientWithMutualTLS");
        setRequireAuthenticatedUser(false);
    }

    @Override
    public ActionResult doExecute(HttpServletRequest req, RenderContext renderContext, Resource resource,
            JCRSessionWrapper session, Map<String, List<String>> parameters,
            URLResolver urlResolver) throws Exception {
        JSONObject response = new JSONObject();

        LOGGER.info("Calling endpoint: {}", ENDPOINT);
        HttpGet httpGet = new HttpGet(ENDPOINT);
        HttpResponse resp = httpClientProvider.getHttpClient().execute(httpGet);
        LOGGER.info("Response status: {}", resp.getStatusLine().getStatusCode());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        resp.getEntity().writeTo(baos);

        response.put("endpoint", ENDPOINT);
        response.put("status-code", resp.getStatusLine().getStatusCode());
        response.put("response", baos.toString());
        return new ActionResult(HttpServletResponse.SC_OK, null, response);
    }

    @Reference
    public void setHttpClientProvider(HttpClientProvider httpClientProvider) {
        this.httpClientProvider = httpClientProvider;
    }
}
