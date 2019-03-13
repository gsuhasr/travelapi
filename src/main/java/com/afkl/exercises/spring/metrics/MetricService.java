package com.afkl.exercises.spring.metrics;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.stereotype.Service;

@Service
public class MetricService {
 
	private static ConcurrentMap<Integer, Integer> statusMetric;
    
    @Autowired
    private CounterService counter;
 
    private List<String> statusList;
 
    public void increaseCount(int status) {
        counter.increment("status." + status);
        increaseStatusMetric(status);
        if (!statusList.contains("counter.status." + status)) {
            statusList.add("counter.status." + status);
        }
    }
    
    
    public MetricService() {
        super();
        statusMetric = new ConcurrentHashMap<Integer, Integer>();
        //timeMap = new ConcurrentHashMap<String, ConcurrentHashMap<Integer, Integer>>();
    }

    private void increaseStatusMetric(final int status) {
        final Integer statusCount = statusMetric.get(status);
        if (statusCount == null) {
            statusMetric.put(status, 1);
        } else {
            statusMetric.put(status, statusCount + 1);
        }
    }
    
    
    public Map getStatusMetric() {
    	System.out.println("Suhas::"+statusMetric.size());
        return statusMetric;
    }
}