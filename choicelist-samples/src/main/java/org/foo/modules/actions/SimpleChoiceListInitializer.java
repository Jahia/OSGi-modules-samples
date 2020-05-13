package org.foo.modules.actions;

import org.apache.commons.beanutils.BeanUtils;
import org.jahia.services.content.nodetypes.ExtendedPropertyDefinition;
import org.jahia.services.content.nodetypes.initializers.ChoiceListValue;
import org.jahia.services.content.nodetypes.initializers.ModuleChoiceListInitializer;
import org.osgi.service.component.annotations.*;
import org.tuckey.web.filters.urlrewrite.Run;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * A simple configurable ChoiceList initializer
 */
@Component(service = ModuleChoiceListInitializer.class, scope=ServiceScope.BUNDLE)
public class SimpleChoiceListInitializer implements ModuleChoiceListInitializer {

    private String key;
    private List<String> values;

    @Activate
    public void activate(Map<String, ?> props) {
        try {
            BeanUtils.copyProperties(this, props);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    public void setValues(String values) {
        this.values = Arrays.asList(values.split(","));
    }

    @Override
    public List<ChoiceListValue> getChoiceListValues(ExtendedPropertyDefinition extendedPropertyDefinition, String s, List<ChoiceListValue> list, Locale locale, Map<String, Object> map) {
        return values.stream().map(v -> new ChoiceListValue(v,v)).collect(Collectors.toList());
    }
}
