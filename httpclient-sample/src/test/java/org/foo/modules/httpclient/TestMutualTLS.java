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
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.junit.Test;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.*;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author Jerome Blanchard
 */
public class TestMutualTLS {

    private static final Logger LOGGER = Logger.getLogger(TestMutualTLS.class.getName());
    private static final String SERVER_URL = "https://client.badssl.com/";
    private static final String KEYSTORE_PATH = "certs/badssl.com-client.p12";
    private static final String KEYSTORE_PASSWORD = "badssl.com";

    @Test
    public void testSSLContext() {
        try {
            LOGGER.log(Level.INFO, "Loading keystore from .p12 file");
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(KEYSTORE_PATH);
            KeyStore keystore = KeyStore.getInstance("pkcs12");
            keystore.load(inputStream, KEYSTORE_PASSWORD.toCharArray());
            for (Enumeration<String> entries = keystore.aliases(); entries.hasMoreElements();) {
                LOGGER.log(Level.INFO, "Keystore alias found: {}", entries.nextElement());
            }

            LOGGER.log(Level.INFO, "Keystore type: {}", keystore.getType());
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(keystore, KEYSTORE_PASSWORD.toCharArray());
            for (KeyManager km: kmf.getKeyManagers()){
                LOGGER.log(Level.INFO, "KeyManager: {}", km);
            }

            SSLContext ctx = SSLContext.getInstance("TLSv1.2");
            ctx.init(kmf.getKeyManagers(), null, null);

            try (CloseableHttpClient httpClient = HttpClients.custom().setSSLContext(ctx).build()) {
                LOGGER.log(Level.INFO, "Executing request");
                HttpGet httpGet = new HttpGet(SERVER_URL);
                HttpResponse response = httpClient.execute(httpGet);
                LOGGER.log(Level.INFO, "Response status: {}", response.getStatusLine().getStatusCode());
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                response.getEntity().writeTo(baos);
                LOGGER.log(Level.INFO, "Response content: " + baos);
                assertEquals(200, response.getStatusLine().getStatusCode());
            }

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error while testing HttpClient", e);
            fail(e.getMessage());
        }
    }

}
