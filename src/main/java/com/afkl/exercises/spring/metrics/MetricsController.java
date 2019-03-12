package com.afkl.exercises.spring.metrics;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/metrics")
public class MetricsController {
	
	private final MetricService metricService;

	@Autowired
	public MetricsController(MetricService metricService) {
		this.metricService = metricService;
	}
	
	@RequestMapping(value = "/statusmetric", method = RequestMethod.GET)
	@ResponseBody
	public Map getStatusMetric() {
	    return metricService.getStatusMetric();
	}
}

