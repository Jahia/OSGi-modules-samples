package org.sample.modules.sampleservletfilter;

import org.jahia.bin.filters.AbstractServletFilter;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Sample class to extend AbstractServletFilter with hot deploy (SUP-122)
 */
@Component(immediate = true, service = AbstractServletFilter.class)
public class SampleServletFilter extends AbstractServletFilter {

    private Logger logger = LoggerFactory.getLogger(SampleServletFilter.class);

    @Override public void init(FilterConfig filterConfig) {
    }

    @Activate
    public void activate() {
        logger.info("Started SimpleServletFilter with hot deploy...");
        setMatchAllUrls(true);
    }

    @Override public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        logger.debug("Executing SampleServletFilter.doFilter()...");
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setHeader("x-sample-filter-header", "some-test-value");
        chain.doFilter(request, response);
    }

    @Override public void destroy() {
        logger.info("Destroying SimpleServletFilter...");
    }
}
