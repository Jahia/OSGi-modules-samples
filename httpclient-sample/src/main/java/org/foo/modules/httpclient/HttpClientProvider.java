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
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.util.Enumeration;

@Component(service = HttpClientProvider.class)
public class HttpClientProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientProvider.class);
    private static final String KEYSTORE_PATH = "certs/badssl.com-client.p12";
    private static final String KEYSTORE_PASSWORD = "badssl.com";

    private CloseableHttpClient httpClient;

    @Activate
    protected void activate() throws Exception {
        LOGGER.info("Loading keystore from path:{}", KEYSTORE_PATH);
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(KEYSTORE_PATH);
        KeyStore keystore = KeyStore.getInstance("pkcs12");
        keystore.load(inputStream, KEYSTORE_PASSWORD.toCharArray());
        for (Enumeration<String> entries = keystore.aliases(); entries.hasMoreElements();) {
            LOGGER.info("Keystore alias found: {}", entries.nextElement());
        }

        LOGGER.info("Keystore type: {}", keystore.getType());
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(keystore, KEYSTORE_PASSWORD.toCharArray());
        for (KeyManager km: kmf.getKeyManagers()){
            LOGGER.info("KeyManager: {}", km);
        }

        SSLContext ctx = SSLContext.getInstance("TLSv1.2");
        ctx.init(kmf.getKeyManagers(), null, null);

        httpClient = HttpClients.custom().setSSLContext(ctx).build();
    }

    @Deactivate
    protected void deactivate() {
        try {
            httpClient.close();
        } catch (Exception e) {
            LOGGER.error("Failed to close http client", e);
        }
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }

}
