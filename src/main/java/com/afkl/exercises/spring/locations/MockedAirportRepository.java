package com.afkl.exercises.spring.locations;

import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class MockedAirportRepository implements AirportRepository {

    private final Map<String, Location> nlAirports;
    private final Map<String, Location> enAirports;
    private final ObjectMapper mapper;

    @Autowired
    public MockedAirportRepository(ObjectMapper mapper) {
        this.mapper = mapper;
        nlAirports = parseMockData("nl");
        enAirports = parseMockData("en");
    }

    @Override
    public Optional<Location> get(Locale locale, String key) {
        return locale.getLanguage().equals("nl") ?
                Optional.ofNullable(nlAirports.get(key.toUpperCase())) :
                Optional.ofNullable(enAirports.get(key.toUpperCase()));
    }

    @Override
    public Optional<Collection<Location>> find(Locale locale, String term) {
        Predicate<Location> filter = l -> l.getCode().toLowerCase().contains(term.toLowerCase())
                || l.getName().toLowerCase().contains(term.toLowerCase())
                || l.getDescription().toLowerCase().contains(term.toLowerCase());
        Collection<Location> results = locale.getLanguage().equals("nl") ?
                nlAirports.values().parallelStream().filter(filter).collect(toList()) :
                enAirports.values().parallelStream().filter(filter).collect(toList());
        return Optional.ofNullable(results);
    }

    @Override
    public Collection<Location> list(Locale locale) {
        return locale.getLanguage().equals("nl") ? nlAirports.values() : enAirports.values();
    }

    private Map<String, Location> parseMockData(String lang) {
        try {
            log.info("Loading static resources from classpath and populating mocks.");
            final List<Location> locations = mapper.readValue(
                    new ClassPathResource("locations_".concat(lang).concat(".json")).getInputStream(),
                    mapper.getTypeFactory().constructCollectionType(ArrayList.class, Location.class));
            return locations.parallelStream()
                    .map(l -> new SimpleEntry<>(l.getCode(), l)).sorted()
                    .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
        } catch (IOException e) {
            throw new IllegalStateException("Unable to initialize mock in-memory AirportRepository.", e);
        }
    }

	@Override
	public Iterable<Location> findAll(Sort arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Location> findAll(Pageable arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long count() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Location arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Iterable<? extends Location> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean exists(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<Location> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Location findOne(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Location save(Location arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Location> save(Iterable<? extends Location> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
