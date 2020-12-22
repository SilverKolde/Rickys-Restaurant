package com.virtualpairprogrammers.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;


// ==================  Just an example that a single URL can have multiple Filters.  =================================

@WebFilter("/searchResults.html")
public class MenuSearchLoggingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        System.out.println("User searched for : " + request.getParameter("searchTerm")); // rice already changed to risotto in here

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
