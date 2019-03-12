package com.afkl.exercises.spring.metrics;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.stereotype.Service;

@Service
public class MetricService {
 
    private ConcurrentMap<Integer, Integer> statusMetric;
 
    public MetricService() {
        statusMetric = new ConcurrentHashMap<Integer, Integer>();
    }
     
   /* public void increaseCount(String request, int status) {
        Integer statusCount = statusMetric.get(status);
        if (statusCount == null) {
            statusMetric.put(status, 1);
        } else {
            statusMetric.put(status, statusCount + 1);
        }
    }*/
    
    
    
    private ConcurrentMap<String, ConcurrentHashMap<Integer, Integer>> metricMap;
    
    public void increaseCount(String request, int status) {
        ConcurrentHashMap<Integer, Integer> statusMap = metricMap.get(request);
        if (statusMap == null) {
            statusMap = new ConcurrentHashMap<Integer, Integer>();
        }
 
        Integer count = statusMap.get(status);
        if (count == null) {
            count = 1;
        } else {
            count++;
        }
        statusMap.put(status, count);
        metricMap.put(request, statusMap);
    }
 
    public Map getFullMetric() {
    	
    	
    	metricMap.forEach((k,v)->System.out.println("Key:::"+k+"  Value::"+v));
        return metricMap;
    }
    
 
    public Map getStatusMetric() {
    	System.out.println("Suhas");

    	statusMetric.forEach((k,v)->System.out.println("Key:::"+k+"  Value::"+v));
        
        return statusMetric;
    }
}