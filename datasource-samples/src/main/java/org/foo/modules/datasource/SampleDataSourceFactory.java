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

import org.mariadb.jdbc.MariaDbPoolDataSource;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.Dictionary;
import java.util.Hashtable;

@Component(name = "SampleDataSourceFactory", configurationPid = "org.foo.modules.datasource.sample", immediate = true)
@Designate(ocd = SampleDataSourceFactory.DataSourceConfig.class)
public class SampleDataSourceFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(SampleDataSourceFactory.class.getName());

    @ObjectClassDefinition(name = "DataSourceConfiguration", description = "The DataSource Configuration")
    public @interface DataSourceConfig {
        @AttributeDefinition(name = "Datasource Name", defaultValue = "defaultDS", description = "The DataSource Name")
        String name() default "defaultDS";

        @AttributeDefinition(name = "Server Host", defaultValue = "127.0.0.1", description="The database server hostname")
        String hostname() default "127.0.0.1";

        @AttributeDefinition(name = "Server Port", defaultValue = "3306", description="The database server port")
        int port() default 3306;

        @AttributeDefinition(name = "Database Name", defaultValue = "sampleDB", description="The database name")
        String dbname() default "sampleDB";

        @AttributeDefinition(name = "Username", defaultValue = "root", description="The username to use for authentication")
        String username() default "root";

        @AttributeDefinition(name = "Password", defaultValue = "root1234", description="The password to use for authentication")
        String password() default "root1234";
    }

    private BundleContext bundleContext;
    private String name;
    private ServiceRegistration dsRegistration;
    private MariaDbPoolDataSource datasource;
    private int configHash;

    @Activate
    protected void activate(BundleContext ctx, DataSourceConfig config) throws SQLException {
        bundleContext = ctx;
        try {
            datasource = new MariaDbPoolDataSource(buildUrl(config));
            name = config.name();

            Dictionary<String, Object> svcProps = new Hashtable<>();
            svcProps.put("datasource.name", name);
            svcProps.put(Constants.SERVICE_VENDOR, "Jahia Solutions Group");
            svcProps.put(Constants.SERVICE_DESCRIPTION, "DataSource service based on MariaDB Pool");
            dsRegistration = bundleContext.registerService(javax.sql.DataSource.class, datasource, svcProps);

            configHash = hash(config);
            LOGGER.info("DataSource created and available at target: (&(objectclass=javax.sql.DataSource)({}={})", "datasource.name", name);
        } catch (SQLException e) {
            LOGGER.error("Error creating DataSource [{}]: {}", name, e.getMessage());
            throw e;
        }
    }

    @Modified
    protected void modified(DataSourceConfig config) throws Exception {
        if (this.configHash != hash(config)) {
            LOGGER.info("Changes detected in datasource config. DataSource would be recreated");
            deactivate();
            activate(bundleContext, config);
        }
    }

    @Deactivate
    protected void deactivate() {
        LOGGER.info("Closing DataSource [{}]", name);
        if (dsRegistration != null) {
            dsRegistration.unregister();
            dsRegistration = null;
        }

        datasource.close();
        datasource = null;
        name = null;
        configHash = 0;
    }

    private String buildUrl(DataSourceConfig config) {
        return "jdbc:mariadb://" + config.hostname() + ":" + config.port()
                + "/" + config.dbname() + "?user=" + config.username() + ((!config.password().isEmpty())?"&password=" + config.password():"");
    }

    private int hash(DataSourceConfig config) {
        String configString = config.name() + config.hostname() + config.port() + config.dbname() + config.username() + config.password();
        return configString.hashCode();
    }

}
