package com.virtualpairprogrammers.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter("/searchResults.html")
public class MenuSearchCorrectionFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Filter interface requires us to override this...
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String searchTerm = request.getParameter("searchTerm");

        // Now what we are trying to do, is that if user wants "rice", but we offer "risotto", then client should be able to see it,
        // so we have to change the request parameters.
        // But we can't do that directly, web wasn't designed to do that -  we have a work-around

        if (searchTerm.toLowerCase().equals("rice")) {

            // Basically we have to create a new request.
            // To do that, we'll firstly create MenuSearchCorrectionRequestWrapper that extends HttpServletRequestWrapper.
            MenuSearchCorrectionRequestWrapper wrapper = new MenuSearchCorrectionRequestWrapper((HttpServletRequest) request);

            // There we can set a new searchTerm
            wrapper.setNewSearchTerm("risotto");

            // and pass the modified request instead of the original
            chain.doFilter(wrapper, response);
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        // Filter interface requires us to override this...
    }
}
