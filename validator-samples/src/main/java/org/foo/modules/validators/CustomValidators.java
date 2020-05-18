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
 *     Copyright (C) 2002-2020 Jahia Solutions Group. All rights reserved.
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
package org.foo.modules.validators;

import org.jahia.services.content.decorator.validation.JCRNodeValidatorDefinition;
import org.osgi.service.component.annotations.Component;

import java.util.Collections;
import java.util.Map;

/**
 *
 * @author yousria
 */
@Component(service = JCRNodeValidatorDefinition.class, immediate = true)
public class CustomValidators extends JCRNodeValidatorDefinition {
    /**
     * Register custom validator for generic content nodetype
     *
     * @return validators list
     * @see org.foo.modules.validators.TestContent
     * @see org.foo.modules.validators.TestNodeValidator
     */
    @Override
    public Map<String, Class> getValidators() {
        return Collections.singletonMap(TestContent.NODETYPE, TestNodeValidator.class);
    }
}
