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
package org.foo.modules.datasource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component(name = "SampleDataSourceConsumer")
public class SampleDataSourceConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(SampleDataSourceConsumer.class.getName());

    @Reference(target = "(&(objectclass=javax.sql.DataSource)(datasource.name=sampleDS))")
    private DataSource dataSource;

    @Activate
    public void activate() throws SQLException {
        Connection conn = dataSource.getConnection();
        LOGGER.info("Connection retrieved");
        LOGGER.info("Database product Name: {}", conn.getMetaData().getDatabaseProductName());
        conn.close();
    }

}
