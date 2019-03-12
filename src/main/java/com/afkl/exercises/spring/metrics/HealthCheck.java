package com.afkl.exercises.spring.metrics;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class HealthCheck  implements HealthIndicator{

	@Override
	public Health health() {
		// TODO Auto-generated method stub
		return Health.status("Alive").up().build();
	}
	
	

}
