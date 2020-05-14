package org.foo.modules.choicelists;

import org.apache.commons.beanutils.*;
import org.jahia.services.content.JCRPropertyWrapper;
import org.jahia.services.content.nodetypes.ExtendedPropertyDefinition;
import org.jahia.services.content.nodetypes.initializers.ChoiceListValue;
import org.jahia.services.content.nodetypes.initializers.ModuleChoiceListInitializer;
import org.jahia.services.content.nodetypes.renderer.AbstractChoiceListRenderer;
import org.jahia.services.content.nodetypes.renderer.ModuleChoiceListRenderer;
import org.jahia.services.render.RenderContext;
import org.jahia.utils.Patterns;
import org.osgi.service.component.annotations.*;

import javax.jcr.RepositoryException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * A simple configurable ChoiceList initializer
 */
@Component(service = {ModuleChoiceListInitializer.class, ModuleChoiceListRenderer.class}, scope=ServiceScope.BUNDLE)
public class SimpleChoiceListInitializer extends AbstractChoiceListRenderer implements ModuleChoiceListRenderer, ModuleChoiceListInitializer {

    private String key;
    private LazyDynaMap values = new LazyDynaMap();

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

    public DynaBean getValues() {
        return values;
    }

    @Override
    public String getStringRendering(RenderContext context, JCRPropertyWrapper propertyWrapper)
            throws RepositoryException {
        String value;
        if (propertyWrapper.isMultiple()) {
            value = propertyWrapper.getValues()[0].getString();
        } else {
            value = propertyWrapper.getValue().getString();
        }
        return getStringRendering(context, null, value);
    }

    @Override
    public String getStringRendering(Locale locale, ExtendedPropertyDefinition extendedPropertyDefinition, Object o) throws RepositoryException {
        return (String) values.get(o.toString());
    }

    @Override
    public List<ChoiceListValue> getChoiceListValues(ExtendedPropertyDefinition extendedPropertyDefinition, String s, List<ChoiceListValue> list, Locale locale, Map<String, Object> map) {
        Set<Map.Entry<String,String>> set = values.getMap().entrySet();
        return set.stream().map(entry -> new ChoiceListValue(entry.getValue(), entry.getKey())).collect(Collectors.toList());
    }
}
