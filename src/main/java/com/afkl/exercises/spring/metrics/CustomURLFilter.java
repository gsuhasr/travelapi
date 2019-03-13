package com.afkl.exercises.spring.metrics;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.afkl.exercises.spring.locations.AirportRepository;

public class CustomURLFilter implements Filter {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomURLFilter.class);
	
	
    private MetricService metricService;
	
	@Autowired
	public CustomURLFilter() {
		this.metricService = new MetricService();
		
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		LOGGER.info("########## Initiating CustomURLFilter filter ##########");
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		
		// call next filter in the filter chain
		filterChain.doFilter(request, response);
		int status = ((HttpServletResponse) response).getStatus();
        metricService.increaseCount(status);
	}

	@Override
	public void destroy() {

	}
}