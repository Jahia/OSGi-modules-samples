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

import org.jahia.services.content.JCRNodeWrapper;
import org.jahia.services.content.decorator.validation.JCRNodeValidator;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.osgi.service.component.annotations.Component;

/**
 *
 * @author yousria
 */
@Component(immediate = true)
public class TestNodeValidator implements JCRNodeValidator {

    private JCRNodeWrapper node;

    public TestNodeValidator(JCRNodeWrapper node) {
        this.node = node;
    }

    @Length(min = 1000)
    public String getLongValue() {
        return node.getPropertyAsString("longValue");
    }

    @NotEmpty
    public String getNotEmptyValue() {
        return node.getPropertyAsString("notEmptyValue");
    }
}
