package com.afkl.exercises.spring.metrics;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MetricFilter implements Filter {
 
    @Autowired
    private MetricService metricService;
 
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
      throws java.io.IOException, ServletException {
        chain.doFilter(request, response);
 
        int status = ((HttpServletResponse) response).getStatus();
        metricService.increaseCount(status);
    }

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
}