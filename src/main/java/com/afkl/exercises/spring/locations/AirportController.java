package com.afkl.exercises.spring.locations;

import com.afkl.exercises.spring.metrics.MetricService;
import com.afkl.exercises.spring.paging.Pageable;
import com.sun.javafx.font.Metrics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/airports")
public class AirportController {

	private final AirportRepository repository;
	private final MetricService metricService;

	@Autowired
	public AirportController(AirportRepository repository,MetricService metricService) {
		this.repository = repository;
		this.metricService=metricService;
	}
	
	@RequestMapping(value = "/metric", method = RequestMethod.GET)
	@ResponseBody
	public Map getMetric() {
	    return metricService.getFullMetric();
	}
	
	
	@RequestMapping(value = "/statusmetric", method = RequestMethod.GET)	
	public Map getStatusMetric() {
		
	    return metricService.getStatusMetric();
	}

	@RequestMapping(method = GET)
	public Callable<PagedResources<Resource<Location>>> list(
			@RequestParam(value = "lang", defaultValue = "en") 
			String lang, Pageable<Location> pageable,@RequestHeader HttpHeaders httpHeader) {
		
		log.info(httpHeader.getFirst("trace-id"));
		return () -> pageable.partition(repository.list(Locale.forLanguageTag(lang)));
	}

	@RequestMapping(value = "/{key}", method = GET)
	public Callable<HttpEntity<Location>> show(@RequestParam(value = "lang",
	defaultValue = "en") String lang,
			@PathVariable("key") String key,@RequestHeader HttpHeaders httpHeader) {
		log.info(httpHeader.getFirst("trace-id"));
		return () -> {
			Thread.sleep(ThreadLocalRandom.current().nextLong(200, 800));
			return repository.get(Locale.forLanguageTag(lang), key)
					.map(l -> new ResponseEntity<>(l, OK))
					.orElse(new ResponseEntity<>(NOT_FOUND));
		};
	}

	@RequestMapping(method = GET, params = "term")
	public Callable<HttpEntity<PagedResources<Resource<Location>>>> find(
			@RequestParam(value = "lang", defaultValue = "en") String lang, @RequestParam("term") String term,
			Pageable<Location> pageable,@RequestHeader HttpHeaders httpHeader) {
		log.info(httpHeader.getFirst("trace-id"));
		return () -> repository.find(Locale.forLanguageTag(lang), term)
				.map(l -> new ResponseEntity<>(pageable.partition(l), OK)).orElse(new ResponseEntity<>(NOT_FOUND));
	}

}
