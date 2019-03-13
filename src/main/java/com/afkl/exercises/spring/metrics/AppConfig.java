package com.afkl.exercises.spring.metrics;

import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class AppConfig {

	//@Bean
	public FilterRegistrationBean filterRegistrationBean() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		CustomURLFilter customURLFilter = new CustomURLFilter();

		registrationBean.setFilter(customURLFilter);
		registrationBean.addUrlPatterns("/airports");
		registrationBean.setOrder(1); // set precedence
		return registrationBean;
	}
}