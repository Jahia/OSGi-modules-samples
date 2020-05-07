package org.foo.modules.filters;

import net.htmlparser.jericho.*;
import org.jahia.services.render.RenderContext;
import org.jahia.services.render.Resource;
import org.jahia.services.render.filter.AbstractFilter;
import org.jahia.services.render.filter.RenderChain;
import org.jahia.services.render.filter.RenderFilter;
import org.jetbrains.annotations.NotNull;
import org.osgi.service.component.annotations.Component;

import java.util.List;

/**
 * This is the minimum required setup to declare a Jahia Filter using OSGi
 */
@Component(service = RenderFilter.class, immediate = true)
public class SimpleFilter extends AbstractFilter {

    private String headScript;

    public SimpleFilter() {
        setPriority(3);
        setApplyOnEditMode(true);
        setSkipOnAjaxRequest(true);
        setApplyOnConfigurations("page");
        setApplyOnTemplateTypes("html,html-*");
    }

    @Override
    public String prepare(RenderContext renderContext, Resource resource, RenderChain chain) throws Exception {
        headScript = "\n<script>alert('Hello Jahia!')</script>\n<";
        return super.prepare(renderContext, resource, chain);
    }

    @Override
    public String execute(String previousOut, RenderContext renderContext, Resource resource, RenderChain chain) throws Exception {
        String output = super.execute(previousOut, renderContext, resource, chain);
        output = enhanceOutput(output);
        return output;
    }

    /**
     * This Function is just to add some logic to our filter and therefore not needed to declare a filter
     *
     * @param output    Original output to modify
     * @return          Modified output
     */
    @NotNull
    private String enhanceOutput(String output) {
        Source source = new Source(output);
        OutputDocument outputDocument = new OutputDocument(source);
        List<Element> elementList = source.getAllElements(HTMLElementName.HEAD);
        if (elementList != null && !elementList.isEmpty()) {
            final EndTag bodyEndTag = elementList.get(0).getEndTag();
            outputDocument.replace(bodyEndTag.getBegin(), bodyEndTag.getBegin() + 1, headScript);
        }
        output = outputDocument.toString().trim();
        return output;
    }
}
