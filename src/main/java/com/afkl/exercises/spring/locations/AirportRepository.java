package com.afkl.exercises.spring.locations;

import java.util.Collection;
import java.util.Locale;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AirportRepository  extends PagingAndSortingRepository<Location, String>{

    Optional<Location> get(Locale locale, String key);

    Optional<Collection<Location>> find(Locale locale, String term);

    Collection<Location> list(Locale locale);

}
